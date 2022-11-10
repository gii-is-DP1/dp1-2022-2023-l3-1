package org.springframework.samples.parchisoca.board;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.ParchisPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxesParchis extends BaseEntity{

    @Positive
    int numberBoxesParchis;

    private SpecialBoxesParchis specialBoxesParchis;

    public BoxesParchis(SpecialBoxesParchis parchis) {
        this.specialBoxesParchis = parchis;
    }


}
