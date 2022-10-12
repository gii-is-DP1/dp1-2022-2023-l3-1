package org.springframework.samples.petclinic.statistics;

import javax.persistence.Entity;

import javax.persistence.Table;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "achievement")
public class Achievement extends NamedEntity {
    
    protected double  threshold;

    protected String description;

    protected String badgeImage;
    
    public String getActualDescription(){
        return description.replace("<THRESHOLD>", String.valueOf(threshold));
    }
    
}
