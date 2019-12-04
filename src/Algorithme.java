import java.util.ArrayList;

public abstract class Algorithme {
    private Probleme probleme;
    Probleme getMonProbleme(){
        return probleme;
    }
    void setMonProbleme(Probleme pb){
        this.probleme = pb;
    }
    abstract ArrayList<Integer> executer();
}
