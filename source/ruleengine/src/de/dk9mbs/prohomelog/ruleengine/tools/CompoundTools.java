///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package de.dk9mbs.prohomelog.ruleengine.tools;
//
//import java.util.ArrayList;
//import jpl.Atom;
//import jpl.Compound;
//import jpl.Term;
//
///**
// *
// * @author mbuehler
// */
//public class CompoundTools {
//
//    private static boolean compoundReader(Term term, ArrayList list) {
//        for (Term oneTerm : term.args()) {
//            if( (!oneTerm.isCompound() || oneTerm.isAtom()) && !oneTerm.toString().equals("[]") ) { 
//                list.add(oneTerm.toString());
//            }
//            if(oneTerm.isCompound() && !oneTerm.isAtom() ) compoundReader(oneTerm, list);
//        }        
//        return true;
//    }
//
//    private static Compound getCompound() {
//        Compound teacher_of = new Compound(
//            "teacher_of",
//            new Term[] {
//                new Atom("test1"),
//                new Atom("test2")
//            }
//        );   
//        return teacher_of;
//    }
//}
