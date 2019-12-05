import java.util.HashMap;

public class Station {
    private int idStation;
    private int c_i;
    private int v_i;
    private int w_i;
    private int k_i;
    private int x_i;
    private String nomStation;
    private HashMap<Station,Integer> xi_ij;

    //constructeur
    public Station(int idStation, int k_i, String nomStation) {
        this.idStation = idStation;
        this.k_i = k_i;
        this.nomStation = nomStation;
    }

    //getters et setters
    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }

    public int getC_i() {
        return c_i;
    }

    public void setC_i(int c_i) {
        this.c_i = c_i;
    }

    public int getV_i() {
        return v_i;
    }

    public void setV_i(int v_i) {
        this.v_i = v_i;
    }

    public int getW_i() {
        return w_i;
    }

    public void setW_i(int w_i) {
        this.w_i = w_i;
    }

    public int getK_i() {
        return k_i;
    }

    public void setK_i(int k_i) {
        this.k_i = k_i;
    }

    public int getX_i() {
        return x_i;
    }

    public void setX_i(int x_i) {
        this.x_i = x_i;
    }

    public String getNomStation() {
        return nomStation;
    }

    public void setNomStation(String nomStation) {
        this.nomStation = nomStation;
    }

    public HashMap<Station, Integer> getXi_ij() {
        return xi_ij;
    }

    public void setXi_ij(HashMap<Station, Integer> xi_ij) {
        this.xi_ij = xi_ij;
    }

    //to string

    @Override
    public String toString() {
        return "Station{" +
                "id de la station=" + idStation +
                ", capacite=" + k_i +
                ", nom de la station='" + nomStation + '\'' +
                '}';
    }
}
