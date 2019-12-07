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
        return null;
    }
    public boolean egaliteSolutions() {
        //TODO
        return false;
    }

}
