import java.util.HashMap;


public abstract class Recuit extends Algorithme {
    //Variables de classe
    private float temperature;
    private int nbIterations;
    private int palierTemperature;

    //M�thodes de classe

    public boolean calculerCstGibbsBoltz(int a, int b) {
        //TODO
        return false;
    }

    public HashMap<Station,Integer> calcVoisin(HashMap<Station,Integer> array) {
        //TODO
        return null;
    }

    public HashMap<Station,Integer> recuit(HashMap<Station,Integer> array ){
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
