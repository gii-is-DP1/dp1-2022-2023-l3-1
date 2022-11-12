package org.springframework.samples.parchisoca.Oca;

import org.springframework.beans.factory.annotation.Autowired;
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
    
}
