package com.accenture.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long turnNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "location")
    private List<String> salvoLocations;

    public Salvo() {
    }

    public long getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(long turnNumber) {
        this.turnNumber = turnNumber;
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getSalvoLocations() {
        return salvoLocations;
    }

    public void setSalvoLocations(List<String> salvoLocations) {
        this.salvoLocations = salvoLocations;
    }

    public Salvo(long turnNumber , GamePlayer gamePlayer, List<String> salvoLocations) {
        this.turnNumber = turnNumber;
        this.gamePlayer = gamePlayer;
        this.salvoLocations = salvoLocations;
    }

    public Map<String, Object> getSalvoDTO(){
        Map<String, Object> salvoList = new LinkedHashMap<>();
        salvoList.put("turn", this.turnNumber);
        salvoList.put("player", this.gamePlayer.getPlayer().getId());
        salvoList.put("locations", this.salvoLocations);
        return salvoList;
    }

}
