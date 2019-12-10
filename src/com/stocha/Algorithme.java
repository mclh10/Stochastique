package com.stocha;

import java.util.HashMap;

public abstract class Algorithme {
    private Probleme probleme;
    Probleme getMonProbleme(){
        return probleme;
    }
    void setMonProbleme(Probleme pb){
        this.probleme = pb;
    }
    abstract HashMap<Station,Integer> executer();
}
