package com.accenture.salvo;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private long id;
    private String userName;
    private String password;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer>  gamePlayers = new HashSet<>();

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score>  scores = new HashSet<>();

    @Override
    public String toString() {
        return  "username: " + this.userName;
    }

    public Player(){}

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addGamePlayer(GamePlayer gamePlayer){
        this.gamePlayers.add(gamePlayer);
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public List<Game> getGames(){
        return this.gamePlayers.stream().map(game -> game.getGame()).collect(Collectors.toList());
    }

    private long getWonGames() {
        long total = scores.stream().filter(score -> score.getScore() == 1).count();
        return total;
    }

    private double getLostGames() {
        long total = scores.stream().filter(score -> score.getScore() == 0).count();
        return total;
    }

    private long getTiedGames() {
        long total = scores.stream().filter(score -> score.getScore() == 0.5).count();
        return total;
    }

    public Map<String, Object> getPlayerDTO() {
        Map<String,Object>  playerDTO = new LinkedHashMap<>();
        playerDTO.put("id", this.id);
        playerDTO.put("email", this.userName);
        return playerDTO;
    }

    public Object getAllScoreDTO() {
        Map<String,Object> AllScoreDTO = new LinkedHashMap<>();
        AllScoreDTO.put("name", this.userName);
        AllScoreDTO.put("score", this.getScoreResumeDTO());
        return AllScoreDTO;
    }

    private Object getScoreResumeDTO() {
        Map<String,Object> scoreResume = new LinkedHashMap<>();
        long totalWon = this.getWonGames();
        double totalLost = this.getLostGames();
        double totalTie = this.getTiedGames();
        double totalTotal = scores.stream().filter(score ->
                score.getScore() != -1).mapToDouble(score -> score.getScore()).sum();
        scoreResume.put("total", totalTotal);
        scoreResume.put("won", totalWon);
        scoreResume.put("lost", totalLost);
        scoreResume.put("tied", totalTie);
        return scoreResume;
    }
}

