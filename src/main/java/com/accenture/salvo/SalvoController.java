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


    @RequestMapping("/game_view/{id}")
    public Object getGameById(@PathVariable("id") Long gamePlayerId) {
        long authPlayerId = this.getAuthPlayer().getId();
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerId);
        if (gamePlayer.getPlayer().getId() == authPlayerId) {
            return gamePlayer.getGamePlayerViewDTO();
        } else {
            return new ResponseEntity<>(this.getMapDTO("error", "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
        }
    }

    private Object getMapDTO (String key, Object message){
        Map<String, Object> MapDTO = new LinkedHashMap<>();
        MapDTO.put(key, message);
        return MapDTO;
    }

    private Player getAuthPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            return playerRepository.findByUserName(authentication.getName());
        }
    }

    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public Object getGameId(Authentication authentication) {
        Map<String, Object> gamesDTO = new LinkedHashMap<>();
        List<Game> games = gameRepository.findAll();
        Player player = this.getAuthPlayer();
        if (player == null) {
            gamesDTO.put("player", "Guest");
        } else {
            gamesDTO.put("player", player.getPlayerDTO());
        }
        gamesDTO.put("games", games.stream().map(Game::getGameDTO).collect(Collectors.toList()));
        return gamesDTO;
    }

    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> newPlayer(@RequestParam String username, String password) {
        if (username.isEmpty()) {
            return new ResponseEntity<>(this.getMapDTO("error", "userName is empty"), HttpStatus.BAD_REQUEST);
        }

        Player player = playerRepository.findByUserName(username);
        if (player != null) {
            return new ResponseEntity<>(this.getMapDTO("error", "Name in use"), HttpStatus.CONFLICT);
        }

        playerRepository.save(new Player(username, password));
        return new ResponseEntity<>(this.getMapDTO("error", "User created"), HttpStatus.CREATED);
    }

    @RequestMapping("/leaderBoard")
    public List<Object> getLeaderBoard() {
        List<Player> score = playerRepository.findAll();
        List<Object> leaderBoard = score.stream().map(player -> player.getAllScoreDTO()).collect(Collectors.toList());
        return leaderBoard;

    }
}

   // -------------------------------------------------------------

    /*private Object getMapDTO (String key, Object message){
        Map<String, Object> MapDTO = new LinkedHashMap<>();
        MapDTO.put(key, message);
        return MapDTO;
    }

    private Player getAuthPlayer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        } else {
            return playerRepository.findByUserName(authentication.getName());
        }
    }

    private ResponseEntity<Object> createResponseEntity (String tipoDeRespuesta, Object valor, HttpStatus httpStatus ) {
        Map<String,Object> responseMap = new LinkedHashMap<>();
        responseMap.put(tipoDeRespuesta, valor);
        return new ResponseEntity<>(responseMap, httpStatus);
    }



}
