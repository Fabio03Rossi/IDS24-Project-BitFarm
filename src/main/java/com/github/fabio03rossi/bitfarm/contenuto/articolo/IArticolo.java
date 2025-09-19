package com.github.fabio03rossi.bitfarm.contenuto.articolo;

public interface IArticolo {
    public String getName();
    public void setName(String name);
    public double getPrice();
    public void setPrice(double price);
    public String getDescription();
    public void setDescription(String description);
    public String getCertificates();
    public void setCertificates(String certificates);
    public int getIdSeller();
    public void setIdSeller(int idSeller);
    public int getId();

}
