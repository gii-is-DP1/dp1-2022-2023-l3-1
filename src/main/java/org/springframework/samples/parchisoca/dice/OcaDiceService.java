package org.springframework.samples.parchisoca.dice;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OcaDiceService {
    
    @Autowired
    private OcaDiceRepository ocaDiceRepository;

    @Transactional
    public void save(OcaDice dice){
        ocaDiceRepository.save(dice);
    }
    
}
