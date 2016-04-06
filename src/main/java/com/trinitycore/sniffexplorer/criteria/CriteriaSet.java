/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.criteria;

import java.util.HashSet;
import java.util.Set;
import com.trinitycore.sniffexplorer.message.Message;

/**
 *
 * @author chaouki
 */
public class CriteriaSet {
    private Set<Criteria> criterion=new HashSet<>(); // two "same" criteria object shoudn't be in the set. 
    // therefore, equals() and hashcode() need to be reimplemented for Criteria and each child classes.
    
    public boolean IsSatisfiedBy(Message message){ // a CriteriaSet is satisfied if at least one Criteria object match the message
        for(Criteria criteria:criterion)
            if(criteria.isSatisfiedBy(message))
                return true;
        return false;
    }
    
    public void addCriteria(Criteria... criteriaVec){
        for(Criteria criteria:criteriaVec)
            criterion.add(criteria);
    }
}
