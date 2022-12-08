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

    int numberBoxesParchis;

    private Integer positionBoard;

    // @Enumerated(EnumType.STRING)
    // private SpecialBoxesParchis specialBoxesParchis;
    public BoxesParchis boxesParchis(Integer box, Boolean entry, Boolean safe, Boolean exit, Boolean bridge){
        BoxesParchis res = new BoxesParchis();
        res.setPositionBoard(box);
        res.setSafe(safe);
        res.setExit(exit);
        res.setEntry(entry);
        res.setBridge(bridge);
        return res;
    }

    @ManyToOne
    ParchisBoard parchisBoard;

    Boolean safe;
    
    Boolean exit;

    Boolean entry;

    Boolean bridge;

    @OneToMany
    List<ParchisPiece> piecesInBox;

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
