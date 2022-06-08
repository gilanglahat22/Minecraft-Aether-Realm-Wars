package com.aetherwars.controller;

import com.aetherwars.model.Card;
import com.aetherwars.model.Summon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    @FXML private VBox summon0;
    @FXML private VBox summon1;
    @FXML private VBox summon2;
    @FXML private VBox summon3;
    @FXML private VBox summon4;
    @FXML private SummonController summon0Controller;
    @FXML private SummonController summon1Controller;
    @FXML private SummonController summon2Controller;
    @FXML private SummonController summon3Controller;
    @FXML private SummonController summon4Controller;

    public List<VBox> summonBoxes = new ArrayList<>();
    public List<SummonController> summonControllers = new ArrayList<>();

    /** Mengeset summon pada posisi idx, otomatis layout terupdate
     * Isi summmon dengan null untuk menghapus summon pada posisi idx */
    public void setSummon(Summon summon, int idx){
        this.summonControllers.get(idx).setSummon(summon);
    }

    public void updateSummon(List<Summon> summons){
        int i;
        for(i = 0; i < summons.size(); i++){
            this.setSummon(summons.get(i), i);
        }
        for(; i < 5; i++){
            this.setSummon(null, i);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        summonBoxes.add(summon0);
        summonBoxes.add(summon1);
        summonBoxes.add(summon2);
        summonBoxes.add(summon3);
        summonBoxes.add(summon4);
        summonControllers.add(summon0Controller);
        summonControllers.add(summon1Controller);
        summonControllers.add(summon2Controller);
        summonControllers.add(summon3Controller);
        summonControllers.add(summon4Controller);
        int i = 0;
        for(SummonController s : this.summonControllers){
            s.setSummonExpLevel((char)('A' + i) + "");
            i++;
        }
    }
}
