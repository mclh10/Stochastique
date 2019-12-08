import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("running the program may take some time depending on the number of scenarios to generate");
        ProblemeVLS vls = new ProblemeVLS();
        vls.parseData();
        int x =1; //nb scénarios à générer
        vls.genererScenarios(x);
        HashMap<Station,Integer> sol = new HashMap<>();
        HashMap<Station,Integer> phi = new HashMap<>();
        for(Station s : vls.getMesStations()){
            sol.put(s,0);
            phi.put(s,1);
        }
        if(vls.verifierContraintes(sol)){
            System.out.println(vls.calculFctObjectif(sol));
        }
        System.out.println(vls.calculFctObjectif(sol));
        HashMap<Scenario,HashMap<Station, Integer>> lambda = new HashMap<>();
        Scenario scen =vls.getMesScenarios().get(0);
        lambda.put(scen,phi);
        System.out.println(vls.calculFctObjSousRecuit(scen,sol,lambda,phi,sol));
        //System.out.println(vls.calculFctObjGenerale(sol,lambda,phi,sol));
        System.out.println("ça compile (OK)");
    }
}
