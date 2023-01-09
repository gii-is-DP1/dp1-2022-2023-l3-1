package org.springframework.samples.parchisoca.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.parchisoca.board.OcaBoard;
import org.springframework.samples.parchisoca.board.OcaBoardService;
import org.springframework.samples.parchisoca.board.ParchisBoard;
import org.springframework.samples.parchisoca.board.ParchisBoardService;
import org.springframework.samples.parchisoca.player.Player;
import org.springframework.samples.parchisoca.player.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {
    
    GameRepository repository;

    @Autowired
    ParchisBoardService parchisBoardService;

    @Autowired
    OcaBoardService ocaBoardService;
    
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    public GameService(GameRepository repository ){
        this.repository = repository;
    }
    
    List<Game> getGames(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Game findGameById(int id) {
        return repository.getGameById(id);
    }

    @Transactional(readOnly = true)
    public GameType findGameType(String name) {
        return repository.getGameType(name);
    }

    @Transactional(readOnly = true)
    public List<GameType> findAllGameTypes() {
        return repository.getAllGameTypes();
    }

    @Transactional
    public Game save(Game g){
        return repository.save(g);
    }

    @Transactional
    public void deleteGameById(int id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Game> findPublicGames(){
        return repository.getPublicGames();
    }

    @Transactional(readOnly = true)
    public List<Game> findPublicGamesNotFinished(){
        return repository.getPublicGamesNotFinished();
    }

    @Transactional(readOnly = true)
    public List<Game> findGamesFinished(){
        return repository.getGamesFinished();
    }

    @Transactional(readOnly = true)
    public List<Game> findGamesInProgress(){
        return repository.getGamesInProgress();
    }

    @Transactional(readOnly = true)
    public Game findGameByCode(String code) {
        return repository.getGameByCode(code);
    }

    @Transactional(readOnly = true)
    public Player findPlayerById(int id) {
        return playerRepository.getPlayerById(id);
    }

    @Transactional
    public String selectGame(GameType currentGameType, Game currentGame) {

        if (currentGameType.getName().equals("PARCHIS")) {
            ParchisBoard newParchisBoard = parchisBoardService.initBoard(currentGame);
            currentGame.setStarted(true);
            currentGame.setParchisBoard(newParchisBoard);
            newParchisBoard.setGame(currentGame);
            parchisBoardService.save(newParchisBoard);
            save(currentGame);
            int parchisBoardId = newParchisBoard.getId();
            return "redirect:/boards/parchisBoard/"+parchisBoardId;
        } else {
            OcaBoard newOcaBoard = ocaBoardService.initBoard(currentGame);
            currentGame.setStarted(true);
            currentGame.setOcaBoard(newOcaBoard);
            newOcaBoard.setGame(currentGame);
            ocaBoardService.save(newOcaBoard);
            save(currentGame);
            int ocaBoardId = newOcaBoard.getId();
            return "redirect:/boards/ocaBoard/"+ocaBoardId;
        }
    }

}


