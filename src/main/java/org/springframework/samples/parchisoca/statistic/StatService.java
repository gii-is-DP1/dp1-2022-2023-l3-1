package org.springframework.samples.parchisoca.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatService {
    
    private StatRepository statRepository;

    @Autowired
    public StatService(StatRepository repository) {
        this.statRepository = repository;
    }

    @Transactional(readOnly = true)
    public Stat findStatsByPlayer(Player player) {
        Stat result = statRepository.getStatsByPlayer(player);
        return result;        
    }
}
