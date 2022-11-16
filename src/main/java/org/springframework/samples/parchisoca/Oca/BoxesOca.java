package org.springframework.samples.parchisoca.oca;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxesOca extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private SpecialBoxesOca specialBoxOca;

    private Integer positionBoard;
    
}
