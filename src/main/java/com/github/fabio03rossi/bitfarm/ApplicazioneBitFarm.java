package com.github.fabio03rossi.bitfarm;

import javafx.application.Application;
import javafx.scene.Scene;
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

        Scene scene = new Scene(new BorderPane(), WIDTH, HEIGHT, Paint.valueOf("142345"));

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }
}