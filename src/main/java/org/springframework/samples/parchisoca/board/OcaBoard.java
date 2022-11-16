package org.springframework.samples.parchisoca.board;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.swing.text.Position;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.dice.OcaDice;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.oca.Action;
import org.springframework.samples.parchisoca.oca.BoxesOca;
import org.springframework.samples.parchisoca.oca.SpecialBoxesOca;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.player.Player;

import lombok.Getter;

import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "oca_boards")
public class OcaBoard extends BaseEntity {

    String background;

    @Positive
    int width;

    @Positive
    int height;
    
    public OcaBoard(){
        this.background  = "/resources/images/tablero-oca.jpg";
        this.width = 800;
        this.height = 800;
    }  

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ocaBoard")
    List<OcaPiece> pieces = new ArrayList<>(); 

    @OneToOne
    private Game game;

    @OneToMany
    private List<BoxesOca> boxes;

    @OneToOne
    private OcaDice ocaDice;

    public void addPiece(OcaPiece piece) {
        if(getPieces() == null){
            List<OcaPiece> ls = new ArrayList<>();
            ls.add(piece);
            setPieces(ls);
        }else{
            List<OcaPiece> ls = getPieces();
            ls.add(piece);
            setPieces(ls);
        }
    }

    
    public Integer action(BoxesOca box,OcaPiece ocaPiece) {
        Action action = new Action();
        Integer pos =0;
        SpecialBoxesOca specialBox = box.getSpecialBoxOca();
        if(specialBox.equals(SpecialBoxesOca.BRIDGE)){
            pos = action.bridge(box.getPositionBoard());
        }else if(specialBox.equals(SpecialBoxesOca.DEATH)){
            pos = action.death(box.getPositionBoard());
        }else if(specialBox.equals(SpecialBoxesOca.DICES)){
            pos = action.dices(box.getPositionBoard());
        }else if(specialBox.equals(SpecialBoxesOca.GOAL)){
            pos = action.goal(box.getPositionBoard());
        }else if(specialBox.equals(SpecialBoxesOca.HOSTAL)){
            pos = action.hostal(box.getPositionBoard(),ocaPiece);
        }else if(specialBox.equals(SpecialBoxesOca.LABYRINTH)){
            pos = action.labyrinth(box.getPositionBoard());
        }else if(specialBox.equals(SpecialBoxesOca.OCA)){
            pos = action.oca(box.getPositionBoard());
        }else if(specialBox.equals(SpecialBoxesOca.PRISON)){
            pos = action.prison(box.getPositionBoard(),ocaPiece);
        }else if (specialBox.equals(SpecialBoxesOca.WELL)){
            pos = action.well(box.getPositionBoard(),ocaPiece);
        }else{
            pos = box.getPositionBoard();
        }
        return pos;
    }

    public Integer reboteTirada(Integer position){  
        if( position > 63){
            position = 2* 63 - position ;
        }
        return position;
    }
    
}


