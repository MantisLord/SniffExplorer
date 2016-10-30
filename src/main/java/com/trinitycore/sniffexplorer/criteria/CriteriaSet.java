/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinitycore.sniffexplorer.criteria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import com.trinitycore.sniffexplorer.message.Message;

/**
 *
 * @author chaouki
 */
public class CriteriaSet {
    private HashSet<Criteria> criterion=new HashSet<>(); // todo (one day): two "same" criteria object shoudn't be in the set. therefore, in theory, equals() and hashcode() need to be reimplemented for Criteria and each child classes.
    private LocalDateTime globalMinTime;
    private LocalDateTime globalMaxTime;

    public boolean IsSatisfiedBy(Message message){ // a CriteriaSet is satisfied if at least one Criteria object match the message
        for(Criteria criteria:criterion)
            if((globalMinTime == null || !message.getTime().isBefore(globalMinTime))
                    && (globalMaxTime == null || !message.getTime().isAfter(globalMaxTime))
                    && criteria.isSatisfiedBy(message))
                return true;
        return false;
    }

    public CriteriaSet() {

    }

    public CriteriaSet(Criteria... criteriaVec) {
        addCriteria(criteriaVec);
    }

    public CriteriaSet(CriteriaSet criteriaSetA, CriteriaSet criteriaSetB) {
        HashSet<Criteria> criteriaSet = (HashSet<Criteria>) criteriaSetA.criterion.clone();
        criteriaSet.addAll(criteriaSetB.criterion);
        this.criterion = criteriaSet;
    }

    public void addCriteria(Criteria... criteriaVec){
        for(Criteria criteria:criteriaVec)
            criterion.add(criteria);
    }

    public void setGlobalMinTime(String formattedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        this.globalMinTime = LocalDateTime.parse(formattedDateTime, formatter);
    }

    public void setGlobalMinTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        this.globalMinTime = LocalDateTime.of(year, month, day, hour, minute, second, millisecond*1_000_000);
    }

    public void setGlobalMaxTime(String formattedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.SSS");
        this.globalMaxTime = LocalDateTime.parse(formattedDateTime, formatter);
    }

    public void setGlobalMaxTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        this.globalMaxTime = LocalDateTime.of(year, month, day, hour, minute, second, millisecond*1_000_000);
    }
}
