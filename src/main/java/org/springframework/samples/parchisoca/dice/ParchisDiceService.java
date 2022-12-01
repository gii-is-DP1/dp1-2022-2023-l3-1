package org.springframework.samples.parchisoca.dice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisDiceService {

    @Autowired
    ParchisDiceRepository parchisDiceRepository;
    
    @Transactional
    public void save(ParchisDice dice1, ParchisDice dice2) {
        parchisDiceRepository.save(dice1);
        parchisDiceRepository.save(dice2);
    }
    
}
