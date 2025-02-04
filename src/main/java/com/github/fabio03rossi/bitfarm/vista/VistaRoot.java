package com.github.fabio03rossi.bitfarm.vista;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public abstract class VistaRoot {
    private BorderPane root;
    private Header headerView;

    public VistaRoot(Header headerView) {
        this.headerView = headerView;
        this.root = new BorderPane();
    }

    public Parent asParent() {
        return root;
    }
}
