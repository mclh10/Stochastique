package com.stocha;

import java.util.ArrayList;
import java.util.HashMap;

//Ici c'est le recuit général
public class RecuitStochastique extends Recuit {

    //Variables
    private HashMap<Scenario,HashMap<Station, Float>> lambda;
    private HashMap<Station, Float> innerLambda;
    private HashMap<Station, Float> phi;
    private HashMap<Station, Integer> x_ref;
    private ArrayList<RecuitDeterministe> recuitDeterArray;
    protected boolean penalite = false; // A mettre dans le recuit stochastique?

    public RecuitStochastique(float myTemp, int myNbIt, int myPalierTemp, ArrayList<Scenario> myScenario) {
        super(myTemp, myNbIt, myPalierTemp, myScenario);
        this.lambda = new HashMap<Scenario, HashMap<Station, Float>>();

        initializeLambda();
        initializePhi();
        initalizeXRef();
        this.recuitDeterArray = new ArrayList<RecuitDeterministe>();
    }

    //Methodes de classe
    // Renvoie ensemble des solutions
    @Override
    HashMap<Station,Integer> executer() {
        //TODO faut-il modifier les paramètres de la méthode runRecuit pour juste get ma variable x_ref ici
        this.runRecuit();
        return this.x_ref;
    }

    //On check si la somme finale de tous les valeurs sommées pour une clé donné sont égales <=>
    public boolean egaliteSolutions(HashMap<Station, Integer> myHm, int nbScenarios) {
        for (Station s : myHm.keySet()){
            if((myHm.get(s) % nbScenarios) != 0) {
                return false;
            }
        }
        return true;
    }

    //Methode permettant d'initialiser la HashMap Lambda
    // A N'UTILISER QUE DANS LE CONSTRUCTEUR
    public void initializeLambda(){
        this.lambda = new HashMap<Scenario, HashMap<Station, Float>>();
        this.innerLambda = new HashMap<Station, Float>();
        for (Scenario sc : this.getMonProbleme().getMesScenarios()){
            for (Station st : this.getMonProbleme().getSolutions().keySet()){
                innerLambda.put(st, 0f);
            }
            lambda.put(sc, innerLambda);
        }

    }
    //Methode permettant d'initialiser la HashMap PHI
    // A N'UTILISER QUE DANS LE CONSTRUCTEUR
    public void initializePhi(){
        //TODO: Il faut changer la valeur initiale des phi
        this.phi = new HashMap<Station, Float>();
        float value;
        for (Station s : this.getMonProbleme().getSolutions().keySet()){
            value = (float)(s.getCi()/2) * s.getXi();
            phi.put(s,value);
        }
    }
    //Methode permettant d'initialiser la HashMap X_REF
    // A N'UTILISER QUE DANS LE CONSTRUCTEUR
    public void initalizeXRef(){
        this.x_ref = new HashMap<Station, Integer>();
        for (Station s : this.getMonProbleme().getSolutions().keySet()){
            x_ref.put(s,0);
        }
    }

    //Methode permettant l'ajustement des penalites
    public void ajustePenalite(Scenario myScenario, HashMap<Station, Integer> xCourant, HashMap<Station, Integer> xMoyen) {
        float alpha = 5f;
        float lambdaValue = 0f;
        for (Station s : this.phi.keySet()) {
            //Ajustement de la penalite pour Phi
            this.phi.put(s, this.phi.get(s) * alpha);
            //Ajustement de la penalite pour lambda
            lambdaValue = this.innerLambda.get(s) + this.phi.get(s) * ((float) xCourant.get(s) - (float) xMoyen.get(s));
            this.innerLambda.put(s, lambdaValue);
            this.lambda.put(myScenario, this.innerLambda);
        }
    }

    public void runRecuit(){
        RecuitDeterministe rDeterministe;
        HashMap<Station, Integer> myRecuitDeterResultat = new HashMap<Station, Integer>();
        int nbMaxIterationRecuitStocha = 5;
        //Pour chacun de mes scénarios
        //On répète la boucle tant qu'on n'a pas égalité des solutions entre les scénarios ou que le nombre d'itération max est atteint
        while (this.egaliteSolutions(x_ref, this.getMonProbleme().mesScenarios.size()) || nbMaxIterationRecuitStocha < 0) {
            for (int i = 0; i < this.getMonProbleme().getMesScenarios().size(); i++){
                //TODO Faut-il supprimer tous les recuits avant? Pour moi oui
                //recuitDeterArray.clear();
                //Constuction d'un nouveau recuit déterministe
                rDeterministe = new RecuitDeterministe(this.getTemp(),this.getNbIterations(), this.getPalierTemperature(), this.getMonProbleme().getMesScenarios(), true);
                //Obtention des résultats pour un scenario donne
                myRecuitDeterResultat = rDeterministe.runRecuit(this.getMonProbleme().getMesScenarios().get(i).getSolutionScenario());
                //Ajout dans x_ref <=> qui stocke ensemble des résultats pour tous les scénarios en sommant les valeurs obtenues
                // Par clé, valeurS
                for (Station s: myRecuitDeterResultat.keySet()){
                    //Ajout des nouvelles valeurs obtenues du recuit deterministe dans le x_ref
                    //Ici on fait la somme des valeurs obtenues pour tous les scénarios
                    x_ref.put(s, x_ref.get(s) + myRecuitDeterResultat.get(s));
                    recuitDeterArray.add(rDeterministe);
                }
            }//Fin de la boucle
            if (egaliteSolutions(x_ref, this.getMonProbleme().mesScenarios.size())){
                //L'ensemble des solutions sont égales
                for (Station s: x_ref.keySet()){
                    //Calcul moyenne entière avec myMoyenne
                    int myMoyenne = Math.round(((float)x_ref.get(s) / (float)this.getMonProbleme().getMesScenarios().size())) ;
                    //Modification de la valeur de x_ref en fonction de la clé station s
                    x_ref.put(s, myMoyenne);
                }
                return;
            }
            else{
                //La boucle n'est pas encore temrinée
                for (Station s: x_ref.keySet()){
                    //Calcul moyenne entière avec myMoyenne
                    int myMoyenne = Math.round(((float)x_ref.get(s) / (float)this.getMonProbleme().getMesScenarios().size())) ;
                    //Modification de la valeur de x_ref en fonction de la clé station s
                    x_ref.put(s, myMoyenne);
                }
            }
            nbMaxIterationRecuitStocha = nbMaxIterationRecuitStocha-1;
        }
    }

}
