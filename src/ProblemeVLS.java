import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class ProblemeVLS extends Probleme {

    //Variables de classe
    private String nomFichier;
    private int velosTotaux;
    private ArrayList<Station> mesStations;

    //Constructeur
    public ProblemeVLS() {
        this.nomFichier="velib-disponibilite-en-temps-reel.json";
        this.velosTotaux=0;
        this.mesStations=new ArrayList<>();
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
    public boolean verifierContraintes(HashMap<Station,Integer> currentSolution) {
        //TODO
        return false;
    }

    @Override
    public float calculFctObjectif(HashMap<Station,Integer> currentSolution) {
        //TODO
        return 0;
    }

    //Méthodes de la classe

    //parsing du fichier JSON récupéré sur opendata.paris.fr
    public void parseData(){
        JSONParser parser = new JSONParser();
        String path = "deps\\"+this.getNomFichier();
        //TODO : change path to adapt to interface
        try (FileReader reader = new FileReader(path)){
            Object obj = parser.parse(reader);
            JSONArray stationList = (JSONArray) obj;
            stationList.forEach( emp -> this.parseStationObject( (JSONObject) emp ) );
        }catch(FileNotFoundException e){
            System.out.println("input file was not found");
        }catch(IOException e1){
            System.out.println("io exception was thrown");
        }catch(ParseException e2){
            System.out.println("parsing exception was thrown");
        }catch(Exception e3){
            System.out.println("an unexpected exception was raised, we decline all responsibility");
        }
    }
    //appelée par parseData(), crée toutes les instances de stations et les ajoute à mesStations
    public void parseStationObject(JSONObject obj){
        try {
            JSONObject stationObject = (JSONObject) obj.get("fields");
            String name = (String) stationObject.get("station_name");
            String code = (String) stationObject.get("station_code");
            int idStation = Integer.parseInt(code);
            int nbDocks = (int)(long)stationObject.get("nbedock");
            Station s = new Station(name,idStation, nbDocks );
            mesStations.add(s);
        }catch(RuntimeException e){
            //a key was missing, the station will be ignored during the simulation
        }
    }

    public ArrayList<Scenario> genererScenarios(Scenario scenarioMoyen){
        //TODO
        return new ArrayList<>();
    }
}
