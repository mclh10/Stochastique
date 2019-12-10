package com.stocha.graphic;

import com.stocha.ProblemeVLS;
import com.stocha.Station;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;

enum Algo {
    RECUITDETER("Recuit déterministe"),
    RECUITSTO("Recuit stochastique"),
    CPLEX("CPLEX");

    private final String text;
    Algo(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}

public class Controller {

    @FXML
    ComboBox<Algo> algoComboBox;
    @FXML
    Spinner<Integer> stationNumberSpinner;
    @FXML
    Button calculateButton;
    @FXML
    TextArea resultTextArea;
    @FXML
    TextField filePath;

    @FXML
    public void initialize() {
        algoComboBox.getItems().addAll(Algo.values());
        filePath.setText("velib-disponibilite-en-temps reel.json");
        calculateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> run());

    }

    public void testFunction(){
        System.out.println("une fois");
    }

    private void run(){
        if(algoComboBox.getValue()!= null ) {
            calculateButton.setDisable(true);
            resultTextArea.setText("The calculation may take some time");
            new Thread(() -> {
                ProblemeVLS vls = new ProblemeVLS();
                vls.parseData();
                int x = stationNumberSpinner.getValue(); //nb scénarios à générer
                vls.genererScenarios(x);
                HashMap<Station, Integer> sol = new HashMap<>();
                for (Station s : vls.getMesStations()) {
                    sol.put(s, 0);
                }

                switch (algoComboBox.getValue()) {
                    case CPLEX:
                        break;
                    case RECUITSTO:
                        break;
                    case RECUITDETER:
                        break;
                }
                float res = vls.calculFctObjectif(sol);
                resultTextArea.setText("résultat fonction objectif : " + res);
                calculateButton.setDisable(false);
            }).start();
        }
        else{
            resultTextArea.setText("Some fields are empty or incorrect, the calculation cannot be done");
        }
    }

}
