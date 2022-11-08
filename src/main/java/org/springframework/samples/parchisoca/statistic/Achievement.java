package org.springframework.samples.parchisoca.statistic;

import javax.persistence.Entity;

import javax.persistence.Table;

import org.springframework.samples.parchisoca.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "achievements")
public class Achievement extends NamedEntity {
    
    protected double threshold;

    protected String description;

    protected String badgeImage;
    
    public String getActualDescription(){
        return description.replace("<THRESHOLD>", String.valueOf(threshold));
    }
    
}
