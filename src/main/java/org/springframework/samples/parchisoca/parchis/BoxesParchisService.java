package org.springframework.samples.parchisoca.parchis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoxesParchisService {
    
    @Autowired
    BoxesParchisRepository boxesParchisRepository;

    @Transactional
    public void save(BoxesParchis boxes) {
        boxesParchisRepository.save(boxes);
    }
}
