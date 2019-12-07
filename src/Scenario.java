import java.util.ArrayList;
import java.util.HashMap;

public class Scenario {
    //variables de classe
    private ArrayList<Integer> donnees;
    private HashMap<Station,Integer> solutionScenario;

    //constructeurs

    public Scenario() {
        this.donnees = new ArrayList<>();
        this.solutionScenario = new HashMap<>();
    }

    public Scenario(ArrayList<Integer> donnees, HashMap<Station,Integer> solutionScenario) {
        this.donnees = donnees;
        this.solutionScenario = solutionScenario;
    }

    //getters et setters

    public ArrayList<Integer> getDonnees() {
        return donnees;
    }

    public void setDonnees(ArrayList<Integer> donnees) {
        this.donnees = donnees;
    }

    public HashMap<Station,Integer> getSolutionScenario() {
        return solutionScenario;
    }

    public void setSolutionScenario(HashMap<Station,Integer> solutionScenario) {
        this.solutionScenario = solutionScenario;
    }
}
