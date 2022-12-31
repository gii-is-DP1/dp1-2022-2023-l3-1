package org.springframework.samples.parchisoca.parchis;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;


import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.ParchisPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxesParchis extends BaseEntity{

    private Integer positionBoard;

    @ManyToOne
    private ParchisBoard parchisBoard;

    private Boolean safe;
    
    private Boolean exit;

    private Boolean bridge;

    private Integer xPosition;

    private Integer yPosition;

    @OneToMany
    private List<ParchisPiece> piecesInBox;

    public void addPieceToBox (ParchisPiece parchisPiece) {
        if (getPiecesInBox() == null) {
            List<ParchisPiece> ls = new ArrayList<>();
            ls.add(parchisPiece);
            setPiecesInBox(ls);
        } else {
            List<ParchisPiece> ls = getPiecesInBox();
            ls.add(parchisPiece);
            setPiecesInBox(ls);
        }
    }

}
