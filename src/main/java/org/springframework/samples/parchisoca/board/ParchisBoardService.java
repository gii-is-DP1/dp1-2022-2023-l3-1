package org.springframework.samples.parchisoca.board;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisBoardService {
    
    ParchisBoardRepository parchisBoardRepo;

    @Autowired
    public ParchisBoardService(ParchisBoardRepository parchisBoardRepo) {
        this.parchisBoardRepo = parchisBoardRepo;
    }

    @Transactional(readOnly = true)
    public Optional<ParchisBoard> findById(Integer id){
		return parchisBoardRepo.findById(id);
	}

    @Transactional
    public void save(ParchisBoard parchisBoard) {
        parchisBoardRepo.save(parchisBoard);
    }
}
