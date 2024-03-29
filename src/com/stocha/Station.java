package com.stocha;

import java.util.HashMap;
import java.util.Map;

public class Station {
    //Variables de classe
    private static int idStation=0;
    private String nomStation;
    private int code;
    private int c_i; //cout d'acquisition d'un velo
    private int v_i; //cout s'il manque des vélos à la station
    private int w_i; //cout pour l'utilisateur s'il ne trouve pas une place
    private int x_i; //nb de velos a affecter
    private int k_i; //capacite de la station
    private HashMap<Station, Integer> xi_ij; //demande en direction de chaque station
    private HashMap<Station, Integer> beta_ij; //nombre de vélos loués en direction de chaque station

    //Constructeur
    public Station(String nomStation, int code, int k_i) {
        this.nomStation = nomStation;
        this.code = code;
        this.k_i = k_i;
        this.c_i = 50;
        this.v_i = 30;
        this.w_i = 10;
        this.xi_ij = new HashMap<>();
        this.beta_ij = new HashMap<>();
    }

    //Methodes de classe


    public static int getIdStation() {
        return idStation;
    }

    public int getCode() {
        return code;
    }

    public int getCi() {
        return this.c_i;
    }
    public int getVi() {
        return this.v_i;
    }
    public int getWi() {
        return this.w_i;
    }
    public int getXi() {
        return this.x_i;
    }
    public int getKi() {
        return this.k_i;
    }
    public HashMap<Station, Integer> getXiij() {
        return this.xi_ij;
    }
    public HashMap<Station,Integer> getBeta_ij(){
        return this.beta_ij;
    }

    public void setCi(int ci) {
        this.c_i = ci;
    }
    public void setVi(int vi) {
        this.v_i = vi;
    }
    public void setWi(int wi) {
        this.w_i = wi;
    }
    public void setXi(int xi) {
        this.x_i = xi;
    }
    public void setKi(int ki) { this.k_i = ki; }
    public void setXiij(HashMap<Station, Integer> xiij) {
        this.xi_ij = xiij;
    }
    public void setBeta_ij(HashMap<Station,Integer> betaij){
        this.beta_ij=betaij;
    }

    @Override
    public String toString() {
        return "com.stocha.Station{" +
                "nomStation='" + nomStation + '\'' +
                ", code=" + code +
                ", k_i=" + k_i +
                '}';
    }

    /*public void ajouterDemandeStochastique(com.stocha.Station sta){
        getXiij().put(sta,ThreadLocalRandom.current().nextInt(0, 2 * k_i + 1)); //demande comprise entre 0 et 2 * capacite de la station
    }*/

    public HashMap<Station,Integer> calculerResDemande(){ //calcul des beta
        for (Map.Entry e : getXiij().entrySet()) {
            Station s = (Station) e.getKey();
            int demande = s.getXiij().get(this); //demande vers this
            if(getXi()>=demande) {
                this.beta_ij.put(s,demande);
                setXi(x_i-demande);
            }
            else{
                this.beta_ij.put(s,x_i);
                setXi(0);
            }
        }
        return this.beta_ij;
    }
}
