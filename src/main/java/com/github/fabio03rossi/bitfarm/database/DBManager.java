package com.github.fabio03rossi.bitfarm.database;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.acquisto.Ordine;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.*;
import java.util.Date;

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
            this.conn = DriverManager.getConnection("jdbc:sqlite:dbArticoli");
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

        String ordiniArticoliTableQuery = "CREATE TABLE IF NOT EXISTS ordini_articoli (" +
                "id_ordine INTEGER NOT NULL," +
                "id_articolo INTEGER NOT NULL," +
                "quantita INTEGER NOT NULL," +
                "PRIMARY KEY (id_ordine, id_articolo)," +
                "FOREIGN KEY (id_ordine) REFERENCES ordini(id)," +
                "FOREIGN KEY (id_articolo) REFERENCES articoli(id))";

        String pacchettiTableQuery = "CREATE TABLE IF NOT EXISTS pacchetti (" +
                "id_pacchetto INTEGER NOT NULL," +
                "id_articolo INTEGER NOT NULL," +
                "quantita INTEGER DEFAULT 1," +
                "FOREIGN KEY (id_articolo) REFERENCES articoli (id_articolo))";

        String utentiTableQuery = "CREATE TABLE IF NOT EXISTS utenti(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nickname TEXT NOT NULL," +
                "data_creazione DATETIME NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL)";

        String aziendeTableQuery = "CREATE TABLE IF NOT EXISTS aziende(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "descrizione TEXT," +
                "indirizzo TEXT NOT NULL," +
                "tipologia TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "partita_iva TEXT NOT NULL," +
                "certificazione TEXT)";

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



        try {
            stmt.execute(articoliTableQuery);
            stmt.execute(pacchettiTableQuery);
            stmt.execute(utentiTableQuery);
            stmt.execute(aziendeTableQuery);
            stmt.execute(ordiniTableQuery);
            stmt.execute(eventiTableQuery);
            stmt.execute(ordiniArticoliTableQuery);

            conn.commit();

        } catch (SQLException e) {
            log.error("DbManager: errore nella creazione delle tabelle, tipo errore: " + e.getMessage());
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
                    log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
                }
            }
        }

        log.info("Articolo " + articolo.getNome() + " aggiunto!");

    }

    public IArticolo getArticolo(int id) {

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
                    nome = rs.getString("nome");
                    descrizione = rs.getString("descrizione");
                    prezzo = rs.getDouble("prezzo");
                    certificazioni = rs.getString("certificazioni");
                    tipologia = rs.getString("tipologia");
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }


        // Controllo se il record è un pacchetto o un prodotto
        if (Objects.equals(tipologia, "pacchetto")) {
            // Se è un PACCHETTO, istanzia la classe Pacchetto
            articolo = getPacchetto(id, nome, descrizione, prezzo, certificazioni);
        }
        if(Objects.equals(tipologia, "prodotto")){
            articolo = getProdotto(id);
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

    private Prodotto getProdotto(int id) {
        Prodotto prodotto = null;
        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Leggi il tipo dall'attributo "tipo_articolo"
                    String tipoArticolo = rs.getString("tipologia");

                    prodotto = new Prodotto(
                            rs.getString("nome"),
                            rs.getString("descrizione"),
                            rs.getDouble("prezzo"),
                            rs.getString("certificazioni")
                    );
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }
        return prodotto;
    }

    private Pacchetto getPacchetto(int id, String nome, String descrizione, double prezzo, String certificazioni) {
        Pacchetto pacchetto = new Pacchetto(nome, descrizione, prezzo, certificazioni);

        int id_pacchetto = id;

        String sql = "SELECT * FROM articoli WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_pacchetto);
            try (ResultSet rsp = stmt.executeQuery()) {
                if (rsp.next()) {
                    Prodotto prodotto = getProdotto(rsp.getInt("id_articolo"));
                    pacchetto.addProduct(prodotto, rsp.getInt("quantita"));
                }
            }
        } catch (SQLException ex) {
            log.error("Errore durante l'accesso al database: " + ex.getMessage());
        }

        return pacchetto;
    }

    private void addProdotto(IArticolo articolo) {
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
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
        }
        return evento;
    }

    public void addEvento(Evento evento){
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
            pstmt.setString(6, evento.getPosizione().toString());
            // Eseguo la query
            pstmt.executeUpdate();

            log.info("Evento " + evento.getNome() + " aggiunto!");
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
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
            // Gestisci l'errore, ad esempio stampando un messaggio
            log.error("DBManager: Errore durante la cancellazione: " + ex.getMessage());
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
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'evento: " + ex.getMessage());
        }
    }

    // // --------------- UTENTI ---------------

    public Utente getUtente(int id) {

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
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
        }
        return utente;
    }

    public void addUtente(Utente utente) {
        String sql = "INSERT INTO utenti(nickname, data_creazione, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            // Set values for placeholders
            pstmt.setString(1, utente.getNickname());

            java.sql.Date sqlDate = new java.sql.Date(utente.getDataCreazione().getTime());
            pstmt.setDate(2, sqlDate);

            pstmt.setString(3, utente.getEmail());
            pstmt.setString(4, utente.getPassword());

            // Execute the query
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage());
        }

        log.info("Utente " + utente.getNickname() + " aggiunto!");
    }

    public void updateUtente(Utente utente) {
        String sql = "UPDATE utenti SET nickname = ?, email = ?, password = ? WHERE id_utente = ?";
        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, utente.getNickname());
            pstmt.setString(2, utente.getEmail());
            pstmt.setString(3, utente.getPassword());
            pstmt.setInt(4, utente.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("DBManager: Utente " + utente.getNickname() + " aggiornato correttamente!");
            } else {
                log.info("DBManager: Nessun utente trovato con ID " + utente.getId() + " da aggiornare.");
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'utente: " + ex.getMessage());
        }
    }

    // --------------- ACCOUNT AZIENDALI ---------------

    public Azienda getAzienda(int id) {
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
                    String email = rs.getString("email");
                    String password = rs.getString("password");

                    azienda = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);
                }
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'accesso al database: " + ex.getMessage(), ex);
        }
        return azienda;
    }

    public void addAzienda(Azienda azienda) {
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
            log.error("DbManager: Errore durante l'inserimento dell'azienda: " + ex.getMessage(), ex);
        }

        log.info("Azienda " + azienda.getNome() + " aggiunta con successo!");
    }

    public void updateAzienda(Azienda azienda) {
        String sql = "UPDATE aziende SET nome = ?, descrizione = ?, indirizzo = ?, telefono = ?, tipologia = ?, certificazione = ? WHERE id = ?";
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
            }
        } catch (SQLException ex) {
            log.error("DbManager: Errore durante l'aggiornamento dell'azienda: " + ex.getMessage());
        }
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
        }

        // 2. Cancella il record principale nella tabella ordini
        String sqlDeleteOrdine = "DELETE FROM ordini WHERE id = ?";
        try (PreparedStatement pstmtOrdine = this.conn.prepareStatement(sqlDeleteOrdine)) {
            pstmtOrdine.setInt(1, ordine.getId());
            int rowsAffected = pstmtOrdine.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Ordine " + ordine.getId() + " eliminato con successo.");
            } else {
                log.info("Nessun ordine trovato con ID " + ordine.getId() + " da eliminare.");
            }
        } catch (SQLException ex) {
            log.error("DBManager: Errore durante l'eliminazione dell'ordine: " + ex.getMessage());
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
            log.info("Ordine " + ordine.getId() + " aggiornato correttamente, inclusi gli articoli.");

        } catch (SQLException ex) {
            // In caso di errore, annulla la transazione per evitare modifiche parziali
            if (this.conn != null) {
                try {
                    log.error("Transazione annullata a causa di un errore.");
                    this.conn.rollback();
                } catch (SQLException e) {
                    log.error("Errore durante il rollback: " + e.getMessage());
                }
            }
            log.error("DBManager: Errore durante l'aggiornamento dell'ordine: " + ex.getMessage(), ex);
        } finally {
            // Ripristina l'auto-commit
            if (this.conn != null) {
                this.conn.setAutoCommit(true);
            }
        }
    }

}

