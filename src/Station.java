import java.util.HashMap;

public class Station {
    //Variables de classe
    private static int idStation=0;
    private String nomStation;
    private int c_i;
    private int v_i;
    private int w_i;
    private int x_i;
    private int k_i;
    private HashMap<Station, Integer> xi_ij;

    //Constructeur

    //Methodes de classe
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

}
