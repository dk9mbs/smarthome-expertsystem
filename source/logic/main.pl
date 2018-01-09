:- dynamic status/2.
:- dynamic group/3.

/*
Im Status wird der Zustand des Systems verwaltet.
*/
status(temp_vorlauf,0).
status(temp_vorlauf_parallel,35).
status(temp_vorlauf_steigung,-2).
status(temp_aussen,25).

status(temp_wohnzimmer,19).
status(temp_wohnzimmer_soll,22).
status(temp_range_min,0.5).
status(temp_range_max,0.5).
status(heizung,off).
status(anwesend,yes).
status(tag,no).
status(sommer,no).
status(heizung_nacht,off).  

/*
Status - Gruppen Zuordnung(name,root,type).
*/
group(all,root,group).
group(home,all,group).
group(garten,all,group).
group(temp_home,home,group).
group(temp_home_eg,temp_home,group).
group(temp_home_keller,temp_home,group).

group(temp_wohnzimmer_soll,temp_home_eg,status).
group(temp_kueche_soll,temp_home_eg,status).
group(temp_bad_soll,temp_home_eg,status).


/*
Anwesenheitserkennung
*/

msg_router(anwesend,yes,default,StatusOut,ValueOut,default) :-
    status(heizung_nacht,off),getgroupstatus(X,home),StatusOut=X,ValueOut=22.

msg_router(anwesend,no,default,StatusOut,ValueOut,default) :-
    getgroupstatus(X,home),StatusOut=X,ValueOut=20.

/*
Nachabsenkung in allen Raeumen.
*/
msg_router(heizung_nacht,on,default,StatusOut,ValueOut,default) :-
    getgroupstatus(X,home),StatusOut=X,ValueOut=19.

msg_router(heizung_nacht,off,default,StatusOut,ValueOut,default) :-
    status(anwesend,yes),getgroupstatus(X,home),StatusOut=X,ValueOut=22.


/*
Ueber die Aussentemperatur wird die Vorlauftemperatur der Heizungsanlage geregelt.
*/
msg_router(StatusIn,ValueIn,RoutingIn,StatusOut,ValueOut,RoutingOut) :-
    StatusIn=temp_aussen,status(temp_aussen,Temp),Temp>15.0,StatusOut=temp_vorlauf,ValueOut=0,RoutingOut=default,!.

msg_router(StatusIn,ValueIn,RoutingIn,StatusOut,ValueOut,RoutingOut) :-
    StatusIn=temp_aussen,status(temp_vorlauf_parallel,Parallel),status(temp_vorlauf_steigung,Steigung),status(temp_aussen,Aussen),
    Temp is Parallel+(Aussen*Steigung), StatusOut=temp_vorlauf,ValueOut=Temp,RoutingOut=default.






msg_router(StatusIn,ValueIn,RoutingIn,StatusOut,ValueOut,default) :-
    StatusIn=temp_wohnzimmer,temp(StatusIn,[StatusOut,ValueOut]).

/*
Zuordnungstabelle Fenster - Temp. Sensor - Raum 
Fenster, Temperatursensor, Raum
*/
fenster_temp_map(fenster_flur1, temp_flur, flur).
fenster_temp_map(fenster_flur2, temp_flur, flur).
fenster_temp_map(fenster_wohnzimmer, temp_wohnzimmer, wohnzimmer).

/*
Zuordnungstabelle Aktor - Sensor
*/
aktor_sensor_map(heizung,temp_wohnzimmer).
aktor_sensor_map(heizventil_flur,temp_flur).

/*
Unniversal Thermostat Regelung fuer alle Raeume
Tag=temp_fenster ID des Temp. Sensors.
*/
temp(Tag, Return) :-
    fenster_temp_map(Fenster,Tag,_),status(Tag,Temp),Temp<7,aktor_sensor_map(Aktor,Tag),append([],[Aktor,on],Return),!.

temp(Tag, Return) :-
    fenster_temp_map(Fenster,Tag,_),status(Fenster,open),aktor_sensor_map(Aktor,Tag),append([],[Aktor,off],Return),!.

temp(Tag, Return) :-
    status(sommer,Sommer),Sommer='yes'
    ,append([],[heizung,off],Return),!.

temp(Tag, Return) :-
    status(Tag,Temp),status(temp_wohnzimmer_soll,Soll)
    ,status(temp_range_min,RangeMin),status(temp_range_max,RangeMax)
    ,SollMin is Soll-RangeMin,SollMax is Soll+RangeMax
    ,(
        (Temp>=SollMax,append([],[heizung,off],Return))
        ;(Temp<SollMin,append([],[heizung,on],Return))
    ),!.

temp(Tag, Return) :-
   append([],[test,on],Return),!.



/*
System Regeln
*/    
setstatus(Name,Value) :-
    (retract(status(Name,_)),assert(status(Name,Value)),!);(assert(status(Name,Value)),!).

getstatus(Name,Value) :-
    status(Name,Value),!.

getstatus(Name,Value,Default) :-
    (status(Name,Value),!);(Value=Default,!).


/*
Raumgruppen ermitteln
*/
getgroupstatus(Name,Root) :-
    group(Name,Root,status).

getgroupstatus(Name,Root) :-
    group(X,Root,group),getgroupstatus(Name,X).


