package com.accenture.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name = "salvoLocation")
    private List<String> salvoLocations;

    public Salvo() {
    }

    public Salvo(int turn , GamePlayer gamePlayer, List<String> locations) {
        this.turn = turn;
        this.gamePlayer = gamePlayer;
        this.salvoLocations = locations;
    }

    public Map<String, Object> getSalvoDTO(){
        Map<String, Object> salvoList = new LinkedHashMap<>();
        salvoList.put("turn", this.turn);
        salvoList.put("player", this.gamePlayer.getPlayer().getId());
        salvoList.put("locations", this.salvoLocations);
        return salvoList;
    }

/*    public long getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setSalvoLocations(List<String> salvoLocations) {
        this.salvoLocations = salvoLocations;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getSalvoLocations() {
        return salvoLocations;
    }*/


}
