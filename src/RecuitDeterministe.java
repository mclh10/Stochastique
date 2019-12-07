import java.util.ArrayList;
import java.util.HashMap;

//Ici recuit appliqué à un seul scénario
public class RecuitDeterministe extends Recuit {
    @Override
    HashMap<Station,Integer> executer() {
        //TODO
        return null;
    }
    //Constructeur
    public RecuitDeterministe(float myTemp, int myNbIt, int myPalierTemp, ArrayList<Scenario> myArrayScenario) {
        super(myTemp, myNbIt, myPalierTemp, myArrayScenario);
    }

    @Override
    public HashMap<Station,Integer>  runRecuit(HashMap<Station,Integer> myHm){
        //myHm = ensemble des solutions initiales d'un scenario donné
        //Variable de retour
        HashMap<Station, Integer> myHmCourante = new HashMap<Station, Integer>();
        HashMap<Station, Integer> myHmMeilleure = new HashMap<Station, Integer>();
        HashMap<Station, Integer> myHmVoisinage = new HashMap<Station, Integer>();
        myHmCourante = myHm;
        myHmMeilleure = myHmMeilleure;

        //Initialisation temp et nb_iterations se fait deja a la creation du recuit
        //Ci-dessous choper les stations
        //Variables locales xMeilleur, xCourant, xPrime
        //Calcul de la fonction objectif
        float fMin = this.getMonProbleme().calculFctObjectif(myHmMeilleure);
        float fCourant = this.getMonProbleme().calculFctObjectif(myHmCourante);
        while (this.getTemp() >= this.getPalierTemperature()){
            while(getNbIterations() >= 0){
                //Definir le voisinage a ce moment la
                myHmVoisinage = this.calcVoisin(myHm);
                //Calcul delta fonction objectif
                //Besoin de changer la définition de la fonction Objectif paramètre --> ArrayList<Integer> a Integer
                float delta =  this.getMonProbleme().calculFctObjectif(myHmVoisinage) - this.getMonProbleme().calculFctObjectif(myHmCourante);
                if (delta < 0){
                    myHmCourante = myHmVoisinage;
                    setNbIterations(getNbIterations()-1);
                    if(fCourant < fMin){
                        fMin = fCourant;
                        myHmMeilleure = myHmCourante;
                        break;
                    }
                    else{
                        if (calculerCstGibbsBoltz(-delta, getTemp())){
                            //Xi = Xj ? What
                            setNbIterations(getNbIterations()-1);
                        }
                    }
                }
            }
            //fonction décroissante de la température
            setTemp(getTemp()-1f);
         }
        //Retour de la meilleure solution avec myHmMeilleure
        return myHmMeilleure;

    }
}
