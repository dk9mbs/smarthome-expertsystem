set term png size 800,600
set output "temp.png"

set title "Zimmer Temperaturverlauf in Abhängigkeit von der Zeit"
set xlabel "Zeit t in Ticks (1 Tick=5Minuten)"
set ylabel "Temperatur in C°"

set xrange [0:288]
set yrange [6:23]
set grid
show grid
plot "/var/phl/log/szenario.log" using 1:2 title "gemessen" with lines, \
    "/var/phl/log/szenario.log" using 1:2 title "geglättet"  with lines smooth bezier
#pause -1 "Hit any key to continue"
