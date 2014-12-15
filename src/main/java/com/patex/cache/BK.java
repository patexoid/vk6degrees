package com.patex.cache;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.*;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 */

public class BK implements Closeable {
    private final Environment myEnv;

    final StringBinding keyBind = new StringBinding();
    final StringArrayBinding valueBind = new StringArrayBinding();

    public BK() {
        EnvironmentConfig myEnvConfig = new EnvironmentConfig();
        myEnvConfig.setReadOnly(false);
        myEnvConfig.setAllowCreate(true);
        myEnvConfig.setTransactional(true);
        myEnv = new Environment(new File("home"), myEnvConfig);
    }

    public Database opendDB(String name) {
        DatabaseConfig myDbConfig = new DatabaseConfig();
        myDbConfig.setReadOnly(false);
        myDbConfig.setAllowCreate(true);
        myDbConfig.setTransactional(true);
        return myEnv.openDatabase(null, name, myDbConfig);
    }


    public void close() throws IOException {
        myEnv.close();
    }

    public DatabaseEntry getKeyEntry(String key) {
        return getEntry(key, keyBind);
    }

    public DatabaseEntry getValueEntry(String[] value) {
        return getEntry(value, valueBind);
    }

    private DatabaseEntry getEntry(Object data, TupleBinding keyBind) {
        DatabaseEntry entry = new DatabaseEntry();
        keyBind.objectToEntry(data, entry);
        return entry;
    }

    public String getKey(DatabaseEntry entry) {
        return getData(keyBind, entry);
    }

    public String[] getValue(DatabaseEntry entry) {
        return getData(valueBind, entry);
    }

    private <T> T getData(TupleBinding keyBind, DatabaseEntry entry) {
            return (T) keyBind.entryToObject(entry);
    }


}
