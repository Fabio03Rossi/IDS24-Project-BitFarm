package com.github.fabio03rossi.bitfarm.post;

import java.util.HashMap;

public class Pacchetto implements IArticoloComposto {
        // Coppie Prodotto - quantità
        private HashMap<Prodotto, Integer> listaProdotti;
        private String name;
        private String description;
        private double price;
        private int id;
        private String Certificazioni;

        public Pacchetto( int id, String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String certificazioni) {
                this.name = nomeProdotto;
                this.description = descrizioneProdotto;
                this.price = prezzoProdotto;
                this.id = id;
        }

        public Pacchetto( int id, String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, String certificazioni, HashMap<Prodotto, Integer> listaProdotti) {
                this.listaProdotti = listaProdotti;
                this.name = nomeProdotto;
                this.description = descrizioneProdotto;
                this.price = prezzoProdotto;
                this.id = id;
        }

        public void addProduct(Prodotto product, int quantity) {
                this.listaProdotti.put(product, quantity);
        }

        @Override
        public String getName() {
                return name;
        }

        @Override
        public void setName(String name) {
                this.name = name;
        }

        @Override
        public double getPrice() {
                return 0;
        }

        @Override
        public void setPrice(double price) {

        }

        @Override
        public String getDescription() {
                return this.description;
        }

        @Override
        public void setDescription(String description) {
                this.description = description;
        }

        @Override
        public String getCertificates() {
                return "";
        }

        @Override
        public void setCertificates(String certificates) {

        }

        @Override
        public int getIdSeller() {
                return 0;
        }

        @Override
        public void setIdSeller(int idSeller) {

        }

        @Override
        public int getId() {
                return this.id;
        }

        @Override
        public HashMap<Prodotto, Integer> getProductList() {
                return listaProdotti;
        }
}
