import java.util.ArrayList;
import java.util.HashMap;

//Ici c'est le recuit général
public class RecuitStochastique extends Recuit {

    //Variables
    private HashMap<Station, Float> lambda;
    private HashMap<Station, Float> phi;
    private HashMap<Station, Integer> x_ref;
    protected boolean penalite = false; // A mettre dans le recuit stochastique?

    public RecuitStochastique(float myTemp, int myNbIt, int myPalierTemp, ArrayList<Scenario> myScenario) {
        super(myTemp, myNbIt, myPalierTemp, myScenario);
    }

    //Methodes de classe
    // Renvoie ensemble des solutions
    @Override
    HashMap<Station,Integer> executer() {
        //TODO
        return null;
    }

    //On check si la somme finale de tous les valeurs sommées pour une clé donné sont égales <=>
    public boolean egaliteSolutions(HashMap<Station, Integer> myHm) {
        //TODO
        for (Station s : myHm.keySet()){
            if((myHm.get(s) % myHm.size()) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public HashMap<Station,Integer> runRecuit(HashMap<Station,Integer> array ){
        RecuitDeterministe rDeterministe;
        HashMap<Station, Integer> myRecuitDeterResultat = new HashMap<Station, Integer>();
        //Pour chacun de mes scénarios
        for (int i = 0; i < this.getMonProbleme().getMesScenarios().size(); i++){
            //Constuction d'un nouveau recuit déterministe
            rDeterministe = new RecuitDeterministe(this.getTemp(),this.getNbIterations(), this.getPalierTemperature(), this.getMonProbleme().getMesScenarios());
            //Obtention des résultats pour un scenario donne
            myRecuitDeterResultat = rDeterministe.runRecuit(this.getMonProbleme().getMesScenarios().get(i).getSolutionScenario());
            //Ajout dans x_ref <=> qui stocke ensemble des résultats pour tous les scénarios en sommant les valeurs obtenues
            // Par clé, valeurS
            for (Station s: myRecuitDeterResultat.keySet()){
                //Ajout des nouvelles valeurs obtenues du recuit deterministe dans le x_ref
                //Ici on fait la somme des valeurs obtenues pour tous les scénarios
                x_ref.put(s, x_ref.get(s) + myRecuitDeterResultat.get(s));
            }
        }//Fin de la boucle

        if (egaliteSolutions(x_ref)){
            //TODO
            //Ici normalement on a fini le de faire le recuit
            return x_ref;
        }
        else{
            //La boucle n'est pas encore temrinée
            for (Station s: x_ref.keySet()){
                //Alors attention ici on fait la moyenne
                //Mais est-ce que c'est une division entière? HUMMMM
                x_ref.put(s, x_ref.get(s) / x_ref.size());
            }
        }


        return null;
    }

}
