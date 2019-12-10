package com.stocha;
import java.util.ArrayList;
import java.util.HashMap;

//Ici recuit appliqué à un seul scénario
public class RecuitDeterministe extends Recuit {
    //Variables
    protected static HashMap<Station, Integer> myHmMeilleure;
    private boolean estSousRecuit;
    private HashMap<Station, Float> lambdaDeter;
    private HashMap<Station, Float> phiDeter;

    //Mes methodes ici
    @Override
    HashMap<Station,Integer> executer() {
        //TODO faut-il modifier les paramètres de la méthode runRecuit pour juste get ma variable myHmmeilleure ici
        return myHmMeilleure;
    }
    //Constructeur
    public RecuitDeterministe(float myTemp, int myNbIt, int myPalierTemp, ArrayList<Scenario> myArrayScenario, boolean myEstSousRecuit) {
        super(myTemp, myNbIt, myPalierTemp, myArrayScenario);
        this.estSousRecuit = myEstSousRecuit;
    }



    @Override
    public HashMap<Station,Integer>  runRecuit(HashMap<Station,Integer> myHm){
        //myHm = ensemble des solutions initiales d'un scenario donné
        //Variable de retour
        HashMap<Station, Integer> myHmCourante = new HashMap<Station, Integer>(myHm);
        myHmMeilleure = new HashMap<Station, Integer>(myHm);
        HashMap<Station, Integer> myHmVoisinage = new HashMap<Station, Integer>(myHm);
        myHmCourante = myHm;
        myHmMeilleure = myHmCourante;

        //Calcul de la fonction objectif
        float fMin, fCourant, fVoisinage, delta;
        if (estSousRecuit){
            //Ici mettre la fonction objective du sous recuit
            //+ calculFctObjSousRecuit(x_i, lambda, phi, xref): float
            //TODO Changer la fonction correspondante
            fMin = this.getMonProbleme().calculFctObjectif(myHmMeilleure);
        }
        else{
            //Ici mettre la fonction objectif du recuite général
            //+ calculFctObjGenerale(x_i, lambda, phi, xref): float
            //TODO Changer la fonction correspondante
            fMin = this.getMonProbleme().calculFctObjectif(myHmMeilleure);
        }

        //Execution de l'ensemble du recuit
        while (this.getTemp() >= this.getPalierTemperature()){
            while(getNbIterations() >= 0){
                //Definir le voisinage a ce moment la
                myHmVoisinage = this.calcVoisin(myHm);
                //Calcul delta fonction objectif
                if (estSousRecuit){
                    //Ici mettre la fonction objective du sous recuit
                    //+ calculFctObjSousRecuit(x_i, lambda, phi, xref): float
                    //TODO Changer la fonction correspondante
                    fCourant = this.getMonProbleme().calculFctObjectif(myHmCourante);
                    fVoisinage = this.getMonProbleme().calculFctObjectif(myHmVoisinage);
                }
                else{
                    //Ici mettre la fonction objectif normale
                    fCourant = this.getMonProbleme().calculFctObjectif(myHmCourante);
                    fVoisinage = this.getMonProbleme().calculFctObjectif(myHmVoisinage);
                }
                delta =  fVoisinage - fCourant;
                if (delta < 0){
                    myHmCourante = myHmVoisinage;
                    setNbIterations(getNbIterations()-1);
                    if(fCourant < fMin){
                        fMin = fCourant;
                        myHmMeilleure = myHmCourante;
                        break;
                    }
                }
                else{
                    if (calculerCstGibbsBoltz(-delta, getTemp())){
                        //Xi = Xj ? What
                        myHmCourante = myHmVoisinage;
                        setNbIterations(getNbIterations()-1);
                    }
                }
            }
            //fonction décroissante de la température
            setTemp(getTemp()-1f);
         }
        //Retour de la meilleure solution avec myHmMeilleure
        return myHmMeilleure;
    }
}
