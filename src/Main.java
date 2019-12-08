import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("running the program may take some time depending on the number of scenarios to generate");
        ProblemeVLS vls = new ProblemeVLS();
        vls.parseData();
        int x =1; //nb scénarios à générer
        vls.genererScenarios(x);
        HashMap<Station,Integer> sol = new HashMap<>();
        for(Station s : vls.getMesStations()){
            sol.put(s,0);
        }
        if(vls.verifierContraintes(sol)){
            System.out.println(vls.calculFctObjectif(sol));
        }

        //Initialisation des scenarios
        ArrayList<Scenario> myScenarioList = new ArrayList<Scenario>(vls.getMesScenarios());
        //Initialisation du recuitDeter
        RecuitDeterministe recuitDeter = new RecuitDeterministe(10, 2, 8, myScenarioList, false );

        //recuitDeter.runRecuit(vls.getMesStations().get(0).getXiij());

        System.out.println("ça compile (OK)");
    }
}
