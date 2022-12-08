package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.parchis.BoxesParchis;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.ParchisPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "parchis_boards")
public class ParchisBoard extends BaseEntity {
    
    String background;

    @Enumerated(EnumType.STRING)
    private SpecialBoxesParchis casillasParchis; 

    @Positive
    int width;
    
    @Positive
    int height;

    public ParchisBoard(){
        this.background = "/resources/images/ParchisBoard.png";
        this.width = 650;
        this.height = 650;
    } 

    @OneToOne
    private Game game;

    private Integer turn = 0;

    @OneToMany
    private List<BoxesParchis> boxes;

    @OneToMany
    private List<ParchisPiece> pieces;

    @ManyToMany
    private List<ParchisDice> parchisDices;

    public void addPieceParchis (ParchisPiece parchisPiece) {
        if (getPieces() == null) {
            List<ParchisPiece> ls = new ArrayList<>();
            ls.add(parchisPiece);
            setPieces(ls);
        } else {
            List<ParchisPiece> ls = getPieces();
            ls.add(parchisPiece);
            setPieces(ls);
        }
    }
}
