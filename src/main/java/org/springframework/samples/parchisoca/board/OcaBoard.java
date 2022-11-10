package org.springframework.samples.parchisoca.board;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;


import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.OcaPiece;

import lombok.Getter;

import lombok.Setter;



@Entity
@Getter
@Setter
@Table(name = "oca_boards")
public class OcaBoard extends BaseEntity {

    @OneToMany
    private List<BoxesOca> boxesOcaNormal;

    String background;

    @Positive
    int width;

    @Positive
    int height;


    public OcaBoard(){

        this.background  = "resources/images/tablero-oca.jpg";

        this.width = 800;

        this.height = 800;
    
    }  

    public void  posicionActual() {
        boxesOcaNormal = new ArrayList<BoxesOca>(63);
        for (int i=0; i<63; i++){
            BoxesOca res;
            if(i==5 || i==9 || i==14 || i==18 || i== 23 || i==27 
            || i== 32 || i==36 || i==41 || i==45 || i==50 || i==54 || i== 59){
                res = new BoxesOca(SpecialBoxesOca.OCA);
            } else if (i==6 || i==12) {
                res = new BoxesOca(SpecialBoxesOca.BRIDGE);
            } else if (i==26 || i==53) {
                res = new BoxesOca(SpecialBoxesOca.DICES);
            } else if (i==19) {
                res = new BoxesOca(SpecialBoxesOca.HOSTAL);
            } else if (i==31) {
                res = new BoxesOca(SpecialBoxesOca.WELL);
            } else if (i==42) {
                res = new BoxesOca(SpecialBoxesOca.LABYRINTH);
            } else if (i==58) {
                res = new BoxesOca(SpecialBoxesOca.DEATH);
            } else if (i==63) {
                res = new BoxesOca(SpecialBoxesOca.GOAL);
            } else {
                res = new BoxesOca(SpecialBoxesOca.NORMAL);
            }
            boxesOcaNormal.add(res);
        }
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ocaBoard",fetch = FetchType.EAGER)
    List<OcaPiece> pieces; 

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

}


