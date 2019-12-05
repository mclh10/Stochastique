import java.util.ArrayList;

public abstract class Probleme {

    //Variables de classe

    protected ArrayList<Integer> solutions;
    protected ArrayList<Scenario> mesScenarios;
    protected ArrayList<Algorithme> mesAlgos;

    //Constructeurs

    public Probleme() {
        this.solutions=new ArrayList<>();
        this.mesScenarios=new ArrayList<>();
        this.mesAlgos=new ArrayList<>();
    }

    public Probleme(ArrayList<Integer> solutions, ArrayList<Scenario> mesScenarios, ArrayList<Algorithme> mesAlgos) {
        this.solutions = solutions;
        this.mesScenarios = mesScenarios;
        this.mesAlgos = mesAlgos;
    }

    //Getters et setters

    public ArrayList<Integer> getSolutions() {
        return solutions;
    }

    public ArrayList<Scenario> getMesScenarios() {
        return mesScenarios;
    }

    public ArrayList<Algorithme> getMesAlgos() {
        return mesAlgos;
    }

    public void setSolutions(ArrayList<Integer> solutions) {
        this.solutions = solutions;
    }

    public void setMesScenarios(ArrayList<Scenario> mesScenarios) {
        this.mesScenarios = mesScenarios;
    }

    public void setMesAlgos(ArrayList<Algorithme> mesAlgos) {
        this.mesAlgos = mesAlgos;
    }

    //méthodes abstraites de classe
    abstract public boolean verifierContraintes();
    abstract public float calculFctObjectif(ArrayList<Integer> currentSolution);
}
