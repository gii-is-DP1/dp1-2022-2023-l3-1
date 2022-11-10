package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.piece.ParchisPiece;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "parchis_boards")
public class ParchisBoard extends BaseEntity {

    @OneToMany
    private List<BoxesParchis> normalBoxesParchis;
    
    String background;

    @Positive
    int width;
    
    @Positive
    int height;

    public ParchisBoard(){
        this.background="resources/images/ParchisBoard.png";
        this.width=1900;
        this.height=1900;
    }

    // public void actualPositionParchis() {
    //     normalBoxesParchis = new ArrayList<BoxesParchis>(68);
    //     for (int i=0; i<68; i++) {
    //         BoxesParchis res;
    //         if (i==3 || i==20 || i==37 || i==54){

    //         }
    //     }
    // }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parchisBoard",fetch = FetchType.EAGER)
    List<ParchisPiece> pieces; 
}
