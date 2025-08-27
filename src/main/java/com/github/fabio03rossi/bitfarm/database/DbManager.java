package com.github.fabio03rossi.bitfarm.database;

import com.github.fabio03rossi.bitfarm.post.IArticolo;
import com.github.fabio03rossi.bitfarm.post.Pacchetto;
import com.github.fabio03rossi.bitfarm.post.Prodotto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.HashMap;

public class DbManager
{
    /**
     * La classe si occupa di gestire il database
     */
    private Connection conn;
    private Statement stmt;



// ==============================================================================
//         --- Metodi per la connessione al server SQLite ---
// ==============================================================================

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

// ==============================================================================
//         --- Metodi per la gestione dei dati ---
// ==============================================================================


    private IArticolo getProdotto(int id) throws SQLException {
        IArticolo articolo = null;
        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Leggi il tipo dall'attributo "tipo_articolo"
                    String tipoArticolo = rs.getString("tipologia");

                    articolo = new Prodotto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descrizione"),
                            rs.getDouble("prezzo"),
                            rs.getString("certificazioni")
                    );
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        return articolo;
    }

    private void setProdotto(IArticolo articolo) throws SQLException {
        String sql = "INSERT INTO articoli (nome, descrizione, prezzo, certificazioni, id_venditore, tipologia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Imposta i valori per i segnaposto in base al loro tipo
            pstmt.setString(1, articolo.getName());
            pstmt.setString(2, articolo.getDescription());
            pstmt.setDouble(3, articolo.getPrice());
            pstmt.setString(4, articolo.getCertificates());
            pstmt.setInt(5, articolo.getIdSeller());
            pstmt.setString(6, "prodotto");
            // Eseguo la query
            pstmt.executeUpdate();

            System.out.println("Prodotto " + articolo.getName() + " aggiunto!");
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
    }

    public void setArticolo(IArticolo articolo) throws SQLException {
        /**
         * Metodo per la scrittura in database di un articolo, sia esso un pacchetto che un prodotto (accetta oggetti IArticle)
         */

        // Se si tratta di un prodotto semplice, aggiungo solamente i dati relativi al prodotto
        setProdotto(articolo);

        // Se si tratta di un pacchetto, allora aggiungo anche i dati relativi ai prodotti contenuti
        if (articolo instanceof Pacchetto) {
            int idArticolo = articolo.getId();
            HashMap<Prodotto, Integer> listaProdotti = ((Pacchetto) articolo).getProductList();
            // Itero la lista e salvo ogni prodotto correlato nel database
            for (HashMap.Entry<Prodotto, Integer> coppia : listaProdotti.entrySet()) {

                Prodotto prodotto = coppia.getKey();
                int quantita = coppia.getValue();

                String sql = "INSERT INTO pacchetto (id_prodotto, quantita) VALUES (?, ?, ?)";

                try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
                    // Assegno i valori ai segnaposto
                    pstmt.setInt(1, articolo.getId());
                    pstmt.setInt(2, prodotto.getId());
                    pstmt.setInt(3, quantita);
                    // Eseguo la query
                    pstmt.executeUpdate();

                } catch (SQLException ex) {
                    throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
                }
            }
        }

        System.out.println("Articolo " + articolo.getName() + " aggiunto!");

    }
    public IArticolo getArticolo(int id) throws SQLException {

        IArticolo articolo = null;
        articolo = getProdotto(id);

        if (articolo instanceof Pacchetto) {
            // Se è un PACCHETTO, istanzia la classe Pacchetto
            // Nota: Dovrai anche recuperare i prodotti che compongono il pacchetto
            // Questo richiederà un'altra query o una join

            int id_pacchetto = articolo.getId();

            HashMap<Prodotto, Integer> listaProdotti = new HashMap<>();

            String sql = "SELECT * FROM articoli WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id_pacchetto);
                try (ResultSet rsp = stmt.executeQuery()) {
                    if (rsp.next()) {
                        Prodotto prodotto = (Prodotto) getProdotto(rsp.getInt("id_articolo"));
                        listaProdotti.put(prodotto, rsp.getInt("quantita"));
                    }
                    // Aggiungi logica per popolare la lista dei prodotti del pacchetto
                    // Esempio: recupera i prodotti e li aggiunge al pacchetto
                    // this.popolaPacchetto(pacchetto);
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Errore durante l'accesso al database: " + ex.getMessage());
            }
        }
        return articolo;
    }

    private void createTables (Connection conn, Statement stmt){
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
                "id_pacchetto INTEGER NOT NULL," +
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


}

