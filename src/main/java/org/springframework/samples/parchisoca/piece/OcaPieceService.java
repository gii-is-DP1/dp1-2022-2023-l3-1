package org.springframework.samples.parchisoca.piece;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaPieceService {
    
    @Autowired
    OcaPieceRepository ocaPieceRepository;

    @Autowired
    public OcaPieceService(OcaPieceRepository ocaPieceRepository ){
        this.ocaPieceRepository = ocaPieceRepository;
    }
    
    @Transactional(readOnly = true)
    public OcaPiece findOcaPieceById(Integer id) {
        return ocaPieceRepository.findById(id).get();
    }

    @Transactional
    public void save(OcaPiece p) {
        ocaPieceRepository.save(p);
    }
}
