package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.Game;
import org.springframework.samples.parchisoca.parchis.BoxesParchis;
import org.springframework.samples.parchisoca.parchis.BoxesParchisService;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.ParchisPiece;
import org.springframework.samples.parchisoca.piece.ParchisPieceService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class ParchisBoardService {
    
    ParchisBoardRepository parchisBoardRepo;

    @Autowired
    ParchisPieceService parchisPieceService;

    @Autowired
    ParchisDiceService parchisDiceService;

    @Autowired
    PlayerService playerService;

    @Autowired
    ParchisBoardRepository parchisBoardRepository;

    @Autowired
    BoxesParchisService boxesParchisService;

    @Autowired
    public ParchisBoardService(ParchisBoardRepository parchisBoardRepo) {
        this.parchisBoardRepo = parchisBoardRepo;
    }

    @Transactional(readOnly = true)
    public Optional<ParchisBoard> findById(Integer id){
		return parchisBoardRepo.findById(id);
	}

    @Transactional
    public void save(ParchisBoard parchisBoard) {
        parchisBoardRepo.save(parchisBoard);
    }

    //Inititate board with piece and dice
    public ParchisBoard initBoard(Game game){
    ParchisBoard parchis = new ParchisBoard();
    parchisBoardRepo.save(parchis);
    List<Player> players = game.getPlayers();
    for(Player p : players){
        ParchisPiece piece = new ParchisPiece();
        piece.setPlayer(p);
        piece.setColour(Colour.RED);
        piece.setParchisBoard(parchis); 
        parchisPieceService.save(piece);
        ParchisDice dice1 = new ParchisDice();
        ParchisDice dice2 = new ParchisDice();
        parchisDiceService.save(dice1, dice2);
        dice1.setParchisBoard(parchis);
        dice2.setParchisBoard(parchis);
        p.addDicesParchis(dice1, dice2);
        dice1.setPlayer(p);
        dice2.setPlayer(p);
        playerService.save(p);
        parchisDiceService.save(dice1, dice2);
    }
    
    List<BoxesParchis> ls = initBoxes();
    parchis.setBoxes(ls);
    parchisBoardRepository.save(parchis);
    
    return parchis;

}
    
    public List<BoxesParchis> initBoxes() {
        List<BoxesParchis> normalBoxesParchis = new ArrayList<BoxesParchis>(68);
        for(int i=0; i<68; i++) {
            BoxesParchis box = new BoxesParchis();
            if (i==5 || i==22 || i==39 || i==56) {
                box.boxesParchis(i, false, true, true);
                // res.setSpecialBoxesParchis(SpecialBoxesParchis.SAFE);
            } else if (i==12 || i==29 || i==46 || i==63) {
                box.boxesParchis(i, false, true, false);
            } else if (i==17 || i==34 || i==51 || i==68) {
                box.boxesParchis(i, false, true, true);
            } else {
                box.boxesParchis(i, false, false, false);
            }
            
            normalBoxesParchis.add(box);
            boxesParchisService.save(box);
        }
        return normalBoxesParchis;
    }

    @Transactional
    public ParchisPiece nextTurn(ParchisBoard parchisBoard, Integer turn) {
        List<ParchisPiece> pieces = parchisBoard.getPieces();
        if(turn == pieces.size()-1){
            parchisBoard.setTurn(0);
        } else {
            turn += 1;
            parchisBoard.setTurn(turn);
        }
        return pieces.get(turn);
    }
}
