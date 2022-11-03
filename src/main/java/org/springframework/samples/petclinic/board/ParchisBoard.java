package org.springframework.samples.petclinic.board;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "parchis_boards")
public class ParchisBoard extends BaseEntity {
    
    String background;

    @Positive
    int width;
    
    @Positive
    int height;

    public ParchisBoard(){
        this.background="resources/images/ParchisBoard.png";
        this.width=800;
        this.height=800;
    }
}
