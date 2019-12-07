public class Main {
    public static void main(String[] args) {
        ProblemeVLS vls = new ProblemeVLS();
        vls.parseData();
        for(Station s:vls.getMesStations()){
            for(Station sta : vls.getMesStations()){
                if(s.getCode() != sta.getCode()){
                    s.ajouterDemandeStochastique(sta);
                }
            }
        }
        //System.out.println(vls.getMesStations().size());
        System.out.println("ça compile (OK)");
    }
}
