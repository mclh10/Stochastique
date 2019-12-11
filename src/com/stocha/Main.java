package com.stocha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
        Random r = new Random();

        int i = 0;
        double[] temp = new double[n];
        int count = 0;
        int maxite = 3;
        for(Station s : vls.getMesStations()){
            if(i < maxite) {

                k[i] = (double) s.getKi();
//                if(k[i] == 0){
//                    k[i] = (double)  r.nextInt(20);
//                }
                c[i] =  (double) s.getCi();
//                if(c[i] == 0.0){
//                    c[i] = (double) r.nextInt(10);
//                }
                int j = 0;

                for (Station s2 : vls.getMesStations()) {
                    if(j<maxite) {
                        xsi[i][j] = (double) s.getXiij().get(s2);
                        if (xsi[i][j] < 0.0) {
                            temp[count] = xsi[i][j];
                            count++;
                            xsi[i][j] = 0.0;
                        }
                        j++;
                    }
                }
                i++;
            }
        }

        System.out.println("Found " + count + "xsi negative values");
        System.out.print("values : {");
        for (int h = 0; h < count; h++) {
            System.out.print(temp[h] + ", ");
        }
        System.out.println("}; ");

        System.out.println("n  = " + i);
        System.out.print("k = {");
        for (int j = 0; j < i; j++) {
            System.out.print(k[j] + ", ");
        }
        System.out.println("};");
        System.out.print("c = {");
        for (int j = 0; j < i; j++) {
            System.out.print(c[j] + ", ");
        }
        System.out.println("};");
        System.out.println("xsi = {");
        for (int j = 0; j < i; j++) {
            System.out.print("    {");
            for (int l = 0; l <i ; l++) {
                System.out.print(xsi[j][l] + ", ");
            }
            System.out.println("}; ");

        }
        System.out.println("};");



        // solving the problem with cplex
        Cplex.solve(i, k, c, v, w, xsi);

    }
}
