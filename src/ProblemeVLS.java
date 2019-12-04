import java.util.ArrayList;

public class ProblemeVLS extends Probleme {

    //Variables de classe
    private String nomFichier;
    private int velosTotaux;
    private ArrayList<Station> mesStations;

    //Constructeur

    public ProblemeVLS() {
        this.nomFichier="";
        this.velosTotaux=0;
    }

    //Getters et setters

    public int getVelosTotaux() {
        return velosTotaux;
    }

    public void setVelosTotaux(int velosTotaux) {
        this.velosTotaux = velosTotaux;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public ArrayList<Station> getMesStations() {
        return mesStations;
    }

    public void setMesStations(ArrayList<Station> mesStations) {
        this.mesStations = mesStations;
    }

    //Méthodes de Probleme
    @Override
    public boolean verifierContraintes() {
        //TODO
        return false;
    }

    @Override
    public float calculFctObjectif(ArrayList<Integer> currentSolution) {
        //TODO
        return 0;
    }

    //Méthodes de la classe

    public void parseData(){
        //TODO
    }

    public ArrayList<Scenario> genererScenarios(Scenario scenarioMoyen){
        //TODO
        return new ArrayList<>();
    }
}
