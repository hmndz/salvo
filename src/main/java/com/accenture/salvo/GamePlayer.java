package com.accenture.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Salvo> salvos = new HashSet<>();

    public GamePlayer() {
    }

    public Player getPlayer() {
        return this.player;
    }

    public Game getGame() {
        return this.game;
    }

    public Long getId() {
        return this.id;
    }

    public Date getJoinDate() {
        return this.joinDate;
    }

    public Set<Ship> getShips() {
        return this.ships;
    }

    public void addShip(List<Ship> ships) {
        ships.forEach(ship -> this.ships.add(new Ship(ship.getShipType(), this,
                ship.getShipLocations())));
    }

    public void addSalvos(Salvo salvo) {
        this.salvos.add(salvo);
    }

    public boolean withOutShips() {
        return this.ships.isEmpty();
    }

    public Set<Salvo> getSalvos() {
        return this.salvos;
    }

    public List<Object> getGPlayerShipsDTO() {
        return this.ships.stream().map(ship -> ship.getShipDTO()).collect(Collectors.toList());
    }

    public Object getSalvosDTO() {
        return this.salvos.stream().map(sal -> sal.getSalvoDTO()).collect(Collectors.toList());
    }

    public boolean notPermittedSalvo(List <String> salvoLocations) {
        for (Salvo salvo: this.salvos) {
            if (salvo.checkLocations(salvoLocations)) {
                return true;}}
        return false;
    }

    public GamePlayer(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.joinDate = new Date();
    }

    @JsonIgnore
    public Map<String, Object> getGamePlayerDTO() {
        Map<String, Object> gamePlayerDTO = new LinkedHashMap<>();
        gamePlayerDTO.put("id", this.id);
        gamePlayerDTO.put("player", this.player.getPlayerDTO());
        gamePlayerDTO.put("joinDate", this.joinDate);
        return gamePlayerDTO;
    }

    public Map<String, Object> getGamePlayerViewDTO() {
        Map<String, Object> gamePlayerDTO = new LinkedHashMap<>();
        gamePlayerDTO.put("id", this.game.getId());
        gamePlayerDTO.put("created", this.game.getCreationDate());
        gamePlayerDTO.put("gamePlayers", this.game.getGamePlayersDTO());
        gamePlayerDTO.put("ships", this.getGPlayerShipsDTO());
        gamePlayerDTO.put("salvos", this.game.getGameSalvosDTO());
        gamePlayerDTO.put("hits", this.game.getHitsDTO());
        return gamePlayerDTO;
    }






}


