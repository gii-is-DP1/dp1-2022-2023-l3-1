package org.springframework.samples.parchisoca.piece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaPieceService {
    
    @Autowired
    OcaPieceRepository ocaPieceRepository;
    @Autowired
    OcaBoardService ocaBoardService;

    @Autowired
    public OcaPieceService(OcaPieceRepository ocaPieceRepository ){
        this.ocaPieceRepository = ocaPieceRepository;
    }
    
    @Transactional(readOnly = true)
    public OcaPiece findOcaPieceById(int id) {
        return ocaPieceRepository.findById(id).get();
    }

    @Transactional
    public void save(OcaPiece p) {
        ocaPieceRepository.save(p);
    }

}
