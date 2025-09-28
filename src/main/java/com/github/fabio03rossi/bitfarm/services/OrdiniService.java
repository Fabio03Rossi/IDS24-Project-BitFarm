package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Ordine;
import com.github.fabio03rossi.bitfarm.database.DBManager;

public class OrdiniService implements IOrdiniService {

    @Override
    public Ordine getOrdine(int id){
        DBManager db = DBManager.getInstance();
        return db.getOrdine(id);
    }

    @Override
    public void setOrdine(Ordine ordine){
        DBManager db = DBManager.getInstance();
        db.addOrdine(ordine);
    }

    @Override
    public void cancellaOrdine(Ordine ordine){
        DBManager db = DBManager.getInstance();
        db.deleteOrdine(ordine);
    }

    @Override
    public void cancellaOrdine(int id){
        DBManager db = DBManager.getInstance();
        Ordine ordine = db.getOrdine(id);
        cancellaOrdine(ordine);
    }
}
