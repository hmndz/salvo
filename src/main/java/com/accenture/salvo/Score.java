package com.accenture.salvo;


import javax.persistence.*;
import java.util.*;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private double score;
    private Date finishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    public Score(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Score(double score, Game game, Player player) {
        this.player = player;
        this.game = game;
        this.score = score;
        if (this.score != -1) {
            this.finishDate = game.getCreationDate();
            this.finishDate.toInstant().plusSeconds(1800);
        } else {
            this.finishDate = null;
        }
    }

    public Object getPlayerScore() {
        Map<String,Object> playerScoreDTO = new LinkedHashMap<>();
        playerScoreDTO.put("game", this.id);
        playerScoreDTO.put("score", this.score);
        playerScoreDTO.put("finish date", this.finishDate);
        return playerScoreDTO;
    }




}