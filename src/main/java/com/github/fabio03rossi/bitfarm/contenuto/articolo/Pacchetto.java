package com.github.fabio03rossi.bitfarm.contenuto.articolo;

import java.util.HashMap;

public class Pacchetto extends AbstractArticolo implements IArticoloComposto {
        // Coppie Prodotto - quantità
        private HashMap<Prodotto, Integer> listaProdotti;
        private String nome;
        private String description;
        private double price;
        private int id = -1;
        private String Certificazioni;

        public Pacchetto(String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String certificazioni) {
                this.nome = nomeProdotto;
                this.description = descrizioneProdotto;
                this.price = prezzoProdotto;
                this.listaProdotti = new HashMap<>();
        }

        public Pacchetto(String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String certificazioni, HashMap<Prodotto, Integer> listaProdotti) {
                this.listaProdotti = listaProdotti;
                this.nome = nomeProdotto;
                this.description = descrizioneProdotto;
                this.price = prezzoProdotto;
        }

        public void addProduct(Prodotto product, int quantity) {
                this.listaProdotti.put(product, quantity);
        }

        @Override
        public String getNome() {
                return nome;
        }

        @Override
        public void setNome(String nome) {
                this.nome = nome;
        }

        @Override
        public double getPrezzo() {
                return 0;
        }

        @Override
        public void setPrice(double price) {

        }

        @Override
        public String getDescrizione() {
                return this.description;
        }

        @Override
        public void setDescrizione(String description) {
                this.description = description;
        }

        @Override
        public String getCertificati() {
                return "";
        }

        @Override
        public void setCertificati(String certificates) {

        }

        @Override
        public int getId() {
                return this.id;
        }

        @Override
        public String getTipologia() {
                return "pacchetto";
        }

        @Override
        public HashMap<Prodotto, Integer> getProductList() {
                return listaProdotti;
        }
}
