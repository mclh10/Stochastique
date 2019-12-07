import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("running the program may take some time depending on the number of scenarios to generate");
        ProblemeVLS vls = new ProblemeVLS();
        vls.parseData();
        int x =1; //nb scénarios à générer
        vls.genererScenarios(x);
        for(Station s : vls.getMesStations()){
            s.calculerResDemande();
        }
        HashMap<Station,Integer> sol = new HashMap<>();
        for(Station s : vls.getMesStations()){
            sol.put(s,0);
        }
        if(vls.verifierContraintes(sol)){
            System.out.println(vls.calculFctObjectif(sol));
        }
        System.out.println("ça compile (OK)");
    }
}
