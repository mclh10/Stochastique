import java.util.ArrayList;


public abstract class Recuit extends Algorithme {
    //Variables de classe
    private float temperature;
    private int nbIterations;
    private int palierTemperature;

    //M�thodes de classe
    public abstract ArrayList<Integer> executer (ArrayList<Integer> array);

    public boolean calculerCstGibbsBoltz(int a, int b) {
        //TODO
        return false;
    }

    public ArrayList<Integer> calcVoisin(ArrayList<Integer> array) {
        //TODO
        return null;
    }

    public ArrayList<Integer> recuit(ArrayList<Integer> array ){
        //TODO
        return null;
    }

    public float getTemp() {
        return this.temperature;
    }

    public int getNbIterations() {
        return this.nbIterations;
    }

    public void setTemp(int newTemp) {
        this.temperature = newTemp;
    }

    public void setNbIterations(int newNbIt) {
        this.nbIterations = newNbIt;
    }

}
