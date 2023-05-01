package edu.virginia.cs.hw7;

public class ConfigSingleton {
    private static ConfigSingleton instance;

    private String dataBaseName;
    private ConfigSingleton() {
        setDatabase();
    }

    public static ConfigSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigSingleton();
        }
        return instance;
    }

    public String getDatabaseFilename() {
        return dataBaseName;
    }

    private void setDatabase(){
        dataBaseName = "Reviews.sqlite3";
    }

}
