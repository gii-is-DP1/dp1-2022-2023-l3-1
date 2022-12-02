package org.springframework.samples.parchisoca.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.parchis.BoxesParchis;
import org.springframework.samples.parchisoca.parchis.BoxesParchisService;
import org.springframework.samples.parchisoca.dice.ParchisDice;
import org.springframework.samples.parchisoca.dice.ParchisDiceService;
import org.springframework.samples.parchisoca.game.Game;
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
    public Optional<ParchisBoard> findById(Integer id) {
		return parchisBoardRepo.findById(id);
	}

    @Transactional
    public void save(ParchisBoard parchisBoard) {
        parchisBoardRepo.save(parchisBoard);
    }

    public ParchisBoard initBoard(Game game) {
        ParchisBoard parchisBoard = new ParchisBoard(); 
        List<Colour> colours = List.of(Colour.RED,Colour.BLUE,Colour.YELLOW,Colour.GREEN);
        parchisBoardRepo.save(parchisBoard);
        List<Player> players = game.getPlayers();
        int j = 0;
        for(Player p: players) {
            Colour color = colours.get(j);
            for (int i=0; i < 4; i++) {
                ParchisPiece piece = new ParchisPiece();
                p.addPieceParchis(piece);
                piece.setColour(color);
                piece.setParchisBoard(parchisBoard);
                parchisPieceService.save(piece);
            }
            ParchisDice parchisDice1 = new ParchisDice();
            ParchisDice parchisDice2 = new ParchisDice();
            parchisDiceService.save(parchisDice1, parchisDice2);
            parchisDice1.setParchisBoard(parchisBoard);
            parchisDice2.setParchisBoard(parchisBoard);
            p.addDicesParchis(parchisDice1, parchisDice2);
            parchisDice1.setPlayer(p);
            parchisDice2.setPlayer(p);
            playerService.save(p);
            parchisDiceService.save(parchisDice1, parchisDice2);
            j++;
        }

        List<BoxesParchis> ls = initBoxes(parchisBoard);
        parchisBoard.setBoxes(ls);
        parchisBoardRepository.save(parchisBoard);

        return parchisBoard;
    }
    
    public List<BoxesParchis> initBoxes(ParchisBoard parchisBoard) {
        List<BoxesParchis> boxesParchis = new ArrayList<BoxesParchis>(68);
        for(int i=0; i<=68; i++) {
            BoxesParchis box = new BoxesParchis();
            if (i==5 || i==22 || i==39 || i==56) {
                box.boxesParchis(i, false, true, true);
            } else if (i==12 || i==29 || i==46 || i==63) {
                box.boxesParchis(i, false, true, false);
            } else if (i==17 || i==34 || i==51 || i==68) {
                box.boxesParchis(i, false, true, true);
            } else {
                box.boxesParchis(i, false, false, false);
            }

            box.setPositionBoard(i);
            box.setParchisBoard(parchisBoard);
            boxesParchis.add(box);
            boxesParchisService.save(box);
        }
        return boxesParchis;
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
