package com.github.fabio03rossi.bitfarm.database;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class DBManager
{
    /**
     * La classe si occupa di gestire il database
     */
    private Connection conn;
    private Statement stmt;



// ==============================================================================
//         --- Metodi per la connessione al server SQLite ---
// ==============================================================================

    public void startConnection() {
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
                "tipologia TEXT NOT NULL" +
                "pubblicato BOOLEAN DEFAULT false)";


        String pacchettiTableQuery = "CREATE TABLE IF NOT EXISTS pacchetti (" +
                "id_pacchetto INTEGER NOT NULL," +
                "id_articolo INTEGER NOT NULL," +
                "quantita INTEGER DEFAULT 1," +
                "FOREIGN KEY (id_articolo) REFERENCES articoli (id_articolo))";

        String utentiTableQuery = "CREATE TABLE IF NOT EXISTS utenti(" +
                "id_utente INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nickname TEXT NOT NULL," +
                "data_creazione DATETIME NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL,)";

        String aziendeTableQuery = "CREATE TABLE IF NOT EXISTS aziende(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descrizione TEXT," +
                "indirizzo TEXT NOT NULL," +
                "tipologia TEXT NOT NULL" +
                "telefono TEXT NOT NULL," +
                "iva, TEXT NOT NULL," +
                "certificazione TEXT,)";

        String ordiniTableQuery = "CREATE TABLE IF NOT EXISTS ordini(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "indirizzo TEXT NOT NULL," +
                "metodo_di_pagamento TEXT NOT NULL" +
                "id_utente INTEGER NOT NULL,)";

        String eventiTableQuery = "CREATE TABLE IF NOT EXISTS eventi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL" +
                "descrizione TEXT," +
                "data_creazione DATETIME NOT NULL," +
                "numero_partecipanti INTEGER" +
                "posizione TEXT NOT NULL," +
                "pubblicato BOOLEAN DEFAULT false)";



        try {
            stmt.execute(articoliTableQuery);
            stmt.execute(pacchettiTableQuery);
            stmt.execute(utentiTableQuery);
            stmt.execute(aziendeTableQuery);
            stmt.execute(ordiniTableQuery);
            stmt.execute(eventiTableQuery);

            conn.commit();

        } catch (SQLException e) {
            System.err.println("DbManager: errore nella creazione delle tabelle, tipo errore: " + e.getMessage());
        }

        System.out.println("Tabelle create correttamente");

    }

// ==============================================================================
//         --- Metodi per la gestione dei dati ---
// ==============================================================================

    // --------------- ARTICOLI ---------------

    private Prodotto getProdotto(int id) throws SQLException {
        Prodotto prodotto = null;
        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Leggi il tipo dall'attributo "tipo_articolo"
                    String tipoArticolo = rs.getString("tipologia");

                    prodotto = new Prodotto(
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
        return prodotto;
    }



    private void setProdotto(IArticolo articolo) throws SQLException {
        String sql = "INSERT INTO articoli (nome, descrizione, prezzo, certificazioni, id_venditore, tipologia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Imposta i valori per i segnaposto in base al loro tipo
            pstmt.setString(1, articolo.getNome());
            pstmt.setString(2, articolo.getDescrizione());
            pstmt.setDouble(3, articolo.getPrezzo());
            pstmt.setString(4, articolo.getCertificati());
            pstmt.setInt(5, articolo.getId());
            pstmt.setString(6, "prodotto");
            pstmt.setBoolean(7, false);
            // Eseguo la query
            pstmt.executeUpdate();

            System.out.println("Prodotto " + articolo.getNome() + " aggiunto!");
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
    }


    public List<IArticolo> getArticoliNonPubblicati() throws SQLException {
        String sql = "SELECT * FROM articoli WHERE pubblicato IS false";
        List<IArticolo> listaArticoli = null;

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                listaArticoli = new ArrayList<IArticolo>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    double prezzo = rs.getDouble("prezzo");
                    String certificazioni = rs.getString("certificazioni");
                    int idSeller = rs.getInt("id_utente");
                    String tipologia = rs.getString("tipologia");
                    if(tipologia.equals("prodotto")) {
                        listaArticoli.add(new Prodotto(id, nome, descrizione, prezzo, certificazioni));
                    } else if (tipologia.equals("pacchetto")) {

                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        return listaArticoli;
    }

    public void pubblicaArticolo(IArticolo articolo) throws SQLException {
        String sql = "UPDATE articoli SET pubblicato = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, articolo.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        System.out.println("DBManager: Articolo " + articolo.getNome() + " pubblicato!");
    }

    public void cancellaArticolo(IArticolo articolo) throws SQLException {
        String sql = "DELETE FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, articolo.getId());
            pstmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            // Gestisci l'errore, ad esempio stampando un messaggio
            System.err.println("DBManager: Errore durante la cancellazione: " + ex.getMessage());
        }
        System.out.println("DBManager: Articolo " + articolo.getNome() + " cancellato!");

    }

    private Pacchetto getPacchetto(int id, String nome, String descrizione, double prezzo, String certificazioni) throws SQLException {
        Pacchetto pacchetto = new Pacchetto(id, nome, descrizione, prezzo, certificazioni);

        int id_pacchetto = articolo.getId();

        sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_pacchetto);
            try (ResultSet rsp = stmt.executeQuery()) {
                if (rsp.next()) {
                    Prodotto prodotto = getProdotto(rsp.getInt("id_articolo"));
                    pacchetto.addProduct(prodotto, rsp.getInt("quantita"));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Errore durante l'accesso al database: " + ex.getMessage());
        }
        articolo = pacchetto;
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

        System.out.println("Articolo " + articolo.getNome() + " aggiunto!");

    }

    public IArticolo getArticolo(int id) throws SQLException {

        IArticolo articolo = null;
        articolo = getProdotto(id);
        String nome = null;
        String descrizione = null;
        double prezzo = 0;
        String certificazioni = null;
        String tipologia = null;

        // Estraggo i valori del record
        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Leggi il tipo dall'attributo "tipo_articolo"
                    String tipoArticolo = rs.getString("tipologia");

                        nome = rs.getString("nome");
                        descrizione = rs.getString("descrizione");
                        prezzo = rs.getDouble("prezzo");
                        certificazioni = rs.getString("certificazioni");
                        tipologia = rs.getString("tipologia");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }


        // Controllo se il record è un pacchetto o un prodotto
        if (Objects.equals(tipologia, "pacchetto")) {
            // Se è un PACCHETTO, istanzia la classe Pacchetto
            Pacchetto pacchetto = new Pacchetto(id, nome, descrizione, prezzo, certificazioni);

            int id_pacchetto = articolo.getId();

            sql = "SELECT * FROM articoli WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id_pacchetto);
                try (ResultSet rsp = stmt.executeQuery()) {
                    if (rsp.next()) {
                        Prodotto prodotto = getProdotto(rsp.getInt("id_articolo"));
                        pacchetto.addProduct(prodotto, rsp.getInt("quantita"));
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Errore durante l'accesso al database: " + ex.getMessage());
            }
            articolo = pacchetto;
        }
        if(tipologia.equals("prodotto")){
            articolo = getProdotto(id);
        }
        return articolo;
    }

    // // --------------- EVENTI ---------------

    public Evento getEvento(int id) throws SQLException {
        /**
         *
         */

        String sql = "SELECT * FROM eventi WHERE id = ?";
        Evento evento = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    Date data = rs.getDate("data");
                    String numeroPartecipanti = rs.getString("numero_partecipanti");
                    String posizione = rs.getString("posizione");

                    evento = new Evento(id, nome, descrizione, data , posizione);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
        }
        return evento;
    }

    public void setEvento(Evento evento){
        /**
         *
         */
        String sql = "INSERT INTO eventi (id, nome, descrizione, data_creazione, numero_partecipanti, posizione) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Imposta i valori per i segnaposto in base al loro tipo
            pstmt.setInt(1, evento.getId());
            pstmt.setString(2, evento.getNome());
            pstmt.setString(3, evento.getDescrizione());

            java.sql.Date sqlDate = new java.sql.Date(evento.getData().getTime());
            pstmt.setDate(4, sqlDate);

            pstmt.setInt(5, evento.getNumeroPartecipanti());
            pstmt.setString(6, evento.getIndirizzo());
            // Eseguo la query
            pstmt.executeUpdate();

            System.out.println("Evento " + evento.getNome() + " aggiunto!");
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
    }

    public void pubblicaEvento(Evento evento){
        String sql = "UPDATE eventi SET pubblicato = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, evento.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        System.out.println("DBManager: Evento " + evento.getNome() + " pubblicato!");
    }

    public void cancellaEvento(Evento evento){
        String sql = "DELETE FROM eventi WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, evento.getId());
            pstmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            // Gestisci l'errore, ad esempio stampando un messaggio
            System.err.println("DBManager: Errore durante la cancellazione: " + ex.getMessage());
        }
        System.out.println("DBManager: Articolo " + evento.getNome() + " cancellato!");
    }

    // // --------------- UTENTI ---------------

    public Utente getUtente(int id) throws SQLException {

        String sql = "SELECT * FROM utenti WHERE id = ?";
        Utente utente = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String nickname = rs.getString("nickname");
                    String email = rs.getString("email");
                    String password = rs.getString("password");

                    utente = new Utente(id, nickname, email, password);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
        }
        return utente;
    }

    public void setUtente(Utente utente) {
        String sql = "INSERT INTO utenti(id, nickname, data_creazione, email, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Set values for placeholders
            pstmt.setInt(1, utente.getId());
            pstmt.setString(2, utente.getNickname());

            java.sql.Date sqlDate = new java.sql.Date(utente.getDataCreazione().getTime());
            pstmt.setDate(4, sqlDate);

            pstmt.setString(4, utente.getEmail());
            pstmt.setString(5, utente.getPassword());

            // Execute the query
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }

        System.out.println("Utente " + utente.getNickname() + " aggiunto!");
    }

    // --------------- ACCOUNT AZIENDALI ---------------

    public Azienda getAzienda(int id) throws SQLException {
        /**
         *
         */
        String sql = "SELECT * FROM utenti WHERE id = ?";
        Azienda azienda = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String partitaIVA = rs.getString("partita_iva");
                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    String indirizzo = rs.getString("indirizzo");
                    Date data = rs.getDate("data");
                    String telefono = rs.getString("telefono");
                    String tipologia = rs.getString("tipologia");
                    String certificazioni = rs.getString("certificazioni");

                    azienda = new Azienda(id, partitaIVA, nome, descrizione, indirizzo, telefono, tipologia, certificazioni);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
        }
        return azienda;
    }

    public void setAzienda(Azienda azienda) {
        String sql = "INSERT INTO aziende (id, partitaIVA, nome, descrizione, indirizzo, telefono, tipologia, certificazioni) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Imposta i valori per i segnaposto (?) in base ai dati dell'oggetto Azienda
            pstmt.setInt(1, azienda.getId());
            pstmt.setString(2, azienda.getPartitaIVA());
            pstmt.setString(3, azienda.getNome());
            pstmt.setString(4, azienda.getDescrizione());
            pstmt.setString(5, azienda.getIndirizzo());
            pstmt.setString(6, azienda.getTelefono());
            pstmt.setString(7, azienda.getTipologia());
            pstmt.setString(8, azienda.getCertificazioni());

            // Esegue la query di inserimento
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("DbManager: Errore durante l'inserimento dell'azienda: " + ex.getMessage(), ex);
        }

        System.out.println("Azienda " + azienda.getNome() + " aggiunta con successo!");
    }


}

