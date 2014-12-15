package com.patex.cache;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * Created by apotekhin on 12/12/2014.
 */
public class StringBinding extends TupleBinding<String> {
    @Override
    public String entryToObject(TupleInput input) {
        return input.readString();
    }

    @Override
    public void objectToEntry(String object, TupleOutput output) {
        output.writeString(object);
    }
}
