public class Main {
    public static void main(String[] args) {
        int x =10; //nb scénarios à générer
        ProblemeVLS vls = new ProblemeVLS();
        vls.parseData();
        vls.genererScenarios(x);
        System.out.println("ça compile (OK)");
    }
}
