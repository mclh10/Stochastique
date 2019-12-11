package com.stocha;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import java.io.File;
import java.util.HashMap;

public class Cplex extends Algorithme {
    private File modele;
    public static void main(String[] args) {
        int n =3;
        double[] k = {8, 10, 2};
        double[] c = {3, 3, 3};
        double[] v = {2, 2, 4};
        double[] w = {4, 1, 1};
        double[][] xsi = {{0, 2, 1}, {3, 0, 2}, {3, 2, 0}};
//        System.out.println("n  = " + n);
//        System.out.print("k = {");
//        for (int j = 0; j < n; j++) {
//            System.out.print(k[j] + ", ");
//        }
//        System.out.println("};");
//        System.out.print("c = {");
//        for (int j = 0; j < n; j++) {
//            System.out.print(c[j] + ", ");
//        }
//        System.out.println("};");
//        System.out.println("xsi = {");
//        for (int j = 0; j < n; j++) {
//            System.out.print("    {");
//            for (int l = 0; l <n ; l++) {
//                System.out.print(xsi[j][l] + ", ");
//            }
//            System.out.println("}; ");
//
//        }
//        System.out.println("};");


        solve(n, k, c, v, w, xsi);

    }


    /**
     * Defines and solves the LP.
     *
     * @param n Number of stations
     * @param k Number of slot in station i
     * @param c Cost for one bike at station i
     * @param v Cost if 0 bike at station i
     * @param w Cost linked to the time lost by a user when there are no free slot at station i
     * @param xsi Demand to go from i to j
     */
    public static String solve(int n, double[] k, double[] c, double[] v, double[] w, double[][] xsi) {

        try {

            // Initialisation variables a determiner
            IloCplex cplex = new IloCplex();
            IloNumVar[] x = new IloNumVar[n];
            IloNumVar[] IPlus = new IloNumVar[n];
            IloNumVar[][] IMinus = new IloNumVar[n][n];
            IloNumVar[][] Beta = new IloNumVar[n][n];
            IloNumVar[] OPlus = new IloNumVar[n];
            IloNumVar[] OMinus = new IloNumVar[n];

            for (int i = 0; i < n; i++) {
                x[i] = cplex.numVar(0, Double.MAX_VALUE);
            }
            for (int i = 0; i < n; i++) {
                IPlus[i] = cplex.numVar(0, Double.MAX_VALUE);
                OPlus[i] = cplex.numVar(0, Double.MAX_VALUE);
                OMinus[i] = cplex.numVar(0, Double.MAX_VALUE);
                for (int j = 0; j < n; j++) {
                    Beta[i][j] = cplex.numVar(0, Double.MAX_VALUE);
                    IMinus[i][j] = cplex.numVar(0, Double.MAX_VALUE);
                }



            }

            // Objective function

            IloLinearNumExpr objective = cplex.linearNumExpr();
            for (int i = 0; i < n; i++) {
                objective.addTerm(c[i], x[i]);
                for (int j = 0; j < n; j++) {
                    objective.addTerm(v[i], IMinus[i][j]);
                }
                objective.addTerm(w[i], OMinus[i]);
            }

            cplex.addMinimize(objective);



            // Contriantes

            // 2a

            for (int i = 0; i < n; i++) {
                cplex.addGe(k[i], x[i]);

            }

            // 2b
            for (int i = 0; i < n; i++) {
                IloLinearNumExpr exp = cplex.linearNumExpr();

                for (int j = 0; j < n; j++) {
                    // TODO WHY
//                    exp.addTerm(1, Beta[i][j]);
//                    exp.addTerm(1, IMinus[i][j]);
//                    cplex.addEq(exp, xsi[i][j]); // pe mettre cplex.prod(1, Beta etc.)

                    cplex.addEq(xsi[i][j], cplex.sum(cplex.prod(1, Beta[i][j]), cplex.prod(1, IMinus[i][j]))); // pe mettre cplex.prod(1, Beta etc.)

                }


            }
            // 2c
            for (int i = 0; i < n; i++) {
                double xsitoti = 0.0;
                for (int j = 0; j < n; j++) {
                    xsitoti += xsi[i][j];
                }
                xsitoti = -xsitoti;
                cplex.addEq(xsitoti,
                        cplex.sum(
                                IPlus[i],
                                cplex.prod( -1, x[i]),
                                cplex.prod( -1, cplex.sum(IMinus[i]))
                        )
                );

            }
            //2d

            for (int i = 0; i < n; i++) {
                IloLinearNumExpr exprLinFori = cplex.linearNumExpr();
                for (int j = 0; j < n; j++) {
                    exprLinFori.addTerm(-1, Beta[i][j]);
                    exprLinFori.addTerm( 1, Beta[j][i]);
                }
//                cplex.addEq(k[i],
//                        cplex.sum(
//                                OPlus[i],
//                                cplex.prod(-1, OPlus[i]),
//                                x[i],
//                                cplex.prod(-1, exprLinFori)
//                        )

                exprLinFori.addTerm(1, OPlus[i]);
                exprLinFori.addTerm(-1, OPlus[i]);
                exprLinFori.addTerm(1, x[i]);
                cplex.addEq(k[i], exprLinFori);
            }




            //solve
            if(cplex.solve()){
                String res = " SOLVED \n";

                res += "objective_value = " + cplex.getObjValue() + '\n';
                for (int i = 0; i < n ; i++) {
                    res += "x[ " + i + " ] = " + cplex.getValue(x[i]) + '\n';
                    res += "For station " + i + " : \n";
                    for (int j = 0; j < n; j++) {
                        res+= "Beta[ " + i + " ][ " + j + " ] = " + cplex.getValue(Beta[i][j]) + '\n';
                        res += "Iminus[ " + i + " ][ " + j + " ] = " + cplex.getValue(IMinus[i][j]) + '\n';


                    }
//                    System.out.println("Reduced cost = " + cplex.getReducedCost(x[i]));
//                    System.out.println();
//
//                    System.out.println(" -----------------------------------");
//                    System.out.println("SOLVED");
//                    System.out.println(" -----------------------------------");
//                    System.out.println("objective_value = " + cplex.getObjValue());
//                    for (int i = 0; i < n ; i++) {
//                        System.out.println("x[ " + i + " ] = " + cplex.getValue(x[i]));
//                        System.out.println("For station " + i + " : ");
//                        for (int j = 0; j < n; j++) {
//                            System.out.println("Beta[ " + i + " ][ " + j + " ] = " + cplex.getValue(Beta[i][j]));
//                            System.out.println("Iminus[ " + i + " ][ " + j + " ] = " + cplex.getValue(IMinus[i][j]));
//
//
//                        }
//                        System.out.println("Reduced cost = " + cplex.getReducedCost(x[i]));
//                        System.out.println();
                }
                return res;

            } else{
                String fail = "Model not solved :(\n";
//                System.out.println("Model not solved :(");
                fail += cplex.getStatus().toString() + '\n';
//                System.out.println(cplex.getStatus().toString());
                return fail;

            }


        } catch(IloException e){
            System.out.println("cplex solve threw exception");
            e.printStackTrace();
            return "threw exception";
        }

    }

    @Override
    public HashMap<Station,Integer> executer(){

        return new HashMap<>();
    }
}
