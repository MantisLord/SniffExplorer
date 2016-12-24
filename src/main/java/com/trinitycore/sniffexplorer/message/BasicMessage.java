package com.trinitycore.sniffexplorer.message;

/**
 * Message which has no custom parser implemented
 *
 * Created by chaouki on 23-12-16.
 */
public class BasicMessage extends Message{

    @Override
    public boolean contains(Integer relatedEntry) {
        return false;
    }

    @Override
    public boolean contains(String relatedGUID) {
        return false;
    }
}
