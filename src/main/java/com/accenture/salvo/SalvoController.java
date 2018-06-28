package com.accenture.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;


    @RequestMapping("game_view/{id}")
    public Object getGameById(@PathVariable("id") Long gamePlayerId) {
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerId);
        return gamePlayer.getGameplayerViewDTO();

    }

    @RequestMapping("/games")
    public Object getGameIds() {
        List<Game> games = gameRepository.findAll();
        return games.stream().map(Game::getGameDTO).collect(Collectors.toList());
    }

}

/*@RequestMapping("game_view/{id}")
public Object getGameById(@PathVariable("id") StringgamePlayerId) {
GamePlayer gamePlayer = gamePlayerRepository.findById(Long.parseLong(gamePlayerId));
Game game = gamePlayer.getGame();
List<Object> ships = gamePlayer.getGamePlayerShipsDTO();
return game.getGamePViewDTO(ships);
}*/