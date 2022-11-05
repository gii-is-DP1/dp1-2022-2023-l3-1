package org.springframework.samples.parchisoca.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcaBoardService {
    
   
    OcaBoardRepository repo ; 

    @Autowired
    public OcaBoardService (OcaBoardRepository repo){
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public OcaBoard findById(Integer id){
        return repo.findById(id).get();
    }
    

    
}
