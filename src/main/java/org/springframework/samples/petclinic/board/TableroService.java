package org.springframework.samples.petclinic.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableroService {
    
   
    TableroRepository repo ; 

    @Autowired
    public TableroService (TableroRepository repo){
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public board findById(Integer id){
        return repo.findById(id).get();
    }
    

    
}
