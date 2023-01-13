package org.springframework.samples.parchisoca.player;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;


import org.springframework.format.Formatter;

@Component
public class PlayerFormatter implements Formatter<Player> {
    

    @Override
    public String print(Player player, Locale locale) {
        return player.getUser().getUsername();
    }

    @Override
    public Player parse(String text, Locale locale) throws ParseException {
        return null;
    }

   
}
