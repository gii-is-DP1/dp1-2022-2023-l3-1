package org.springframework.samples.parchisoca.game;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class GameTypeFormatter implements Formatter<GameType>{

    @Autowired
    private GameService gs;

    @Override
    public String print(GameType object, Locale locale) {
        String nombre = object.getName();
        return nombre;
    }

    @Override
    public GameType parse(String text, Locale locale) throws ParseException {
        GameType p = this.gs.findGameType(text);
        if (p == null) {
            throw new ParseException(text, 0);
        } else {
            return p;
        }
    }
    
}