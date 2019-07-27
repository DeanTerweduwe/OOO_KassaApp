package model.db;

public class DbExeption extends Exception {
    public DbExeption() {
    }

    public DbExeption(String message) {
        super(message);
    }
}
