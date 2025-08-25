package com.github.fabio03rossi.bitfarm.database;

import com.github.fabio03rossi.bitfarm.post.IArticolo;
import com.github.fabio03rossi.bitfarm.post.Pacchetto;
import com.github.fabio03rossi.bitfarm.post.Prodotto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.sql.*;

public class DbManager
{
    private Connection conn;
    private Statement stmt;

    /**
     * La classe si occupa di gestire il database
     */

    public void startConnection(){
        /**
         * Avvia la connessione al database e genera le tabelle chiamando i metodi responsabili
         */
        // Effettuo la connessione al database SQLite
        connect();
        // Creo le tabelle
        createTables(this.conn, this.stmt);
    }

    private void connect(){
        try
        {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dbArticoli");
            this.stmt = conn.createStatement();
            System.out.println("Connessione al database stabilita");

        } catch (SQLException e) {
            System.err.println("DbManager: errore nella connessione al server SQLite, tipo errore: " + e.getMessage());
        }
    }



// ...

    private IArticolo getProdotto(ResultSet rs) throws SQLException {
        return new Prodotto(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("descrizione"),
                rs.getDouble("prezzo"),
                rs.getString("certificazioni")
                );
    }

    public IArticolo getArticolo(int id) throws SQLException {
        IArticolo articolo = null;
        String sql = "SELECT * FROM articoli WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Leggi il tipo dall'attributo "tipo_articolo"
                    String tipoArticolo = rs.getString("tipologia");

                    if ("prodotto".equalsIgnoreCase(tipoArticolo)) {
                        // Se è un PRODOTTO, istanzia la classe Prodotto
                        articolo = getProdotto(rs);

                    } else if ("pacchetto".equalsIgnoreCase(tipoArticolo)) {
                        // Se è un PACCHETTO, istanzia la classe Pacchetto
                        // Nota: Dovrai anche recuperare i prodotti che compongono il pacchetto
                        // Questo richiederà un'altra query o una join

                        int id_pacchetto = rs.getInt("id");

                        articolo = new Pacchetto(
                                rs.getInt("id"),
                                rs.getString("nome"),
                                rs.getString("descrizione"),
                                rs.getDouble("prezzo"),
                                rs.getString("certificazioni"),

                        );
                        // Aggiungi logica per popolare la lista dei prodotti del pacchetto
                        // Esempio: recupera i prodotti e li aggiunge al pacchetto
                        // this.popolaPacchetto(pacchetto);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore nel recupero dell'articolo: " + e.getMessage());
            throw e;
        }
        return articolo;
    }

    private void createTables(Connection conn, Statement stmt) {
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
            System.err.println("DbManager: errore nella creazione delle tabelle, tipo errore: " + e.getMessage());
        }
    }


    public void getArticolo(){

    }



}


