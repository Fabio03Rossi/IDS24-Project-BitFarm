package com.github.fabio03rossi.bitfarm.exception;

public class DatiNonTrovatiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DatiNonTrovatiException(){
    }

    public DatiNonTrovatiException(String s) {
        super(s);
    }
}
