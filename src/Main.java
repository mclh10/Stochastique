public class Main {
    public static void main(String[] args) {
        System.out.println("running the program may take some time depending on the number of scenarios to generate");
        ProblemeVLS vls = new ProblemeVLS();
        vls.parseData();
        int x =1; //nb scénarios à générer
        vls.genererScenarios(x);
        System.out.println("ça compile (OK)");
    }
}
