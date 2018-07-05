package com.accenture.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @RequestMapping("/game_view/{nn}")
    public Object getGameById(@PathVariable("nn") Long gamePlayerId) {
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerId);
        return gamePlayer.getGamePlayerViewDTO();

    }

    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public Object getGameId(Authentication authentication) {
        Map<String, Object> gamesDTO = new LinkedHashMap<>();
        List<Game> games = gameRepository.findAll();
        Player player = this.getAuthPlayer();

        if (player == null ) {
            gamesDTO.put("player", "Guest");
        } else {

            gamesDTO.put("player", player.getPlayerDTO());
            gamesDTO.put("games", games.stream().map(Game::getGameDTO).collect(Collectors.toList()));
        }
        return gamesDTO;
    }

    private Player getAuthPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            return playerRepository.findByUserName(authentication.getName());
        }
    }

    private Object makeMap (String key, Object message){
        Map<String, Object> makeMap = new LinkedHashMap<>();
        makeMap.put(key, message);
        return makeMap;
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity <Object> newPlayer (@RequestParam String username, String password) {
        if (username.isEmpty()) {
            return new ResponseEntity<>(makeMap("error", "userName is empty"), HttpStatus.BAD_REQUEST);
        }

        Player player = playerRepository.findByUserName(username);
        if (player != null) {
            return new ResponseEntity<>(makeMap("error", "Name in use"), HttpStatus.CONFLICT);
        }

        playerRepository.save(new Player(username, password));
        return new ResponseEntity<>(makeMap("error", "User created"), HttpStatus.CREATED);
    }

    @RequestMapping("/leaderBoard")
    public List<Object> getLeaderBoard() {
        List<Player> score = playerRepository.findAll();
        return score.stream().map(player -> player.getAllScoreDTO()).collect(Collectors.toList());

    }

}
