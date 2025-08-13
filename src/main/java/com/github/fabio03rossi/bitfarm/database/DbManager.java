package com.github.fabio03rossi.bitfarm.database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager
{
    /**
     * La classe si occupa di gestire il database
     */

    public void startConnection(){
        /**
         * Avvia la connessione al database e genera le tabelle chiamando i metodi responsabili
         */
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:dbArticoli");
            Statement stmt = conn.createStatement();
            System.out.println("Connessione al database stabilita");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //...
    }


    private void creaTabella(Connection conn, Statement stmt) {
        /**
         * Genera le tabelle articoli, pacchetti e utenti
         */

        String articoliTableQuery = "CREATE TABLE IF NOT EXISTS articoli (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descrizione TEXT NOT NULL)," +
                "prezzo DOUBLE NOT NULL)," +
                "certificazioni TEXT NOT NULL)," +
                "id_venditore INTEGER NOT NULL," +
                "tipologia TEXT NOT NULL)";


        String pacchettiTableQuery = "CREATE TABLE IF NOT EXISTS pacchetti (" +
                "id_pacchetto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_articolo INTEGER NOT NULL," +
                "quantita INTEGER DEFAULT 1," +
                "FOREIGN KEY (id_articolo) REFERENCES articoli (id_articolo))";

        String utentiTableQuery = "CREATE TABLE ID NOT EXISTS utenti(" +
                "id_utente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nickname TEXT NOT NULL," +
                "data_creazione DATETIME NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL,)";

        try {
            stmt.execute(articoliTableQuery);
            stmt.execute(pacchettiTableQuery);
            stmt.execute(utentiTableQuery);
            conn.commit();
            System.out.println("Tabella articoli creata correttamente");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public void getArticolo(){

    }



}


