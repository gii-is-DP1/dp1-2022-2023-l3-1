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

    @Transactional
    public Integer roll2Dices(List<ParchisDice> dices) {
        ParchisDice dice1 = dices.get(0);
		ParchisDice dice2 = dices.get(1);
        dice1.rollDice();
        dice2.rollDice();
        Integer resultDice1 = dice1.getNumber();
		Integer resultDice2 = dice2.getNumber();
		Integer result = resultDice1 + resultDice2;
        return result;
    }
    
}
