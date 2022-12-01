package org.springframework.samples.parchisoca.piece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.transaction.annotation.Transactional;

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

}
