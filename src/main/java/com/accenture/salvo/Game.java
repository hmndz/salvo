package com.accenture.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final Date creationDate;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<GamePlayer> gamePlayers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Score> scores = new HashSet<>();


    public long getId() {
        return this.id;
    }

    public Date getCreationDate(){
        return this.creationDate;
    }

    public Game(){
        this.creationDate = new Date();
    }

    public Game(Date date){
        this.creationDate = date;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        this.gamePlayers.add(gamePlayer);
    }


    @JsonIgnore
    public List<Player> getPlayers(){
        return this.gamePlayers.stream().map(p -> p.getPlayer()).collect(Collectors.toList());
    }

    public Set<GamePlayer> getGamePlayers() {
        return this.gamePlayers;
    }

    @JsonIgnore
    public Object getGamePlayersDTO() {
        return this.gamePlayers.stream().map(gp -> gp.getGamePlayerDTO()).collect(Collectors.toList());
    }

    public Object getGameSalvosDTO(){
        return gamePlayers.stream().flatMap(gp->
                gp.getSalvos().stream().map(salvo -> salvo.getSalvoDTO())).collect(Collectors.toList());
    }

    public long countGamePlayers() {
        return this.gamePlayers.size();
    }

    public Map <String,Object> getGameDTO() {
        Map <String,Object>  gameDTO = new LinkedHashMap<>();
        gameDTO.put("id", this.id);
        gameDTO.put("created", this.creationDate);
        gameDTO.put("gamePlayers",gamePlayers.stream().map(gp -> gp.getGamePlayerDTO()).collect(Collectors.toList()));
        gameDTO.put("scores", scores.stream().map(Score::getPlayerScore).collect((Collectors.toList())));
        return gameDTO;
    }

    public Object getHitsDTO(long RequestPlayerId) {
        Map<String,Object> hitsDTO = new LinkedHashMap<>();
        if (this.gamePlayers.size()!= 2) {
            return getPHHitsDTO();
        }
        GamePlayer gamePlayersRequest = this.gamePlayers.stream().filter(gp -> gp.getId() == RequestPlayerId).findFirst().get();
        GamePlayer opponent = this.getOpponent(RequestPlayerId);
        hitsDTO.put(("self"),this.processSalvos(opponent,gamePlayersRequest));
        hitsDTO.put(("opponent"),this.processSalvos(gamePlayersRequest, opponent));
        return hitsDTO;
    }

    private Map<String,Object> getPHHitsDTO() {
        Map<String,Object> hitsDTO = new LinkedHashMap<>();
        hitsDTO.put("self", new ArrayList<>());
        hitsDTO.put("opponent", new ArrayList<>());
        return  hitsDTO;
    }

    public GamePlayer getOpponent(long id) {
        Iterator<GamePlayer> gpIterator = this.gamePlayers.iterator();
        GamePlayer gamePlayer1 = gpIterator.next();
        GamePlayer gamePlayer2 = gpIterator.next();
        if (gamePlayer1.getId() == id) {
            return gamePlayer2;
        } else {
            return gamePlayer1;
        }
    }

    private List<Map<String,Object>> processSalvos(GamePlayer attacker, GamePlayer defender ) {
        List<Map<String,Object>> processedSalvosDTO = new LinkedList<>();
        Map<String,Integer> shipsStatusMap = this.createShipsStatusMap();

        for (Salvo salvo: attacker.getSalvos()) {
            Map<String, Object> processedTurnDTO = new LinkedHashMap<>();
            processedTurnDTO.put("turn", salvo.getTurn());
            processedTurnDTO.put("hitLocations", salvo.getSalvoLocations());
            processDefenders(salvo.getSalvoLocations(), defender.getShips(), shipsStatusMap);
            processedTurnDTO.put("damages", new LinkedHashMap<>(shipsStatusMap));
            processedTurnDTO.put("missed", countMissShots(salvo.getSalvoLocations().size(),shipsStatusMap));
            processedSalvosDTO.add(processedTurnDTO);
            ShipStatusMapReset(shipsStatusMap);
        }
        return processedSalvosDTO;
    }

    private long countMissShots(int numberSalvos, Map<String,Integer> shipsStatusMap) {
        for (String key: shipsStatusMap.keySet()) {
            if (key.contains("Hits")) {
                numberSalvos = numberSalvos - shipsStatusMap.get(key);
            }
        }
        return numberSalvos;
    }

    private Map<String,Integer> createShipsStatusMap() {
        Map<String,Integer> shipsStatusMap = new LinkedHashMap<>();
        shipsStatusMap.put("carrierHits",0);
        shipsStatusMap.put("battleshipHits",0);
        shipsStatusMap.put("submarineHits",0);
        shipsStatusMap.put("destroyerHits",0);
        shipsStatusMap.put("patrolboatHits",0);
        shipsStatusMap.put("carrier",0);
        shipsStatusMap.put("battleship",0);
        shipsStatusMap.put("submarine",0);
        shipsStatusMap.put("destroyer",0);
        shipsStatusMap.put("patrolboat",0);

        return shipsStatusMap;
    }

    private void ShipStatusMapReset(Map<String,Integer> shipsStatusMap) {
        shipsStatusMap.put("carrierHits",0);
        shipsStatusMap.put("battleshipHits",0);
        shipsStatusMap.put("submarineHits",0);
        shipsStatusMap.put("destroyerHits",0);
        shipsStatusMap.put("patrolboatHits",0);
    }

    private void checkShipHit(String salvoLocation, Set<Ship> ships, Map<String,Integer> shipsStatusMap) {
        ships.stream().forEach(ship -> {
            if (ship.shipPieceHit(salvoLocation)) {
                shipsStatusMap.merge(ship.getShipTypeString() + "Hits", 1, Integer::sum);
                shipsStatusMap.merge(ship.getShipTypeString(), 1, Integer::sum);
            }
        });
    }

    private void processDefenders (List<String> salvoLocations, Set<Ship> ships,  Map<String,Integer> shipsStatusMap) {
        salvoLocations.stream().forEach(salvoLocation -> checkShipHit(salvoLocation, ships, shipsStatusMap));
    }



}
