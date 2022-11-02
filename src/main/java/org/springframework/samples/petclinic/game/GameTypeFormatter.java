package org.springframework.samples.petclinic.game;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class GameTypeFormatter implements Formatter<GameType>{

    //el formatter debe mostrar los productos usando la cadena de su nombre, y debe obtener un tipo de producto dado su nombre buscándolo en la BD. Si no se puede formatear debe lanzar una execpcion.

    @Autowired
    private GameService gs;

    @Override
    public String print(GameType object, Locale locale) {
        String nombre = object.getName();
        return nombre;
    }

    @Override
    public GameType parse(String text, Locale locale) throws ParseException {
        GameType p = this.gs.getGameType(text);
        if(p == null){
            throw new ParseException(text, 0);
        }else{
            return p;
        }
    }
    
}