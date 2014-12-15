package com.patex.cache;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by apotekhin on 12/12/2014.
 */
public class BKCache implements Cache, Closeable{

    private final String _name;
    private final Database _database;


    BK _bk= new BK();

    public BKCache(String name) {
        _name = name;
        _database = _bk.opendDB(name);
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public Object getNativeCache() {
        return _database;
    }

    @Override
    public ValueWrapper get(Object key) {
        DatabaseEntry value= new DatabaseEntry();
        _database.get(null,_bk.getKeyEntry((String) key),value, LockMode.DEFAULT);
        if (value.getData() != null && value.getData().length != 0) {
            return new SimpleValueWrapper(_bk.getValue(value));
        } else {
            return null;
        }

    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return (T)get(key).get();
    }

    @Override
    public void put(Object key, Object value) {
        _database.put(null, _bk.getKeyEntry((String) key), _bk.getValueEntry((String[])  value));//TODO to object
    }

    @Override
    public void evict(Object key) {
        _database.delete(null,_bk.getKeyEntry((String) key));//TODO to object
    }

    @Override
    public void clear() {

    }

    @Override
    public void close() throws IOException {
        _database.close();
    }


}
