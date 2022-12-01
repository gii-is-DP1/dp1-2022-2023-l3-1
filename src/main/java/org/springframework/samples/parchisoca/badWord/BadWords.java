package org.springframework.samples.parchisoca.badWord;

public enum BadWords {
    FUCK ("FUCK"),
    CUNT ("CUNT"),
    PISS ("PISS"),
    SHIT ("SHIT"),
    COCK ("COCK"),
    DICK ("DICK"),
    KNOB ("KNOB"),
    PUSS ("PUSS"),
    SHAG ("SHAG"),
    TITS ("TITS"),
    TWAT ("TWAT"),
    ARSE ("ARSE"),
    DYKE ("DYKE");

    private final String badWord;
     
    BadWords (String badWord){
        this.badWord = badWord;
    }

    public String getBadWord() {
        return this.badWord;
    }
}
