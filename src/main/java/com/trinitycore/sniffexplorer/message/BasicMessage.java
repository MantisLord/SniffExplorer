package com.trinitycore.sniffexplorer.message;

import com.trinitycore.sniffexplorer.exceptions.ParseException;

import java.util.List;

/**
 * Message which has no custom parser implemented
 *
 * Created by chaouki on 23-12-16.
 */
public class BasicMessage extends Message{

    @Override
    public void initialize(List<String> lines) throws ParseException {
        // do nothing
    }

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        return false;
    }
}
