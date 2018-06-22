package com.accenture.salvo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.*;
import java.util.Date;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private Date joinDate;

    public Date getJoinDate() {
        return joinDate;
    }

    /*private GamePlayer gamePlayer;*/

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player players;

    public void setPlayer (Player players){
        this.players = players;
    }

    public Player getPlayer(){
        return players;
    }

    public Player getPlayers(){
        return players;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game games;

    public void setGame (Game games){
        this.games = games;
    }

    public Game getGame(){
        return games;
    }

    public Game getGames(){
        return games;
    }


    public GamePlayer(){ }

    public GamePlayer(Game games, Player players, Date joinDate) {
        this.games = games;
        this.players = players;
        this.joinDate = new Date();

    }

   /* public void setPlayer (Player players){
        this.players = players;
    }

    public Game getGames() {
        return games;
    }

    public void setGames (Game games){
        this.games = games;
    }*/

}