package com.patex.cache;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.bind.tuple.TupleInput;
import com.sleepycat.bind.tuple.TupleOutput;

/**
 * Created by apotekhin on 12/12/2014.
 */
public class StringArrayBinding extends TupleBinding<String[]> {
    @Override
    public String[] entryToObject(TupleInput input) {
        int length = input.readInt();
        String[] result=new String[length];
        for(int i=0;i<length;i++){
            result[i]=input.readString();
        }
        return result;
    }

    @Override
    public void objectToEntry(String[] object, TupleOutput output) {
        output.writeInt(object.length);
        for (String s : object) {
            output.writeString(s);
        }
    }
}
