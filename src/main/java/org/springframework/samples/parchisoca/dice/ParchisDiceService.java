package org.springframework.samples.parchisoca.dice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParchisDiceService {

    @Autowired
    ParchisDiceRepository parchisDiceRepository;
    
    @Transactional(readOnly = true)
    public ParchisDice findById(int id){
        return parchisDiceRepository.findById(id).get();
    }

    @Transactional
    public List<ParchisDice> findDiceByParchisBoardPlayer(ParchisBoard parchisBoard, Player player){
        return parchisDiceRepository.getParchisDiceByParchisBoardPlayer(parchisBoard, player);
    }

    @Transactional
    public void save(ParchisDice dice1){
        parchisDiceRepository.save(dice1);
    }

    @Transactional
    public void save(ParchisDice dice1, ParchisDice dice2) {
        parchisDiceRepository.save(dice1);
        parchisDiceRepository.save(dice2);
    }

    
}
