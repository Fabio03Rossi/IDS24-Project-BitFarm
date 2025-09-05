package com.github.fabio03rossi.bitfarm.misc;

/**
 * Classe Posizione principalmente usata per la localizzazione di singole aziende
 * ed eventi.
 */
public class Posizione {
    private final double latitudine;
    private final double longitudine;

    Posizione(double latitudine, double longitudine) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }
}
