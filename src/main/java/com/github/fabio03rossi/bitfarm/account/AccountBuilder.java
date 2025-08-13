package com.github.fabio03rossi.bitfarm.account;

public interface AccountBuilder<T> {
    void reset();

    AccountBuilder<T> email(String email);

    AccountBuilder<T> password(String password);

    public T getResult();
}
