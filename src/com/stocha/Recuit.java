package com.stocha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Recuit extends Algorithme {
    //Variables de classe
    private float temperature = 100f;
    private int nbIterations = 10;
    private int palierTemperature = 40;
    private ArrayList<Scenario> scenarioList; //Deterministe = 1 scénario sinon plusieurs pour le stocha


    //Constructeur de la classe com.stocha.Recuit
    public Recuit(float myTemp, int myNbIt, int myPalierTemp, ArrayList<Scenario> myScenario) {
        this.temperature = myTemp;
        this.nbIterations = myNbIt;
        this.palierTemperature = myPalierTemp;
        this.getMonProbleme().setMesScenarios(myScenario);
    }
    public Recuit(ArrayList<Scenario> myScenario) {
        this.getMonProbleme().setMesScenarios(myScenario);
    }

    //Methodes de classe
    // True si p <= exp(-deltaF/temperature)
    public boolean calculerCstGibbsBoltz(double myDeltaF, float myTemperature) {
        //Tirer une probabilité au hasard, nombre compris entre 0 et 1
        double myProba = Math.random() ;
        //Calcul constante de Gibbs-Boltzmann
        double myGB = Math.exp(-myDeltaF)/myTemperature;
        return myProba <= myGB;
    }


    public HashMap<Station,Integer> calcVoisin(HashMap<Station,Integer> myHm) {
        //HashMap renvoyée en résultat
        HashMap<Station, Integer> myHmResultat = new HashMap<Station, Integer>(myHm);
        //Probabilite de tirer un nombre entre 0 et 1
        double probaAction = Math.random();
        //Tirer une station au hasard
        Random generator = new Random();
        Station[] values = (Station[]) myHm.keySet().toArray();
        Station stationAffectee = (Station) values[generator.nextInt(values.length)];

        //3 actions possibles au niveau du voisinage
        //Si 0 < probaAction < 0,33, ajouter un vélo
        if(probaAction >= 0 && probaAction < 0.33) {
            //Prend une station au hasard
            myHmResultat.put(stationAffectee, myHmResultat.get(stationAffectee)+1);
        }
        //Si 0,33 < probaAction < 0,66 enlever un vélo
        else if(probaAction >= 0.33f && probaAction < 0.66){
            myHmResultat.put(stationAffectee, myHmResultat.get(stationAffectee)-1);
        }
        //Si 0,66 < probaAction < 1 tranférer d'une station A à une station B
        else{
            //Tirage d'une deuxieme station qui interviendra dans le transfert d'un vélo
            Station stationAffectee2 = (Station) values[generator.nextInt(values.length)];
            //La première station a un vélo qui sera enlevé
            myHmResultat.put(stationAffectee, myHmResultat.get(stationAffectee)-1);
            //La deuxième station a un vélo qui sera ajouté
            myHmResultat.put(stationAffectee2, myHmResultat.get(stationAffectee2)+1);
        }
        return myHmResultat;
    }

    //Renvoie HashMap<com.stocha.Station,Integer> array
    public HashMap<Station,Integer> runRecuit(HashMap<Station,Integer> array ){
        //TODO
        return null;
    }

    public float getTemp() {
        return this.temperature;
    }

    public int getNbIterations() {
        return this.nbIterations;
    }

    public void setTemp(float newTemp) {
        this.temperature = newTemp;
    }

    public void setNbIterations(int newNbIt) {
        this.nbIterations = newNbIt;
    }

    public int getPalierTemperature(){
        return this.palierTemperature;
    }
    public void setPalierTemperature(int myPalierTemp){
        this.palierTemperature = myPalierTemp;
    }

}
