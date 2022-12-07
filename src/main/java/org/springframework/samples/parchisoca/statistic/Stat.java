package org.springframework.samples.parchisoca.statistic;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.samples.parchisoca.model.BaseEntity;
import org.springframework.samples.parchisoca.player.Player;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "stats")
public class Stat extends BaseEntity {
    
    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    @Min(value = 0)
    private Integer playedGames = 0;

    @Min(value = 0)
    private Integer wonGames = 0;

    @Min(value = 0)
    private Integer lostGames = 0;

    public Double ratio(Integer wonGames, Integer lostGames) {
        Double result = ((double) lostGames/ (double) wonGames) * 100;
        return (double)Math.round(result * 100d) / 100d;
    }

}
