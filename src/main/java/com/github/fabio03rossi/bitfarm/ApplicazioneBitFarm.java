package com.github.fabio03rossi.bitfarm;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.AziendaBuilder;
import com.github.fabio03rossi.bitfarm.account.TipologiaAzienda;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Applicazione di JavaFX per avvio del programma
 */
public class ApplicazioneBitFarm extends Application {
    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("BitFarm");

        var borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT, Paint.valueOf("142345"));

        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();

        AziendaBuilder builder = new AziendaBuilder();
        var azienda = builder
                .partitaIVA("")
                .email("")
                .password("")
                .nome("")
                .descrizione("")
                .indirizzo("")
                .telefono("")
                .tipologia(TipologiaAzienda.TRASFORMATORE)
                .getResult();

        borderPane = new BorderPane();
        borderPane.setCenter(new TextArea(azienda.toString()));
        scene = new Scene(borderPane, WIDTH, HEIGHT, Paint.valueOf("142345"));
        stage.setScene(scene);
    }
}