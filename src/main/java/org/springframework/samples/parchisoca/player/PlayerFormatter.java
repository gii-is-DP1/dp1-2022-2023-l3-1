package org.springframework.samples.parchisoca.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;


import org.springframework.format.Formatter;
import org.springframework.samples.parchisoca.game.GameService;


@Component
public class PlayerFormatter implements Formatter<Player> {
    
    @Autowired
    private GameService gs;

    @Override
    public String print(Player object, Locale locale) {
        String nombre = object.getFirstName();
        return nombre;
    }

    @Override
    public Player parse(String text, Locale locale) throws ParseException {
        // TODO Auto-generated method stub
        return null;
    }

   
}
