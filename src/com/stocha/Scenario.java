package com.stocha;

import java.util.HashMap;

public class Scenario {
    //variables de classe
    private HashMap<Station,HashMap<Station,Integer>> donnees; //demande stochastique par station
    private HashMap<Station,HashMap<Station, Integer>> beta; //nb vélos loués par station en direction de chaque station
    private HashMap<Station,Integer> solutionScenario;
    private float proba;

    //constructeurs

    public Scenario() {
        this.donnees = new HashMap<>();
        this.solutionScenario = new HashMap<>();
        this.beta = new HashMap<>();
    }

    public Scenario(HashMap<Station,HashMap<Station,Integer>> donnees, HashMap<Station,HashMap<Station,Integer>> beta, HashMap<Station,Integer> solutionScenario, float proba) {
        this.donnees = donnees;
        this.beta = beta;
        this.solutionScenario = solutionScenario;
        this.proba = proba;
    }

    //getters et setters

    public HashMap<Station,HashMap<Station,Integer>> getDonnees() {
        return donnees;
    }

    public void setDonnees(HashMap<Station,HashMap<Station,Integer>> donnees) {
        this.donnees = donnees;
    }

    public HashMap<Station,Integer> getSolutionScenario() {
        return solutionScenario;
    }

    public void setSolutionScenario(HashMap<Station,Integer> solutionScenario) {
        this.solutionScenario = solutionScenario;
    }

    public HashMap<Station, HashMap<Station, Integer>> getBeta() {
        return beta;
    }

    public void setBeta(HashMap<Station, HashMap<Station, Integer>> beta) {
        this.beta = beta;
    }

    public float getProba() {
        return proba;
    }

    public void setProba(float proba) {
        this.proba = proba;
    }
}
