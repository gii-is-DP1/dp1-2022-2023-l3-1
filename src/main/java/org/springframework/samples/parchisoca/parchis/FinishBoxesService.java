package org.springframework.samples.parchisoca.parchis;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinishBoxesService {
    
    @Autowired
    FinishBoxesRepository finishBoxesRepository;

    @Transactional
    public void save(FinishBoxes box){
        finishBoxesRepository.save(box);
    } 

}
