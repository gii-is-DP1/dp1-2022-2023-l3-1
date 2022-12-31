package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.Oca.Action;
import org.springframework.samples.parchisoca.Oca.BoxesOca;
import org.springframework.samples.parchisoca.Oca.SpecialBoxesOca;
import org.springframework.samples.parchisoca.piece.OcaPiece;

import lombok.Getter;

import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "oca_boards")
public class OcaBoard extends BaseEntity {

    public OcaBoard(){
        this.background  = "/resources/images/tablero-oca.png";
        this.width = 650;
        this.height = 650;
    }  
    
    @NotNull
    private String background;

    @Positive
    private int width;

    @Positive
    private int height;

    @OneToOne
    private Game game;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ocaBoard")
    private List<OcaPiece> pieces = new ArrayList<>(); 

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ocaBoard")
    private List<BoxesOca> boxes;

    private Integer turn = 0;

    public void addPiece(OcaPiece piece) {
        if (getPieces() == null) {
            List<OcaPiece> ls = new ArrayList<>();
            ls.add(piece);
            setPieces(ls);
        } else {
            List<OcaPiece> ls = getPieces();
            ls.add(piece);
            setPieces(ls);
        }
    }
    
    public Integer action(BoxesOca box,OcaPiece ocaPiece) {
        Action action = new Action();
        Integer pos =0;
        SpecialBoxesOca specialBox = box.getSpecialBoxOca();
        if (specialBox.equals(SpecialBoxesOca.BRIDGE)) {
            pos = action.bridge(box.getPositionBoard(),ocaPiece);
        } else if (specialBox.equals(SpecialBoxesOca.DEATH)) {
            pos = action.death(box.getPositionBoard());
        } else if (specialBox.equals(SpecialBoxesOca.DICES)) {
            pos = action.dices(box.getPositionBoard(),ocaPiece);
        } else if (specialBox.equals(SpecialBoxesOca.GOAL)) {
            pos = action.goal(box.getPositionBoard());
        } else if (specialBox.equals(SpecialBoxesOca.HOSTAL)) {
            pos = action.hostal(box.getPositionBoard(),ocaPiece);
        } else if (specialBox.equals(SpecialBoxesOca.LABYRINTH)) {
            pos = action.labyrinth(box.getPositionBoard());
        } else if(specialBox.equals(SpecialBoxesOca.OCA)) {
            pos = action.oca(box.getPositionBoard(),ocaPiece);
        } else if(specialBox.equals(SpecialBoxesOca.PRISON)) {
            pos = action.prison(box.getPositionBoard(),ocaPiece);
        } else if (specialBox.equals(SpecialBoxesOca.WELL)) {
            pos = action.well(box.getPositionBoard(),ocaPiece);
        } else {
            pos = box.getPositionBoard();
        }
        return pos;
    }

    public Integer bounceBack(Integer position) {  
        if (position > 63) {
            position = 2 * 63 - position;
        }
        return position;
    }
    
}


