package org.springframework.samples.parchisoca.oca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoxesOcaService  {

    @Autowired
    private BoxesOcaRepository boRepository;

    @Transactional
    public void save(BoxesOca boxe){
        boRepository.save(boxe);
    }

    @Transactional(readOnly = true)
    public BoxesOca findBoxByPosition(Integer ocaBoardId, Integer position){
        return boRepository.getBoxByPosition(ocaBoardId, position);
    }
    
}
