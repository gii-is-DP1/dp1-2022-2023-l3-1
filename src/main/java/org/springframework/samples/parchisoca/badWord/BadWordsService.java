package org.springframework.samples.parchisoca.badWord;

import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Service;

@Service
public class BadWordsService {
    
    public Boolean checkBadWords(String word) {
        BadWords [] BADWORDS = BadWords.values();
        Boolean result = false;
        for (BadWords bw: BADWORDS) {
            String str = bw.getBadWord().toLowerCase();
            if (word.toLowerCase().contains(str)) {
                result = true;
                break;
            }
        }
        return result;
    }   

    public Boolean checkPlayerBadWords(Player player) {
        Boolean result = false;

        String username = player.getUser().getUsername();
        String firstName = player.getFirstName();
        String lastName = player.getLastName();

        if (checkBadWords(username)) {
            result = true;
        }

        if (checkBadWords(firstName)) {
            result = true;
        }
        
        if (checkBadWords(lastName)) {
            result = true;
        }

        return result;
        
    }
}
