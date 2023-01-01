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

    @Autowired
public void save(Stat stat) {
        statRepository.save(stat);
    }

    @Transactional(readOnly = true)
    public Stat findStatsByPlayer(Player player) {
        Stat result = statRepository.getStatsByPlayer(player);
        return result;        
    }

    @Transactional()
    public void increaseWonGames(Player player) {
        Stat playerStats = statRepository.getStatsByPlayer(player);
        Integer playedGames = playerStats.getPlayedGames() + 1;
        Integer wonGames = playerStats.getWonGames() + 1;
        playerStats.setPlayedGames(playedGames);
        playerStats.setWonGames(wonGames);
    }

    @Transactional()
    public void increaseLostGames(Player player) {
        Stat playerStats = statRepository.getStatsByPlayer(player);
        Integer playedGames = playerStats.getPlayedGames() + 1;
        Integer lostGames = playerStats.getLostGames() + 1;
        playerStats.setPlayedGames(playedGames);
        playerStats.setLostGames(lostGames);
    }

    @Transactional()
    public void initStats(Player player) {
        Stat stat = new Stat();
        stat.setPlayer(player);
        statRepository.save(stat);
        player.setStat(stat);
    }
}
