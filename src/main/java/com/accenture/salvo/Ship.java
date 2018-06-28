package com.accenture.salvo;

import javax.persistence.*;
import java.util.*;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String shipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gamePlayer_id")
    private GamePlayer gamePlayer;

    @ElementCollection
    @Column(name="location")
    private List<String> locations;

    public Ship( ) {    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }


    public Ship(String shipType, GamePlayer gamePlayer, List<String> locations) {
        this.shipType = shipType;
        this.gamePlayer = gamePlayer;
        this.locations = locations;
    }

    public Map<String, Object> getShipDTO(){
        Map<String, Object> shipDTO = new LinkedHashMap<>();
        shipDTO.put("type", this.shipType);
        shipDTO.put("locations", this.locations);
        return shipDTO;
    }
}
