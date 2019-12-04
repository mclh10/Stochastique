import java.util.ArrayList;

public class Scenario {
    //variables de classe
    private ArrayList<Integer> donnees;
    private ArrayList<Integer> solutionScenario;

    //constructeurs

    public Scenario() {
        this.donnees = new ArrayList<>();
        this.solutionScenario = new ArrayList<>();
    }

    public Scenario(ArrayList<Integer> donnees, ArrayList<Integer> solutionScenario) {
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

    public ArrayList<Integer> getSolutionScenario() {
        return solutionScenario;
    }

    public void setSolutionScenario(ArrayList<Integer> solutionScenario) {
        this.solutionScenario = solutionScenario;
    }
}
