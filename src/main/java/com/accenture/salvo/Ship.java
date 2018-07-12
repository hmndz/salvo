package com.accenture.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    public enum ShipType {carrier , battleship, submarine, destroyer, patrolboat}
    private ShipType shipType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="shipLocation")
    private List<String> shipLocations;

    public Ship( ) {    }


    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void setShipLocations(List<String> shipLocations) {
        this.shipLocations = shipLocations;
    }

    public List<String> getShipLocations() {
        return this.shipLocations;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public Ship(ShipType shipType, GamePlayer gamePlayer, List<String> shipLocations) {
        this.shipType = shipType;
        this.gamePlayer = gamePlayer;
        this.shipLocations = shipLocations;
    }

    public Map<String, Object> getShipDTO(){
        Map<String, Object> shipDTO = new LinkedHashMap<>();
        shipDTO.put("shipType", this.shipType);
        shipDTO.put("shipLocations", this.shipLocations);
        return shipDTO;
    }

}
