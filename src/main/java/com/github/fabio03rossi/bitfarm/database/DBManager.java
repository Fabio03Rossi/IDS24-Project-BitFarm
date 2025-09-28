package com.github.fabio03rossi.bitfarm.database;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Curatore;
import com.github.fabio03rossi.bitfarm.account.GestoreDellaPiattaforma;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.acquisto.Ordine;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.exception.DatiNonTrovatiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DBManager
{
    private static final Logger log = LoggerFactory.getLogger(DBManager.class);
    /**
     * La classe si occupa di gestire il database
     */
    private Connection conn;
    private Statement stmt;

    private static DBManager instance;
    private DBManager() {}

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
            instance.startConnection();
        }
        return instance;
    }

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
            this.conn = DriverManager.getConnection("jdbc:sqlite:bitfarmDatabase");
            this.stmt = conn.createStatement();
            this.conn.setAutoCommit(false); // Disabilito l'auto-commit
            log.info("Connessione al database stabilita");

        } catch (SQLException e) {
            log.error("DbManager: errore nella connessione al server SQLite, tipo errore: " + e.getMessage());
        }
    }

    private void createTables (Connection conn, Statement stmt){
        /**
         * Genera le tabelle articoli, pacchetti e utenti
         */

        String articoliTableQuery = "CREATE TABLE IF NOT EXISTS articoli (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descrizione TEXT NOT NULL," +
                "prezzo DOUBLE NOT NULL," +
                "certificazioni TEXT NOT NULL," +
                "id_venditore INTEGER NOT NULL," +
                "tipologia TEXT NOT NULL," +
                "pubblicato BOOLEAN DEFAULT false)";

        String pacchettiTableQuery = "CREATE TABLE IF NOT EXISTS pacchetti (" +
                "id_pacchetto INTEGER NOT NULL," +
                "id_articolo INTEGER NOT NULL," +
                "quantita INTEGER DEFAULT 1," +
                "FOREIGN KEY (id_articolo) REFERENCES articoli (id_articolo))";

        String ordiniArticoliTableQuery = "CREATE TABLE IF NOT EXISTS ordini_articoli (" +
                "id_ordine INTEGER NOT NULL," +
                "id_articolo INTEGER NOT NULL," +
                "quantita INTEGER NOT NULL," +
                "PRIMARY KEY (id_ordine, id_articolo)," +
                "FOREIGN KEY (id_ordine) REFERENCES ordini(id)," +
                "FOREIGN KEY (id_articolo) REFERENCES articoli(id))";

        String ordiniTableQuery = "CREATE TABLE IF NOT EXISTS ordini(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "indirizzo TEXT NOT NULL," +
                "metodo_di_pagamento TEXT NOT NULL," +
                "id_utente INTEGER NOT NULL)";

        String eventiTableQuery = "CREATE TABLE IF NOT EXISTS eventi(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descrizione TEXT," +
                "data_creazione DATETIME NOT NULL," +
                "numero_partecipanti INTEGER," +
                "posizione TEXT NOT NULL," +
                "pubblicato BOOLEAN DEFAULT false)";

        String utentiTableQuery = "CREATE TABLE IF NOT EXISTS utenti(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "data_creazione DATETIME NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL," +
                "indirizzo TEXT NOT NULL)";

        String aziendeTableQuery = "CREATE TABLE IF NOT EXISTS aziende(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descrizione TEXT," +
                "indirizzo TEXT NOT NULL," +
                "tipologia TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "partita_iva TEXT NOT NULL," +
                "certificazioni TEXT," +
                "email TEXT," +
                "password TEXT NOT NULL," +
                "accettata BOOLEAN DEFAULT false)";

        String curatoriTableQuery = "CREATE TABLE IF NOT EXISTS curatori(" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "data_creazione DATETIME NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)";

        String gestorePiattaformaQuery = "CRATE TABLE IF NOT EXISTS gestore_piattaforma(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "data_creazione DATETIME NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)";



        try {
            stmt.execute(articoliTableQuery);
            stmt.execute(pacchettiTableQuery);

            stmt.execute(ordiniTableQuery);
            stmt.execute(ordiniArticoliTableQuery);
            stmt.execute(eventiTableQuery);

            stmt.execute(utentiTableQuery);
            stmt.execute(aziendeTableQuery);
            stmt.execute(curatoriTableQuery);
            stmt.execute(gestorePiattaformaQuery);

            conn.commit();

        } catch (SQLException e) {
            log.error("DbManager: errore nella creazione delle tabelle, tipo errore: {}", e.getMessage());
        }

        log.info("Tabelle create correttamente");

    }

