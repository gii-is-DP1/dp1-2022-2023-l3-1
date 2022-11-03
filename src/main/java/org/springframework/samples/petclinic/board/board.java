package org.springframework.samples.petclinic.board;


import javax.persistence.Entity;
import javax.validation.constraints.Positive;


import org.springframework.samples.petclinic.model.BaseEntity;


import lombok.Getter;

import lombok.Setter;



@Entity
@Getter
@Setter
public class board extends BaseEntity {


    String background;

    @Positive
    int width;

    @Positive
    int height;

    public board(){

        this.background  = "resources/images/tablero-oca.jpg";

        this.width = 800;

        this.height = 800;
    
    }  
}


