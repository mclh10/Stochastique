package com.stocha;

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



        // parsing the vls problem into cplex

        int n = vls.getMesStations().size(); // number of station
        double[] k = new double[n]; // slot by station
        double[] c = new double[n]; // cost of a bike by station
        double[] v = new double[n]; // cost if 0 bike at station i
        double[] w = new double[n]; // time cost for the user if station i is full
        double[][] xsi = new double[n][n]; // demand from station i to j


        // initializing xsi to 0 in case the parsing fails
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                xsi[i][j] = 0;
//            }
//
//        }

        // parsing the vls problem into the variables for cplex
        int i = 0;
        for(Station s : vls.getMesStations()){
            k[i] = s.getKi();
            c[i] = s.getCi();
            int j = 0;
            for(Station s2 : vls.getMesStations()){
                xsi[i][j]= (double) s.getXiij().get(s2);
                j++;
            }
            i++;
        }



        // solving the problem with cplex
        Cplex.solve(n, k, c, v, w, xsi);

    }
}
