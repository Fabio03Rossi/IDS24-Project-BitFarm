package com.github.fabio03rossi.bitfarm.contenuto.articolo;

import com.github.fabio03rossi.bitfarm.contenuto.IContenuto;

public interface IArticolo extends IContenuto {
    public String getNome();
    public void setNome(String nome);
    public double getPrezzo();
    public void setPrezzo(double prezzo);
    public String getDescrizione();
    public void setDescrizione(String description);
    public String getCertificati();
    public void setCertificati(String certificates);
    public int getId();
    public String getTipologia();
    public void setId(int id);
}
