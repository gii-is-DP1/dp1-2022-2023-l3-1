package org.springframework.samples.parchisoca.board;


import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.OcaPiece;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class BoxesOca extends BaseEntity {

    @Positive
    int numberBoxesOca;

    private SpecialBoxesOca specialBoxOca;

    public BoxesOca(SpecialBoxesOca oca) {
        this.specialBoxOca = oca;
    }

    
}
