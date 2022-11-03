package org.springframework.samples.petclinic.tablero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.PlayerOca;

import lombok.Getter;

import lombok.Setter;



@Entity
@Getter
@Setter
public class TableroOca extends BaseEntity {
    private String background;
    @OneToMany
    private List<PlayerOca> jugador;
 
    public List<Integer> casillas(){
        List<Integer> casillas = new ArrayList<Integer>();
        for (int i=1; i<=63; i++){
            casillas.add(i); 
        }
        return casillas;
    }

    private Integer pozo = casillas().get(30);
    private Integer laberinto = casillas().get(41);
    private Integer carcel = casillas().get(52);
    private Integer muerte = casillas().get(57);
    private Integer puente = casillas().get(5);
    private Integer posada = casillas().get(18);

    private Integer casillaFinal = casillas().get(62);

    //castigo por casilla
    private final Integer castigoCarcel = 3;
    private final Integer castigoPosada = 2;
    private final Integer castigoPozo = 2;
    

    //tratamiento castigos + puentes
    public void tratamientoCastigos(PlayerOca p, OcaPiece c){
        if(c.posicionTablero == pozo){
            p.turnoEspera = castigoPozo;
        };
        if(c.posicionTablero == laberinto){
            c.posicionTablero = casillas().get(29);
        }
        if(c.posicionTablero == puente ){
            c.posicionTablero = casillas().get(11);

        }if(c.posicionTablero == muerte){
            c.posicionTablero = casillas().get(0);
        }

        if(c.posicionTablero == carcel){
            p.turnoEspera = castigoCarcel;
        }

        if(c.posicionTablero == posada){
            p.turnoEspera = castigoPosada;
        }

    }

    public void reboteTirada(PlayerOca p, OcaPiece c){
        if(c.posicionTablero > casillaFinal){
            c.posicionTablero = 2* casillaFinal - c.posicionTablero;
        }
    }

    

    
}
