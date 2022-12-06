package org.springframework.samples.parchisoca.statistic;

import java.text.DecimalFormat;

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
    private Integer playedGames;

    @Min(value = 0)
    private Integer wonGames;

    @Min(value = 0)
    private Integer lostGames;

    public Double ratio(Integer wonGames, Integer lostGames) {
        Double result = ((double) lostGames/ (double) wonGames) * 100;
        return (double)Math.round(result * 100d) / 100d;
    }

}
