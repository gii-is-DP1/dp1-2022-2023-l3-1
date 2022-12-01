package org.springframework.samples.parchisoca.board;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.parchis.BoxesParchis;
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
        this.background="resources/images/ParchisBoard.png";
        this.width=1900;
        this.height=1900;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parchisBoard")
    List<ParchisPiece> pieces; 

    @OneToOne
    private Game game;

    Integer turn = 0;

    @OneToMany
    private List<BoxesParchis> boxes;

    @OneToMany
    List<ParchisPiece> parchisPieces;

    @ManyToMany
    List<ParchisDice> parchisDices;
}
