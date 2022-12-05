package org.springframework.samples.parchisoca.piece;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisPieceService {
    
    @Autowired
    ParchisPieceRepository parchisPieceRepository;

    @Autowired
    ParchisBoardService parchisBoardService;

    @Autowired
    public ParchisPieceService(ParchisPieceRepository parchisPieceRepository) {
        this.parchisPieceRepository= parchisPieceRepository;
    }

    @Transactional(readOnly = true)
    public ParchisPiece findById (int id) {
        return parchisPieceRepository.findById(id).get();
    }

    @Transactional
    public void save(ParchisPiece p) {
        parchisPieceRepository.save(p);
    }
    @Transactional
    public List<ParchisPiece> findParchisPiecesByPlayerParchisBoard(Player player, ParchisBoard parchisBoard){
        return parchisPieceRepository.getParchisPiecesByPlayerParchisBoard(player, parchisBoard);
    }

}
