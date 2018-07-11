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

    @Autowired
    ShipRepository shipRepository;



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

    @RequestMapping(path = "/games", method = RequestMethod.GET)
    public Object getGameIds() {
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
    public ResponseEntity<Object> newPlayer(@RequestParam String username,
                                            String password) {
        if (username.isEmpty()) {
            return new ResponseEntity<>(this.getMapDTO("error", "UserName is empty"), HttpStatus.BAD_REQUEST);
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
//Create a new game

    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public Object createGame() {
        Player authPlayer = this.getAuthPlayer();
        if (authPlayer == null) {
            return new ResponseEntity<>(this.getMapDTO("Error", "Not logged in"), HttpStatus.UNAUTHORIZED);
        } else {
            GamePlayer gamePlayer = new GamePlayer(authPlayer, gameRepository.save(new Game()));
            gamePlayerRepository.save(gamePlayer);
            return this.createRespEntity("gpid", gamePlayer.getId(), HttpStatus.CREATED);
        }
    }

//Joining a game

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    public Object joinGame(@PathVariable("id") Long gameId) {
        Player player = this.getAuthPlayer();
        if (player == null) {
            return new ResponseEntity<>(this.getMapDTO("error", "Login first good Sir"), HttpStatus.UNAUTHORIZED);
        }
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            return new ResponseEntity<>(this.getMapDTO("error", "Invalid gameId"), HttpStatus.FORBIDDEN);
        }
        if (game.getGameDTO().size() == 2) {
            return new ResponseEntity<>(this.getMapDTO("error", "Game is Full"), HttpStatus.FORBIDDEN);
        }
        GamePlayer gamePlayer = new GamePlayer(player, game);
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(this.getMapDTO("gpid", gamePlayer.getId()), HttpStatus.CREATED);

    }

// Getting Ships

    @RequestMapping(path = "/games/players/{gamePlayerId}/ships",
            method = RequestMethod.GET)
    public Object getShip(@PathVariable("gamePlayerId") long gpId) {
        Map<String, Object> playerShips = new LinkedHashMap<>();
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gpId);
        Player player = getAuthPlayer();
        if (player == null || (player.getId() != gamePlayer.getPlayer().getId())) {
            return this.createRespEntity("error", "Seriously dude!? You're better than this!", HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null) {
            return this.createRespEntity("error", " 404 Information not found", HttpStatus.NOT_FOUND);
        }
        playerShips.put("ships", gamePlayer.getGPlayerShipsDTO());
        playerShips.put("gpid", gamePlayer.getId());
        return playerShips;
    }

// Setting Ships

    @RequestMapping(path="/games/players/{gamePlayerId}/ships",
            method = RequestMethod.POST)
    public Object setShipsLocations (@PathVariable("gamePlayerId") long gpId,
                                     @RequestBody List<Ship> shipList) {
    Player authPlayer = getAuthPlayer();

    if (authPlayer == null) {
        return this.createRespEntity("error", "Not logged in", HttpStatus.UNAUTHORIZED);}
    GamePlayer selectedGamePlayer = gamePlayerRepository.findOne(gpId);
    if (selectedGamePlayer == null) {
        return this.createRespEntity("error", "Can not access the game", HttpStatus.NOT_FOUND);}
    if (authPlayer.getId() != selectedGamePlayer.getPlayer().getId()) {
        return this.createRespEntity("error", "Unable to add ships ", HttpStatus.UNAUTHORIZED);}
    if (selectedGamePlayer.withOutShips()) {
        shipList.stream().forEach(ship -> {Ship newShip = new Ship(ship.getShipType(), selectedGamePlayer, ship.getShipLocations());
            shipRepository.save(newShip); selectedGamePlayer.addShip(newShip);});
        gamePlayerRepository.save(selectedGamePlayer);
        return this.createRespEntity("message", "Ships added", HttpStatus.CREATED);}
        else {
        return this.createRespEntity("error", "Ships already set", HttpStatus.FORBIDDEN);}
    }

// Getting Salvos

    @RequestMapping(path = "/games/players/{gamePlayerId}/salvoes", method = RequestMethod.GET)
    public Object getSalvos(@PathVariable("gamePlayerId") long gpId) {
        Map<String, Object> playerSalvos = new LinkedHashMap<>();
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gpId);
        Player player = getAuthPlayer();
        if (player == null || (player.getId() != gamePlayer.getPlayer().getId())) {
            return this.createRespEntity("error", "Seriously dude!? You're better than this!", HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null) {
            return this.createRespEntity("error", " 404 Information not found", HttpStatus.NOT_FOUND);
        }
        playerSalvos.put("salvoes", gamePlayer.getSalvosDTO());
        playerSalvos.put("gpid", gamePlayer.getId());
        return playerSalvos;
    }


    // -------------------------------------------------------------

    private Object getMapDTO(String key, Object message) {
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

    private ResponseEntity<Object> createRespEntity(String response, Object value,
                                                    HttpStatus httpStatus) {
        Map<String, Object> respMap = new LinkedHashMap<>();
        respMap.put(response, value);
        return new ResponseEntity<>(respMap, httpStatus);
    }
}

