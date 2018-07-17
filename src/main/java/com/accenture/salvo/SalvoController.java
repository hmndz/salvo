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

    @Autowired
    SalvoRepository salvoRepository;


    @RequestMapping("/game_view/{id}")
    public Object getGameById (@PathVariable("id") Long gamePlayerID) {
        Player authPlayer = this.getAuthPlayer();
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerID);
        if (authPlayer == null) {
            return new ResponseEntity<>(this.getMapDTO("error", "UNAUTHORIZED"), HttpStatus.UNAUTHORIZED);
        }
       long authPlayerId = authPlayer.getId();
        if (gamePlayer.getPlayer().getId() == authPlayerId){

            gamePlayer.updateGameState();
            gamePlayerRepository.save(gamePlayer);
            return gamePlayer.getGamePlayerViewDTO();
        } else {
            return new ResponseEntity<>(this.getMapDTO("error", "Already logged in"), HttpStatus.UNAUTHORIZED);
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
        List<Player> players = playerRepository.findAll();
        return players.stream().map(Player::getAllScoreDTO).collect(Collectors.toList());
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
            return this.createRespEntity("gpId", gamePlayer.getId(), HttpStatus.CREATED);
        }
    }

//Joining a game

    @RequestMapping(path = "/game/{id}/players", method = RequestMethod.POST)
    public Object joinGame(@PathVariable("id") long gameId) {
        Player player = this.getAuthPlayer();
        if (player == null) {
            return new ResponseEntity<>(this.getMapDTO("error", "Login first good Sir"), HttpStatus.UNAUTHORIZED);
        }
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            return new ResponseEntity<>(this.getMapDTO("error", "Invalid gameId"), HttpStatus.FORBIDDEN);
        }
        if (game.countGamePlayers() == 2) {
            return new ResponseEntity<>(this.getMapDTO("error", "Game is Full"), HttpStatus.FORBIDDEN);
        }
        GamePlayer gamePlayer = new GamePlayer(player, game);
        gamePlayerRepository.save(gamePlayer);
        return new ResponseEntity<>(this.getMapDTO("gp", gamePlayer.getId()), HttpStatus.CREATED);
    }

// Getting Ships

    @RequestMapping(path = "/games/players/{gamePlayerID}/ships", method = RequestMethod.GET)
    public Object getShip(@PathVariable("gamePlayerID") long gamePlayerID) {
        Map<String, Object> playerShips = new LinkedHashMap<>();
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerID);
        Player player = getAuthPlayer();
        if (player == null || (player.getId() != gamePlayer.getPlayer().getId())) {
            return this.createRespEntity("error", "Seriously dude!? You're better than this!", HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null) {
            return this.createRespEntity("error", "Information not found", HttpStatus.NOT_FOUND);
        }

        gamePlayer.updateGameState();
        playerShips.put("ships", gamePlayer.getGPlayerShipsDTO());
        playerShips.put("gp", gamePlayer.getId());
        return playerShips;
    }

// Setting Ships

    @RequestMapping(path = "/games/players/{gamePlayerID}/ships", method = RequestMethod.POST)
    public Object setShipsLocations (@PathVariable("gamePlayerID") long gamePlayerID,
                                     @RequestBody List<Ship> ships) {
        Player authPlayer = getAuthPlayer();
        if (authPlayer == null) {
            return this.createRespEntity("error", "Not logged in", HttpStatus.UNAUTHORIZED);
        }
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerID);
        if (gamePlayer == null) {
            return this.createRespEntity("error", "Can not access the game", HttpStatus.UNAUTHORIZED);
        }
        if (authPlayer.getId() != gamePlayer.getPlayer().getId()) {
            return this.createRespEntity("error", "Unable to add ships ", HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer.withOutShips()) {
            gamePlayer.addShip(ships);
            gamePlayer.updateGameState();
            gamePlayerRepository.save(gamePlayer);
            return this.createRespEntity("message", "Ships Added", HttpStatus.CREATED);
        } else {
            return this.createRespEntity("error", "Ships already added", HttpStatus.FORBIDDEN);
        }
    }

// Getting Salvos

    @RequestMapping(path = "/games/players/{gamePlayerID}/salvoes", method = RequestMethod.GET)
    public Object getSalvos(@PathVariable("gamePlayerID") long gamePlayerID) {
        Map<String, Object> playerSalvos = new LinkedHashMap<>();
        GamePlayer selectedGamePlayer = gamePlayerRepository.findOne(gamePlayerID);
        Player player = getAuthPlayer();
        if (player == null || (player.getId() != selectedGamePlayer.getPlayer().getId())) {
            return this.createRespEntity("error", "Seriously dude!? You're better than this!", HttpStatus.FORBIDDEN);
        }
        if (selectedGamePlayer == null) {
            return this.createRespEntity("error", "Information not found", HttpStatus.UNAUTHORIZED);
        }
        playerSalvos.put("gp", selectedGamePlayer.getId());
        playerSalvos.put("salvoes", selectedGamePlayer.getSalvosDTO());
        selectedGamePlayer.updateGameState();
        return playerSalvos;
    }

// Setting Salvos

    @RequestMapping(path="/games/players/{gamePlayerId}/salvoes", method = RequestMethod.POST)
    public Object settingSalvos (@PathVariable("gamePlayerID") long gamePlayerID, @RequestBody Salvo salvos) {
        Player authPlayer = getAuthPlayer();
        if (authPlayer == null) {
            return this.createRespEntity("error", "Not logged in", HttpStatus.UNAUTHORIZED);
        }
        GamePlayer gamePlayer = gamePlayerRepository.findOne(gamePlayerID);
        if (gamePlayer == null) {
            return this.createRespEntity("error", "Can not access the game", HttpStatus.UNAUTHORIZED);}
        if (authPlayer.getId() != gamePlayer.getPlayer().getId()) {
            return this.createRespEntity("error", "PlayerId do not match", HttpStatus.UNAUTHORIZED);}
        Salvo newSalvo = new Salvo(gamePlayer.getSalvos().size()+1,
                gamePlayer, salvos.getSalvoLocations());

        if (this.permittedSalvo(gamePlayer, newSalvo)) {
            gamePlayer.updateGameState();
            gamePlayer.addSalvos(newSalvo);
            gamePlayerRepository.save(gamePlayer);
            return this.createRespEntity("Notification", "Salvos Added", HttpStatus.CREATED);
        } else {
            return this.createRespEntity("error", "Salvos already added", HttpStatus.FORBIDDEN);
        }
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

    private boolean permittedSalvo(GamePlayer gamePlayer, Salvo salvo) {
        if (gamePlayer.getSalvos().isEmpty()) {
            return true;
        }
        return (salvo.getTurn() ==
                gamePlayer.getSalvos().size() + 1)&(!gamePlayer.notPermittedSalvo(salvo.getSalvoLocations()));
    }
}
