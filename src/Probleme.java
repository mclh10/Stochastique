import java.util.ArrayList;
import java.util.HashMap;

public abstract class Probleme {

    //Variables de classe

    protected HashMap<Station,Integer> solutions;
    protected ArrayList<Scenario> mesScenarios;
    protected ArrayList<Algorithme> mesAlgos;

    //Constructeurs

    public Probleme() {
        this.solutions=new HashMap<>();
        this.mesScenarios=new ArrayList<>();
        this.mesAlgos=new ArrayList<>();
    }

    public Probleme(HashMap<Station,Integer> solutions, ArrayList<Scenario> mesScenarios, ArrayList<Algorithme> mesAlgos) {
        this.solutions = solutions;
        this.mesScenarios = mesScenarios;
        this.mesAlgos = mesAlgos;
    }

    //Getters et setters

    public HashMap<Station,Integer> getSolutions() {
        return solutions;
    }

    public ArrayList<Scenario> getMesScenarios() {
        return mesScenarios;
    }

    public ArrayList<Algorithme> getMesAlgos() {
        return mesAlgos;
    }

    public void setSolutions(HashMap<Station,Integer> solutions) {
        this.solutions = solutions;
    }

    public void setMesScenarios(ArrayList<Scenario> mesScenarios) {
        this.mesScenarios = mesScenarios;
    }

    public void setMesAlgos(ArrayList<Algorithme> mesAlgos) {
        this.mesAlgos = mesAlgos;
    }

    //méthodes abstraites de classe
    abstract public boolean verifierContraintes(HashMap<Station,Integer> currentSolution);
    abstract public float calculFctObjectif(HashMap<Station,Integer> currentSolution);
}