// ==============================================================================
//         --- Metodi per la gestione dei dati ---
// ==============================================================================

    // --------------- ARTICOLI ---------------

    public void addArticolo(IArticolo articolo) {
        /**
         * Metodo per la scrittura in database di un articolo, sia esso un pacchetto che un prodotto (accetta oggetti IArticle)
         */

        // Se si tratta di un prodotto semplice, aggiungo solamente i dati relativi al prodotto
        addProdotto(articolo);

        // Se si tratta di un pacchetto, allora aggiungo anche i dati relativi ai prodotti contenuti
        if (articolo instanceof Pacchetto) {
            int idArticolo = articolo.getId();
            HashMap<IArticolo, Integer> listaProdotti = ((Pacchetto) articolo).getListaProdotti();
            // Itero la lista e salvo ogni prodotto correlato nel database
            for (HashMap.Entry<IArticolo, Integer> coppia : listaProdotti.entrySet()) {

                Prodotto prodotto = (Prodotto) coppia.getKey();
                int quantita = coppia.getValue();

                String sql = "INSERT INTO pacchetto (id_prodotto, quantita) VALUES (?, ?)";

                try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
                    // Assegno i valori ai segnaposto
                    pstmt.setInt(1, articolo.getId());
                    pstmt.setInt(2, prodotto.getId());
                    pstmt.setInt(3, quantita);
                    // Eseguo la query
                    pstmt.executeUpdate();

                } catch (SQLException ex) {
                    log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
                }
            }
        }

        log.info("Articolo " + articolo.getNome() + " aggiunto!");

    }

    public IArticolo getArticolo(int id) {

        IArticolo articolo = null;
        articolo = getIArticolo(id);

        if(articolo == null) {
            return null;
        }

        if(articolo instanceof Prodotto) {
            return articolo;
        }

        // Se si tratta di un pacchetto allora cerco la lista degli articoli associati

        HashMap<IArticolo, Integer> listaProdotti = new HashMap<>();

        // Estraggo i valori del record
        String sql = "SELECT * FROM pacchetti WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                // Per ogni articolo associato cerco i valori dell'articolo nella tabella articoli
                while(rs.next()) {
                    int idAssociato = rs.getInt("id_articolo");
                    IArticolo articoloAssociato = getIArticolo(idAssociato);
                    // Aggiungo l'articolo associato alla lista
                    if (listaProdotti.containsKey(articoloAssociato)) {
                        listaProdotti.replace(articoloAssociato, listaProdotti.get(articoloAssociato) + 1);
                    }else{
                        listaProdotti.put(articoloAssociato, 1);
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }

        if(articolo instanceof Pacchetto pacchetto) {
            pacchetto.setListaProdotti(listaProdotti);
        }

        return articolo;
    }

    public void updateArticolo(IArticolo articolo) {
        String sql = "UPDATE articoli SET nome = ?, descrizione = ?, prezzo = ?, certificazioni = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, articolo.getNome());
            pstmt.setString(2, articolo.getDescrizione());
            pstmt.setDouble(3, articolo.getPrezzo());
            pstmt.setString(4, articolo.getCertificati());
            pstmt.setInt(5, articolo.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Articolo " + articolo.getNome() + " aggiornato correttamente!");
            } else {
                log.info("Nessun articolo trovato con ID " + articolo.getId() + " da aggiornare.");
                throw new DatiNonTrovatiException(String.format("Non è presente alcun articolo con ID %d", articolo.getId()));
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'articolo: " + ex.getMessage());
        }
    }

    public List<IArticolo> getArticoliNonPubblicati() {
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
                        listaArticoli.add(new Prodotto(nome, descrizione, prezzo, certificazioni));
                    } else if (tipologia.equals("pacchetto")) {

                    }
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        return listaArticoli;
    }

    public void pubblicaArticolo(int id){
        String sql = "UPDATE articoli SET pubblicato = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        log.info("DBManager: Articolo con id " + id + " pubblicato!");
    }

    public void rifiutaArticolo(int id){
        String sql = "DELETE FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            // Gestisci l'errore, ad esempio stampando un messaggio
            log.error("DBManager: Errore durante la cancellazione: " + ex.getMessage());
        }
        log.info("DBManager: Proposta di articolo con id " + id + " cancellato!");

    }

    public void cancellaArticolo(int id) {
        String sql = "DELETE FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Assumendo che Ordine abbia un getId()
            log.info("DBManager: Evento cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione dell'evento: " + e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public List<IArticolo> getAllArticoli() {
        String sql = "SELECT id FROM articoli";
        return this.getListaArticoli(sql);
    }

    public List<IArticolo> getAllRichiesteArticoli(){
        String sql = "SELECT id FROM articoli WHERE pubblicato IS false";
        return this.getListaArticoli(sql);
    }

    public List<IArticolo> getAllArticoliAccettati(){
        String sql = "SELECT id FROM articoli WHERE pubblicato IS true";
        return this.getListaArticoli(sql);
    }

    private List<IArticolo> getListaArticoli(String sql) {
        List<IArticolo> listaArticoli = null;

        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            // Per ogni articolo associato cerco i valori dell'articolo nella tabella articoli
            while (rs.next()) {
                int id = rs.getInt("id");
                listaArticoli.add(getIArticolo(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaArticoli;
    }



    private IArticolo getIArticolo(int id) {
        IArticolo articolo = null;
        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.wasNull()) throw new DatiNonTrovatiException("Non è stato trovato alcun risultato.");
                if (rs.next()) {
                    // Leggi il tipo dall'attributo "tipo_articolo"
                    String tipoArticolo = rs.getString("tipologia");
                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    double prezzo = rs.getDouble("prezzo");
                    String certificazioni = rs.getString("certificazioni");

                    if(Objects.equals(tipoArticolo, "prodotto")) {
                        articolo = new Prodotto(nome, descrizione, prezzo, certificazioni);
                    } else if (Objects.equals(tipoArticolo, "pacchetto")) {
                        articolo = new Pacchetto(nome, descrizione, prezzo, certificazioni);
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return articolo;
    }

    private Pacchetto getPacchetto(int id, String nome, String descrizione, double prezzo, String certificazioni) {
        Pacchetto pacchetto = new Pacchetto(nome, descrizione, prezzo, certificazioni);

        int id_pacchetto = id;

        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_pacchetto);
            try (ResultSet rsp = stmt.executeQuery()) {
                if (rsp.next()) {
                    Prodotto prodotto = (Prodotto) getIArticolo(rsp.getInt("id_articolo"));
                    pacchetto.addProduct(prodotto, rsp.getInt("quantita"));
                }
            }
        } catch (SQLException ex) {
            log.error("Errore durante l'accesso al database: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }

        return pacchetto;
    }

    private void addProdotto(IArticolo articolo) {
        String sql = "INSERT INTO articoli (nome, descrizione, prezzo, certificazioni, id_venditore, tipologia, pubblicato) VALUES (?, ?, ?, ?, ?, ?, ?)";
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

            log.info("Prodotto " + articolo.getNome() + " aggiunto!");
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());

        }
    }

    // // --------------- EVENTI ---------------

    public Evento getEvento(int id) {
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

                    evento = new Evento(nome, descrizione, data , posizione);
                    evento.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return evento;
    }

    public void addEvento(Evento evento){
        /**
         *
         */
        String sql = "INSERT INTO eventi (nome, descrizione, data_creazione, numero_partecipanti, posizione) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Imposta i valori per i segnaposto in base al loro tipo

            pstmt.setString(1, evento.getNome());
            pstmt.setString(2, evento.getDescrizione());

            java.sql.Date sqlDate = new java.sql.Date(evento.getData().getTime());
            pstmt.setDate(3, sqlDate);

            pstmt.setInt(4, evento.getNumeroPartecipanti());
            pstmt.setString(5, evento.getPosizione().toString());
            // Eseguo la query
            pstmt.executeUpdate();

            log.info("Evento " + evento.getNome() + " aggiunto!");
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void pubblicaEvento(int id){
        String sql = "UPDATE eventi SET pubblicato = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        log.info("DBManager: Evento con id " + id + " pubblicato!");
    }

    public void rifiutaEvento(int id) {
        String sql = "DELETE FROM eventi WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            log.error("DBManager: Errore durante la cancellazione: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        log.info("DBManager: Proposta di evento con id " + id + " cancellato!");
    }

    public void updateEvento(Evento evento) {
        String sql = "UPDATE eventi SET nome = ?, descrizione = ?, posizione = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, evento.getNome());
            pstmt.setString(2, evento.getDescrizione());
            pstmt.setString(3, evento.getPosizione().toString());
            pstmt.setInt(4, evento.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Evento " + evento.getNome() + " aggiornato correttamente!");
            } else {
                log.info("Nessun evento trovato con ID " + evento.getId() + " da aggiornare.");
                throw new DatiNonTrovatiException(String.format("Non è presente alcun evento con ID %d", evento.getId()));
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'evento: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaEvento(int id) {
        String sql = "DELETE FROM eventi WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Assumendo che Ordine abbia un getId()
            log.info("DBManager: Evento cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione dell'evento: " + e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public List<Evento> getAllEventi() {
        String sql = "SELECT * FROM eventi";
        return this.getListaEventi(sql);
    }

    public List<Evento> getAllRichiesteEventi() {
        String sql = "SELECT * FROM eventi WHERE pubblicato IS false";
        return this.getListaEventi(sql);
    }

    public List<Evento> getAllEventiAccettati() {
        String sql = "SELECT * FROM eventi WHERE pubblicato IS true";
        return this.getListaEventi(sql);
    }

    private List<Evento> getListaEventi(String sql) {
        List<Evento> listaEventi = new ArrayList<>();
            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
                if (rs.next()) {

                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    Date data = rs.getDate("data");
                    String numeroPartecipanti = rs.getString("numero_partecipanti");
                    String posizione = rs.getString("posizione");

                    Evento evento = new Evento(nome, descrizione, data , posizione);
                    evento.setId(rs.getInt("id"));
                    listaEventi.add(evento);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        return listaEventi;
    }


    // // --------------- UTENTI ---------------

    public Utente getUtente(String email) {
        String sql = "SELECT * FROM utenti WHERE email = ?";
        Utente utente = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    String indirizzo = rs.getString("indirizzo");

                    utente = new Utente(id, nome, email, password, indirizzo);
                    utente.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: {}", ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return utente;
    }
    
    public Utente getUtente(int id) {

        String sql = "SELECT * FROM utenti WHERE id = ?";
        Utente utente = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String nome = rs.getString("nome");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String indirizzo = rs.getString("indirizzo");

                    utente = new Utente(id, nome, email, password, indirizzo);
                    utente.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return utente;
    }

    public void addUtente(Utente utente) {
        String sql = "INSERT INTO utenti(nome, data_creazione, email, password, indirizzo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Set values for placeholders
            pstmt.setString(1, utente.getNome());

            java.sql.Date sqlDate = new java.sql.Date(utente.getDataCreazione().getTime());
            pstmt.setDate(2, sqlDate);

            pstmt.setString(3, utente.getEmail());
            pstmt.setString(4, utente.getPassword());
            pstmt.setString(5, utente.getIndirizzo());

            // Execute the query
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }

        log.info("Utente " + utente.getNome() + " aggiunto!");
    }
    
    public void updateUtente(Utente utente) {
        String sql = "UPDATE utenti SET nome = ?, email = ?, password = ?, indirizzo = ? WHERE email = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, utente.getNome());
            pstmt.setString(2, utente.getEmail());
            pstmt.setString(3, utente.getPassword());
            pstmt.setString(4, utente.getIndirizzo());
            pstmt.setString(5, utente.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("DBManager: Utente {} aggiornato correttamente!", utente.getNome());
            } else {
                log.warn("DBManager: Nessun utente trovato con nome {} da aggiornare", utente.getNome());
                throw new DatiNonTrovatiException(String.format("Non è presente alcun Utente con ID %d", utente.getId()));
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'utente: {}", ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaUtente(int id) {
        String sql = "DELETE FROM eventi WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Assumendo che Ordine abbia un getId()
            log.info("DBManager: Utente cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione dell'utente: " + e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    private List<Utente> getListaUtenti() {
        String sql = "SELECT * FROM utenti";
        List<Utente> listaUtenti = new ArrayList<>();
        // Esegue la query e ottiene il set di risultati
        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            if (rs.next()) {

                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String indirizzo = rs.getString("indirizzo");
                int id = rs.getInt("id");

                Utente utente = new Utente(nome, email, password , indirizzo);
                utente.setId(rs.getInt("id"));
                listaUtenti.add(utente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaUtenti;
    }

    // --------------- ACCOUNT AZIENDALI ---------------

    public Azienda getAzienda(int id) {
        /**
         *
         */
        String sql = "SELECT * FROM aziende WHERE id = ?";
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
                    String telefono = rs.getString("telefono");
                    String tipologia = rs.getString("tipologia");
                    String certificazioni = rs.getString("certificazioni");
                    String email = rs.getString("email");
                    String password = rs.getString("password");

                    azienda = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);
                    azienda.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return azienda;
    }

    public Azienda getAzienda(String email) {
        /**
         *
         */
        String sql = "SELECT * FROM aziende WHERE email = ?";
        Azienda azienda = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String partitaIVA = rs.getString("partita_iva");
                    String nome = rs.getString("nome");
                    String descrizione = rs.getString("descrizione");
                    String indirizzo = rs.getString("indirizzo");
                    String telefono = rs.getString("telefono");
                    String tipologia = rs.getString("tipologia");
                    String certificazioni = rs.getString("certificazioni");
                    String password = rs.getString("password");

                    azienda = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);
                    azienda.setId(rs.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: {}", ex.getMessage(), ex);
        }
        return azienda;
    }

    public void addAzienda(Azienda azienda) {
        String sql = "INSERT INTO aziende (partita_iva, nome, descrizione, indirizzo, telefono, tipologia, certificazioni, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Imposta i valori per i segnaposto (?) in base ai dati dell'oggetto Azienda

            pstmt.setString(1, azienda.getPartitaIVA());
            pstmt.setString(2, azienda.getNome());
            pstmt.setString(3, azienda.getDescrizione());
            pstmt.setString(4, azienda.getIndirizzo());
            pstmt.setString(5, azienda.getTelefono());
            pstmt.setString(6, azienda.getTipologia());
            pstmt.setString(7, azienda.getCertificazioni());
            pstmt.setString(8, azienda.getPassword());

            // Esegue la query di inserimento
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'inserimento dell'azienda: " + ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }

        log.info("Azienda " + azienda.getNome() + " aggiunta con successo!");
    }

    public void updateAzienda(Azienda azienda) {
        String sql = "UPDATE aziende SET nome = ?, descrizione = ?, indirizzo = ?, telefono = ?, tipologia = ?, certificazioni = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, azienda.getNome());
            pstmt.setString(2, azienda.getDescrizione());
            pstmt.setString(3, azienda.getIndirizzo());
            pstmt.setString(4, azienda.getTelefono());
            pstmt.setString(5, azienda.getTipologia());
            pstmt.setString(6, azienda.getCertificazioni());
            pstmt.setInt(7, azienda.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("DBManager: Azienda " + azienda.getNome() + " aggiornata correttamente!");
            } else {
                log.info("DBManager: Nessuna azienda trovata con ID " + azienda.getId() + " da aggiornare.");
                throw new DatiNonTrovatiException(String.format("Non è presente alcuna Azienda con ID %d", azienda.getId()));
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'azienda: " + ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaAzienda(int id) {
        String sql = "DELETE FROM eventi WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            log.info("DBManager: Account aziendale cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione dell'account aziendale: " + e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void rifiutaAzienda(int id) {
        this.cancellaAzienda(id);
    }

    public void accettaAzienda(int id) throws SQLException {
        String sql = "UPDATE aziende SET accettata = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("DBManager: Azienda " + id + " aggiunta con successo!");
            } else {
                log.warn("DBManager: Nessuna azienda trovata con ID " + id);
            }
        }
    }

    public List<Azienda> getAllAziende(Azienda azienda) {
        String sql = "SELECT * FROM aziende";
        return this.getListaAziende(sql);
    }

    public List<Azienda> getAllRichiesteAziende(Azienda azienda) {
        String sql = "SELECT * FROM aziende WHERE accettata IS true";
        return this.getListaAziende(sql);
    }

    public List<Azienda> getAllAziendeAccettate(Azienda azienda) {
        String sql = "SELECT * FROM aziende WHERE accettata IS false";
        return this.getListaAziende(sql);
    }

    private List<Azienda> getListaAziende(String sql) {
        List<Azienda> listaAziende = new ArrayList<>();
        // Esegue la query e ottiene il set di risultati
        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            if (rs.next()) {

                String partitaIVA = rs.getString("partita_iva");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String descrizione = rs.getString("descrizione");
                String indirizzo = rs.getString("indirizzo");
                String telefono = rs.getString("telefono");
                String tipologia = rs.getString("tipologia");
                String certificazioni = rs.getString("certificazioni");
                int id = rs.getInt("id");

                Azienda azienda = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);

                azienda.setId(rs.getInt("id"));
                listaAziende.add(azienda);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaAziende;
    }

    // --------------- CURATORI ---------------

    public Curatore getCuratore(String email) {
        String sql = "SELECT * FROM curatori WHERE email = ?";
        Curatore curatore = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    String indirizzo = rs.getString("indirizzo");

                    curatore = new Curatore(email, password, nome, indirizzo);
                    curatore.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante il recupero del curatore: {}", ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return curatore;
    }

    public Curatore getCuratore(int id) {

        String sql = "SELECT * FROM curatori WHERE id = ?";
        Curatore curatore = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String email = rs.getString("email");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    String indirizzo = rs.getString("indirizzo");

                    curatore = new Curatore(email, password, nome, indirizzo);
                    curatore.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante il recupero del curatore: {}", ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return curatore;
    }

    public void addCuratore(Curatore curatore) {
        String sql = "INSERT INTO curatori (nome, data_creazione, email, password, indirizzo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Set values for placeholders
            pstmt.setString(1, curatore.getNome());

            java.sql.Date sqlDate = new java.sql.Date(curatore.getDataCreazione().getTime());
            pstmt.setDate(2, sqlDate);

            pstmt.setString(3, curatore.getEmail());
            pstmt.setString(4, curatore.getPassword());
            pstmt.setString(5, curatore.getIndirizzo());

            // Execute the query
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            log.error("DbManager: Errore durante il salvataggio di un curatore : {}", ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }

        log.info("Curatore " + curatore.getNome() + " aggiunto!");
    }

    public void updateCuratore(Curatore curatore) {
        String sql = "UPDATE utenti SET nome = ?, email = ?, password = ?, indirizzo = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, curatore.getNome());
            pstmt.setString(2, curatore.getEmail());
            pstmt.setString(3, curatore.getPassword());
            pstmt.setString(4, curatore.getIndirizzo());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("DBManager: Utente {} aggiornato correttamente", curatore.getNome());
            } else {
                log.info("DBManager: Nessun utente chiamato {} trovato", curatore.getNome());
                throw new DatiNonTrovatiException(String.format("Non è presente alcun Utente con nome %s", curatore.getNome()));
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento del curatore: {}", ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaCuratore(int id) {
        String sql = "DELETE FROM curatore WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Assumendo che Ordine abbia un getId()
            log.info("DBManager: Curatore cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione del curatore : {}", e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaCuratore(String email) {
        String sql = "DELETE FROM curatore WHERE email = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, email); // Assumendo che Ordine abbia un getId()
            log.info("DBManager: Curatore cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione del curatore : {}", e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public List<Curatore> getAllCuratori() {
        String sql = "SELECT * FROM curatori";
        List<Curatore> listaCuratori = new ArrayList<>();
        // Esegue la query e ottiene il set di risultati
        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            if (rs.next()) {

                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String indirizzo = rs.getString("indirizzo");
                int id = rs.getInt("id");

                Curatore curatore = new Curatore(email, password, nome, indirizzo);
                curatore.setId(rs.getInt("id"));
                listaCuratori.add(curatore);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaCuratori;
    }

    // --------------- GESTORI DELLA PIATTAFORMA ---------------

    public GestoreDellaPiattaforma getGestoreDellaPiattaforma(String email) {
        String sql = "SELECT * FROM gestore_piattaforma WHERE email = ?";
        GestoreDellaPiattaforma gestore = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    String indirizzo = rs.getString("indirizzo");

                    gestore = new GestoreDellaPiattaforma(email, password, nome, indirizzo);
                    gestore.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante il recupero del gestore della piattaforma: {}", ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return gestore;
    }

    public GestoreDellaPiattaforma getGestoreDellaPiattaforma(int id) {

        String sql = "SELECT * FROM gestore_piattaforma WHERE id = ?";
        GestoreDellaPiattaforma gestore = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            // Esegue la query e ottiene il set di risultati
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String email = rs.getString("email");
                    String nome = rs.getString("nome");
                    String password = rs.getString("password");
                    String indirizzo = rs.getString("indirizzo");

                    gestore = new GestoreDellaPiattaforma(email, password, nome, indirizzo);
                    gestore.setId(id);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante il recupero del gestore della piattaforma: {}", ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return gestore;
    }

    public void addGestoreDellaPiattaforma(GestoreDellaPiattaforma gestore) {
        String sql = "INSERT INTO gestore_piattaforma (nome, data_creazione, email, password, indirizzo) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Set values for placeholders
            pstmt.setString(1, gestore.getNome());

            java.sql.Date sqlDate = new java.sql.Date(gestore.getDataCreazione().getTime());
            pstmt.setDate(2, sqlDate);

            pstmt.setString(3, gestore.getEmail());
            pstmt.setString(4, gestore.getPassword());
            pstmt.setString(5, gestore.getIndirizzo());

            // Execute the query
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            log.error("DbManager: Errore durante il salvataggio del gestore della piattaforma : {}", ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }

        log.info("Gestore della piattaforma {} aggiunto", gestore.getNome());
    }

    public void updateGestoreDellaPiattaforma(GestoreDellaPiattaforma gestore) {
        String sql = "UPDATE gestore_piattaforma SET nome = ?, email = ?, password = ?, indirizzo = ? WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, gestore.getNome());
            pstmt.setString(2, gestore.getEmail());
            pstmt.setString(3, gestore.getPassword());
            pstmt.setString(4, gestore.getIndirizzo());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("DBManager: Gestore della piattaforma {} aggiornato correttamente", gestore.getNome());
            } else {
                log.warn("DBManager: Nessun gestore della piattaforma chiamato {} trovato", gestore.getNome());
                throw new DatiNonTrovatiException(String.format("Non è presente alcun Gestore con nome %s", gestore.getNome()));
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento del gestore della piattaforma: {}", ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaGestoreDellaPiattaforma(int id) {
        String sql = "DELETE FROM gestore_piattaforma WHERE id = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            log.info("DBManager: Gestore della piattaforma cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione del gestore della piattaforma : {}", e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void cancellaGestoreDellaPiattaforma(String email) {
        String sql = "DELETE FROM gestore_piattaforma WHERE email = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            log.info("DBManager: Gestore della piattaforma cancellato");
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione del gestore della piattaforma : {}", e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public List<GestoreDellaPiattaforma> getAllGestori() {
        String sql = "SELECT * FROM gestore_piattaforma";
        List<GestoreDellaPiattaforma> listaGestori = new ArrayList<>();
        // Esegue la query e ottiene il set di risultati
        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            if (rs.next()) {

                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String indirizzo = rs.getString("indirizzo");
                int id = rs.getInt("id");

                GestoreDellaPiattaforma utente = new GestoreDellaPiattaforma(email, password, nome, indirizzo);
                utente.setId(rs.getInt("id"));
                listaGestori.add(utente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaGestori;
    }

    // --------------- ORDINI ---------------

    public Ordine getOrdine(int id) {
        Ordine ordine = null;
        String sqlOrdine = "SELECT * FROM ordini WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlOrdine)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String indirizzo = rs.getString("indirizzo");
                    String metodoPagamento = rs.getString("metodo_di_pagamento");
                    int idUtente = rs.getInt("id_utente");

                    ordine = new Ordine(indirizzo, idUtente ,metodoPagamento);
                    ordine.setId(id);

                    // Recupera gli articoli correlati dalla tabella articoli_ordini
                    String sqlArticoli = "SELECT id_articolo, quantita FROM ordini_articoli WHERE id_ordine = ?";
                    try (PreparedStatement pstmtArticoli = conn.prepareStatement(sqlArticoli)) {
                        pstmtArticoli.setInt(1, id);
                        try (ResultSet rsArticoli = pstmtArticoli.executeQuery()) {
                            while (rsArticoli.next()) {
                                int idArticolo = rsArticoli.getInt("id_articolo");
                                int quantita = rsArticoli.getInt("quantita");
                                // Qui devi recuperare l'oggetto IArticolo usando l'id
                                IArticolo articolo = getArticolo(idArticolo);
                                if (articolo != null) {
                                    // Aggiungi l'articolo al carrello dell'ordine
                                    // Assumendo che esista un metodo addArticolo al Carrello
                                    ordine.getCarrello().addArticolo(articolo, quantita);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("DBManager: Errore durante il recupero dell'ordine e dei suoi articoli: " + ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
        return ordine;
    }

    public void addOrdine(Ordine ordine) {
        String sqlOrdine = "INSERT INTO ordini(indirizzo, metodo_di_pagamento, id_utente) VALUES (?, ?, ?)";
        String sqlArticoli = "INSERT INTO ordini_articoli(id_ordine, id_articolo, quantita) VALUES (?, ?, ?)";

        try {
            // Step 1: Insert the main order and get the generated ID
            int idGeneratoOrdine = 0; // Initialize the variable
            try (PreparedStatement pstmtOrdine = this.conn.prepareStatement(sqlOrdine, Statement.RETURN_GENERATED_KEYS)) {
                pstmtOrdine.setString(1, ordine.getIndirizzo());
                pstmtOrdine.setString(2, ordine.getMetodoDiPagamento());
                pstmtOrdine.setInt(3, ordine.getIdUtente());
                pstmtOrdine.executeUpdate();

                try (ResultSet rs = pstmtOrdine.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGeneratoOrdine = rs.getInt(1);
                        ordine.setId(idGeneratoOrdine);
                    } else {
                        log.error("DBManager: L'inserimento dell'ordine non ha generato un ID.");
                        this.conn.rollback(); // Rollback if no ID is generated
                        return; // Exit the method
                    }
                }
            }

            // Step 2: Insert the order items, using the generated ID
            HashMap<IArticolo, Integer> articoliNelCarrello = ordine.getCarrello().getListaArticolo();
            try (PreparedStatement pstmtArticoli = this.conn.prepareStatement(sqlArticoli)) {
                for (Map.Entry<IArticolo, Integer> entry : articoliNelCarrello.entrySet()) {
                    IArticolo articolo = entry.getKey();
                    int quantita = entry.getValue();

                    pstmtArticoli.setInt(1, idGeneratoOrdine);
                    pstmtArticoli.setInt(2, articolo.getId());
                    pstmtArticoli.setInt(3, quantita);
                    pstmtArticoli.executeUpdate();
                }
            }

            // Step 3: Commit the transaction if all operations are successful
            this.conn.commit();
            log.info("Ordine aggiunto con successo con ID: " + idGeneratoOrdine);

        } catch (SQLException e) {
            log.error("DBManager: Errore durante l'inserimento dell'ordine: " + e.getMessage());
            try {
                this.conn.rollback(); // Rollback the entire transaction on failure
            } catch (SQLException ex) {
                log.error("DBManager: Errore durante il rollback: " + ex.getMessage());
            }
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void deleteOrdine(Ordine ordine) {
        // 1. Cancella i record correlati nella tabella ordini_articoli
        String sqlDeleteArticoli = "DELETE FROM ordini_articoli WHERE id_ordine = ?";
        try (PreparedStatement pstmtArticoli = this.conn.prepareStatement(sqlDeleteArticoli)) {
            pstmtArticoli.setInt(1, ordine.getId()); // Assumendo che Ordine abbia un getId()
            pstmtArticoli.setInt(1, ordine.getId()); // Assumendo che Ordine abbia un getId()
            int rowsAffected = pstmtArticoli.executeUpdate();
            log.info("Cancellati " + rowsAffected + " articoli correlati all'ordine " + ordine.getId());
        } catch (SQLException e) {
            log.error("DBManager: Errore durante la cancellazione dell'ordine: " + e.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }

        // 2. Cancella il record principale nella tabella ordini
        String sqlDeleteOrdine = "DELETE FROM ordini WHERE id = ?";
        try (PreparedStatement pstmtOrdine = this.conn.prepareStatement(sqlDeleteOrdine)) {
            pstmtOrdine.setInt(1, ordine.getId());
            int rowsAffected = pstmtOrdine.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Ordine {} eliminato con successo.", ordine.getId());
            } else {
                log.info("Nessun ordine trovato con ID {} da eliminare.", ordine.getId());
                throw new DatiNonTrovatiException(String.format("Non è presente alcun Ordine con nome %d", ordine.getId()));
            }
        } catch (SQLException ex) {
            log.error("DBManager: Errore durante l'eliminazione dell'ordine: {}", ex.getMessage());
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        }
    }

    public void updateOrdine(Ordine ordine) throws SQLException {
        try {
            // Avvia una transazione per assicurare la coerenza dei dati
            this.conn.setAutoCommit(false);

            // 1. Aggiorna i dati principali dell'ordine
            String sqlUpdateOrdine = "UPDATE ordini SET indirizzo = ?, metodo_di_pagamento = ?, id_utente = ? WHERE id = ?";
            try (PreparedStatement pstmtOrdine = this.conn.prepareStatement(sqlUpdateOrdine)) {
                pstmtOrdine.setString(1, ordine.getIndirizzo());
                pstmtOrdine.setString(2, ordine.getMetodoDiPagamento());
                pstmtOrdine.setInt(3, ordine.getIdUtente());
                pstmtOrdine.setInt(4, ordine.getId()); // Assumendo che Ordine abbia un metodo getId()
                pstmtOrdine.executeUpdate();
            }

            // 2. Cancella i vecchi articoli correlati
            String sqlDeleteArticoli = "DELETE FROM ordini_articoli WHERE id_ordine = ?";
            try (PreparedStatement pstmtDelete = this.conn.prepareStatement(sqlDeleteArticoli)) {
                pstmtDelete.setInt(1, ordine.getId());
                pstmtDelete.executeUpdate();
            }

            // 3. Inserisci i nuovi articoli aggiornati
            String sqlInsertArticoli = "INSERT INTO ordini_articoli(id_ordine, id_articolo, quantita) VALUES (?, ?, ?)";
            try (PreparedStatement pstmtInsert = this.conn.prepareStatement(sqlInsertArticoli)) {
                // Assumendo che Carrello abbia un metodo getArticoli()
                HashMap<IArticolo, Integer> articoliNelCarrello = ordine.getCarrello().getListaArticolo();

                for (Map.Entry<IArticolo, Integer> entry : articoliNelCarrello.entrySet()) {
                    IArticolo articolo = entry.getKey();
                    int quantita = entry.getValue();

                    pstmtInsert.setInt(1, ordine.getId());
                    pstmtInsert.setInt(2, articolo.getId());
                    pstmtInsert.setInt(3, quantita);
                    pstmtInsert.executeUpdate();
                }
            }

            // Conferma la transazione
            this.conn.commit();
            log.info("Ordine {} aggiornato correttamente, inclusi gli articoli.", ordine.getId());

        } catch (SQLException ex) {
            // In caso di errore, annulla la transazione per evitare modifiche parziali
            if (this.conn != null) {
                try {
                    log.error("Transazione annullata a causa di un errore.");
                    this.conn.rollback();
                } catch (SQLException e) {
                    log.error("Errore durante il rollback: {}", e.getMessage());
                }
            }
            log.error("DBManager: Errore durante l'aggiornamento dell'ordine: {}", ex.getMessage(), ex);
            throw new DatiNonTrovatiException("Errore di lettura dei dati.");
        } finally {
            // Ripristina l'auto-commit
            if (this.conn != null) {
                this.conn.setAutoCommit(true);
            }
        }
    }

    public List<Ordine> getAllOrdini() {
        String sql = "SELECT id FROM ordini";
        List<Ordine> listaOrdini = new ArrayList<>();
        // Esegue la query e ottiene il set di risultati
        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            if (rs.next()) {
                int id = rs.getInt("id");
                listaOrdini.add(getOrdine(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaOrdini;
    }


}

