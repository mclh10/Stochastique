import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class ProblemeVLS extends Probleme {

    // Variables de classe
    private String nomFichier;
    private int velosTotaux;
    private ArrayList<Station> mesStations;

    //Constructeur
    public ProblemeVLS() {
        super();
        this.nomFichier = "velib-disponibilite-en-temps-reel.json";
        this.velosTotaux = 0;
        this.mesStations = new ArrayList<>();
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
        for (Map.Entry me : currentSolution.entrySet()) { //on parcourt les stations (i dans l'énoncé)
            Station s = (Station) me.getKey();
            int x_i = (int) me.getValue();
            if (x_i > s.getKi()) { //contrainte 1a de l'énoncé
                //System.out.println("1");
                return false;
            }
            for(Scenario scenario : this.getMesScenarios()){
                int sumImoins = 0;
                int sumXi = 0;
                int sumBeta = 0;
                for(Map.Entry me2 : currentSolution.entrySet()) { //on parcourt une deuxième fois pour avoir les j de l'énoncé
                    Station j = (Station) me2.getKey();
                    int beta_ijs = scenario.getBeta().get(s).get(j);
                    int xi_ijs = scenario.getDonnees().get(s).get(j);
                    int Imoins = beta_ijs - x_i ;//>0 ? beta_ijs -x_i :0;
                    /*if (beta_ijs != xi_ijs - Imoins) { //contrainte 1b
                        //System.out.println("2");
                        System.out.println(beta_ijs +","+xi_ijs+","+Imoins);
                        return false;
                    }*/
                    sumImoins+=Imoins;
                    sumXi+=xi_ijs;
                    sumBeta+=beta_ijs;
                }
                int Iplus = x_i - sumBeta > 0 ? x_i - sumBeta : 0 ;
                if(Iplus - sumImoins!=x_i-sumXi){ //contrainte 1c
                    //System.out.println("3");
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public float calculFctObjectif(HashMap<Station,Integer> currentSolution) {
        int sommeCX=0;
        for(Map.Entry me : currentSolution.entrySet()) {
            Station s = (Station) me.getKey();
            int x_i = (int) me.getValue();
            sommeCX += s.getCi() * s.getXi();
        }
        int sommeScenar=0;
        for(Scenario sce : this.mesScenarios) {
            int sommeAcc=0;
            for(Map.Entry me : currentSolution.entrySet()) {
                Station s = (Station) me.getKey();
                int x_i = (int) me.getValue();
                int Imoins = 0;
                int Omoins = x_i - s.getKi();
                int sumBeta_ijs=0;
                int sumBeta_jis=0;
                for(Map.Entry me2 : sce.getDonnees().entrySet()){
                    Station j = (Station) me.getKey();
                    int beta_ijs = sce.getBeta().get(s).get(j);
                    //calcul de la somme beta_ijs
                    sumBeta_ijs += beta_ijs;
                    //calcul de la somme des beta_jis
                    sumBeta_jis = sce.getBeta().get(j).get(s);
                    //calcul de la somme des I_ijs_moins
                    Imoins += beta_ijs - x_i>0 ? beta_ijs - x_i : 0;
                }
                //calcul de O_is_moins
                Omoins += sumBeta_ijs - sumBeta_jis;
                Omoins = Omoins > 0 ? Omoins : 0;
                sommeAcc += s.getVi()*Imoins+s.getWi()*Omoins;
            }
            sommeScenar += sce.getProba()*sommeAcc;
        }
        return sommeCX+sommeScenar;
    }


    public float calculFctObjSousRecuit(Scenario scenario, HashMap<Station,Integer> currentSolution, HashMap<Scenario,HashMap<Station,Integer>> lambda, HashMap<Station,Integer> phi, HashMap<Station,Integer> xref) {
        int sum1 = 0;
        int sum2 = 0;
        int sum3 = 0;
        for(Map.Entry me : currentSolution.entrySet()){
            Station i = (Station) me.getKey();
            int x_i = (int) me.getValue();
            sum1+=(i.getCi()+lambda.get(scenario).get(i)-phi.get(i)*xref.get(i))*x_i;
            sum2+=x_i*x_i;
            int sumImoins = 0;
            int beta_ijs = 0;
            int beta_jis = 0;
            for(Map.Entry me2 : currentSolution.entrySet()){
                Station j = (Station) me.getKey();
                sumImoins+=scenario.getBeta().get(i).get(j) - x_i > 0 ? scenario.getBeta().get(i).get(j) : 0;
                beta_ijs += scenario.getBeta().get(i).get(j);
                beta_jis += scenario.getBeta().get(j).get(i);
            }
            int oMoins = beta_ijs - i.getKi() + x_i + beta_jis > 0 ? beta_ijs - i.getKi() + x_i + beta_jis : 0;
            sum3 += i.getVi()*sumImoins+i.getWi()*oMoins;
        }
        return (sum1 + sum2/2 + sum3);
        /*int sommeCX=0;
        for(Map.Entry me : currentSolution.entrySet()) {
            Station s = (Station) me.getKey();
            int x_i = (int) me.getValue();
            sommeCX += s.getCi() * s.getXi();
        }
        int sommeScenar=0;
        for(Scenario sce : this.mesScenarios) {
            int sommeAcc=0;
            for(Map.Entry me : sce.getDonnees().entrySet()) {
                Station s = (Station) me.getKey();
                int x_i = (int) me.getValue();
                int Imoins = 0;
                int Omoins = x_i - s.getKi();
                int sumBeta_ijs=0;
                int sumBeta_jis=0;
                for(Map.Entry me2 : sce.getDonnees().entrySet()){
                    Station j = (Station) me.getKey();
                    int beta_ijs = sce.getBeta().get(s).get(j);
                    //calcul de la somme beta_ijs
                    sumBeta_ijs += beta_ijs;
                    //calcul de la somme des beta_jis
                    sumBeta_jis = sce.getBeta().get(j).get(s);
                    //calcul de la somme des I_ijs_moins
                    Imoins += beta_ijs - x_i>0 ? beta_ijs - x_i : 0;
                }
                //calcul de O_is_moins
                Omoins += sumBeta_ijs - sumBeta_jis;
                Omoins = Omoins > 0 ? Omoins : 0;
                sommeAcc += s.getVi()*Imoins+s.getWi()*Omoins;
            }
            sommeScenar += sce.getProba()*sommeAcc;
        }
        return sommeCX+sommeScenar;*/
    }


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
        float proba = (float) 1.0/nbScenarios;
        //génération scénario moyen
        HashMap<Station, HashMap<Station,Integer>> donneesScenarioMoy = new HashMap<>();
        for(Station s:this.getMesStations()){
            HashMap<Station,Integer> x = new HashMap<>(); //la hashMap qui contiendra les demandes stochastiques pour la station s
            for(Station sta : this.getMesStations()){
                if(s.getCode() != sta.getCode()){
                    float val = (sta.getKi()+s.getKi())/2; //moyenne entre les deux stations considérées
                    x.put(sta,(int) val);
                }
                else{
                    x.put(sta,0);
                }
            }
            donneesScenarioMoy.put(s,x);
            s.setXiij(x);
        }
        HashMap<Station,HashMap<Station,Integer>> betaScenar = new HashMap<>();
        for(Station s: this.getMesStations()){
            betaScenar.put(s,s.calculerResDemande());
        }
        Scenario scenarioMoyen = new Scenario(donneesScenarioMoy,betaScenar,new HashMap<>(),proba);

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
                    else{
                        x.put(sta,0);
                    }
                }
                donneesScenario.put(s,x);
                s.setXiij(x);
            }
            HashMap<Station,HashMap<Station,Integer>> betaScenar2 = new HashMap<>();
            for(Station s: this.getMesStations()){
                betaScenar2.put(s,s.calculerResDemande());
            }
            Scenario sce = new Scenario(donneesScenario,betaScenar2,new HashMap<>(),proba);
            listeScenarios.add(sce);
        }
        this.setMesScenarios(listeScenarios);
        return listeScenarios;
    }
}
