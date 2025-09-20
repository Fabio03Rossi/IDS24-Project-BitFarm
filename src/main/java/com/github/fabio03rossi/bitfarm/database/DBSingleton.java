package com.github.fabio03rossi.bitfarm.database;

public class DBSingleton {
    private static DBManager instance;
    private DBSingleton() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
            instance.startConnection();
        }
        return instance;
    }
}
