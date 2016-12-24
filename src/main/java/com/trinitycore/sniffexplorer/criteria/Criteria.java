package com.trinitycore.sniffexplorer.criteria;

import com.trinitycore.sniffexplorer.message.Message;

/**
 * Created by chaouki on 23-12-16.
 */
public interface Criteria {

    boolean isSatisfiedBy(Message message);
}
