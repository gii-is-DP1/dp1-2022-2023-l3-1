package org.springframework.samples.parchisoca.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.piece.Colour;
import org.springframework.samples.parchisoca.piece.OcaPiece;
import org.springframework.samples.parchisoca.piece.OcaPieceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaBoardService {
    
    @Autowired
    OcaBoardRepository repo; 
    

    @Autowired
    OcaPieceService ocaPieceService;
    

    @Transactional(readOnly = true)
    public OcaBoard findById(Integer id){
        return repo.findById(id).get();
    }

    @Transactional
    public void save(OcaBoard ocaBoard) {
        repo.save(ocaBoard);
    }

    
}
