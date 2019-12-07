import java.util.HashMap;

public class Scenario {
    //variables de classe
    private HashMap<Station,HashMap<Station,Integer>> donnees;
    private HashMap<Station,Integer> solutionScenario;

    //constructeurs

    public Scenario() {
        this.donnees = new HashMap<>();
        this.solutionScenario = new HashMap<>();
    }

    public Scenario(HashMap<Station,HashMap<Station,Integer>> donnees, HashMap<Station,Integer> solutionScenario) {
        this.donnees = donnees;
        this.solutionScenario = solutionScenario;
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
}
