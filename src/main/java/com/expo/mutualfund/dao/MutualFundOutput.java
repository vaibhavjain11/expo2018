package com.expo.mutualfund.dao;

import com.expo.mutualfund.constants.Risk;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MutualFundOutput")
public class MutualFundOutput {

    String name;

    Risk risk;

    Integer duration;

    Integer counter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}
