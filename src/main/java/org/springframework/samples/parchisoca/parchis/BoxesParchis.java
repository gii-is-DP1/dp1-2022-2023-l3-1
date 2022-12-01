package org.springframework.samples.parchisoca.parchis;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoxesParchis extends BaseEntity{

    @Positive
    int numberBoxesParchis;

    private Integer positionBoard;

    // @Enumerated(EnumType.STRING)
    // private SpecialBoxesParchis specialBoxesParchis;
    public BoxesParchis boxesParchis(Integer box, Boolean entry, Boolean safe, Boolean exit){
        BoxesParchis res = new BoxesParchis();
        res.setPositionBoard(box);
        res.setSafe(safe);
        res.setExit(exit);
        res.setEntry(entry);
        return res;
    }

    @ManyToOne
    ParchisBoard parchisBoard;

    Boolean safe;
    
    Boolean exit;

    Boolean entry;

}
