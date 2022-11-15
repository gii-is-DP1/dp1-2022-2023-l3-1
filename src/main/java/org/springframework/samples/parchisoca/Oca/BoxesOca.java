package org.springframework.samples.parchisoca.oca;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.OcaPiece;

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
