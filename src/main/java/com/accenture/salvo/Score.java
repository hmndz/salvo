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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        this.score = score;
        if (this.score != -1) {
            this.finishDate = game.getCreationDate();
            this.finishDate.toInstant().plusSeconds(1800);
        } else {
            this.finishDate = null;
        }
        this.game = game;
        this.player = player;
    }

    public Object getScoreDTO() {
        Map<String,Object> scoreDTO = new LinkedHashMap<>();
        scoreDTO.put("id", this.id);
        scoreDTO.put("score", this.score);
        scoreDTO.put("finish date", this.finishDate);
        return scoreDTO;
    }




}