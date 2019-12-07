import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


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

    //paramètre : nb de scénarios à générer
    public ArrayList<Scenario> genererScenarios(int nbScenarios){
        //génération scénario moyen
        HashMap<Station, HashMap<Station,Integer>> donneesScenarioMoy = new HashMap<>();
        for(Station s:this.getMesStations()){
            HashMap<Station,Integer> x = new HashMap<>(); //la hashMap qui contiendra les demandes stochastiques pour la station s
            for(Station sta : this.getMesStations()){
                if(s.getCode() != sta.getCode()){
                    float val = (sta.getKi()+s.getKi())/2; //moyenne entre les deux stations considérées
                    x.put(sta,(int) val);
                }
            }
            donneesScenarioMoy.put(s,x);
            s.setXiij(x);
        }
        Scenario scenarioMoyen = new Scenario(donneesScenarioMoy,new HashMap<>());

        //création des scénarios à partir du scénario moyen
        ArrayList<Scenario> listeScenarios = new ArrayList<>();
        listeScenarios.add(scenarioMoyen);
        for(int i=0;i<nbScenarios;i++){ //on va créer le nb de scénarios désiré
            HashMap<Station, HashMap<Station,Integer>> donneesScenario = new HashMap<>();
            for(Station s:this.getMesStations()){
                HashMap<Station,Integer> x = new HashMap<>();
                for(Station sta : this.getMesStations()){
                    if(s.getCode() != sta.getCode()){
                        int valSceMoy = scenarioMoyen.getDonnees().get(s).get(sta);
                        double rand = ThreadLocalRandom.current().nextGaussian(); //les scénarios sont répartis selon une gaussienne
                        x.put(sta,valSceMoy+(int)(rand*10));
                    }
                }
                donneesScenario.put(s,x);
            }
            Scenario sce = new Scenario(donneesScenario,new HashMap<>());
            listeScenarios.add(sce);
        }
        return listeScenarios;
    }
}
