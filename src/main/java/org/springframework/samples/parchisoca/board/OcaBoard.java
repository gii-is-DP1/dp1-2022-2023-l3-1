package org.springframework.samples.parchisoca.board;


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
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "ocaBoard",fetch = FetchType.EAGER)
    List<OcaPiece> pieces; 
}


