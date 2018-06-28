package com.accenture.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class SalvoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SalvoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,
                                      GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository,
                                      SalvoRepository salvoRepository){


            return (args) -> {

//Players

                Player jBauer = playerRepository.save(new Player("j.bauer@ctu.gov"));
                Player cObrian = playerRepository.save(new Player("c.obrian@ctu.gov"));
                Player kBauer = playerRepository.save(new Player("t.almeida@cut.gov"));
                Player tAlmeida = playerRepository.save(new Player("d.palmer@whitehouse.gov"));

//Games

                Game game1 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(3600))));
                Game game2 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(7200))));
                Game game3 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(10800))));
                Game game4 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(14400))));
                Game game5 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(18000))));
                Game game6 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(21600))));
                Game game8 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(25200))));
                Game game7 = gameRepository.save(new Game(Date.from(new Date().toInstant().plusSeconds(28800))));

//GamePlayers

                GamePlayer gamePlayer1 = gamePlayerRepository.save(new GamePlayer(jBauer, game1));
                GamePlayer gamePlayer2 = gamePlayerRepository.save(new GamePlayer(cObrian, game1));
                GamePlayer gamePlayer3 = gamePlayerRepository.save(new GamePlayer(jBauer, game2));
                GamePlayer gamePlayer4 = gamePlayerRepository.save(new GamePlayer(cObrian, game2));
                GamePlayer gamePlayer5 = gamePlayerRepository.save(new GamePlayer(cObrian, game3));
                GamePlayer gamePlayer6 = gamePlayerRepository.save(new GamePlayer(tAlmeida, game3));
                GamePlayer gamePlayer7 = gamePlayerRepository.save(new GamePlayer(cObrian, game4));
                GamePlayer gamePlayer8 = gamePlayerRepository.save(new GamePlayer(jBauer, game4));
                GamePlayer gamePlayer9 = gamePlayerRepository.save(new GamePlayer(tAlmeida, game5));
                GamePlayer gamePlayer10 = gamePlayerRepository.save(new GamePlayer(jBauer, game5));
                GamePlayer gamePlayer11 = gamePlayerRepository.save(new GamePlayer(kBauer, game6));
                GamePlayer gamePlayer12 = gamePlayerRepository.save(new GamePlayer(tAlmeida, game7));
                GamePlayer gamePlayer13 = gamePlayerRepository.save(new GamePlayer(kBauer, game8));
                GamePlayer gamePlayer14 = gamePlayerRepository.save(new GamePlayer(tAlmeida, game8));

//Ship Locations

                List<String> Shiplocation1 = new ArrayList<>(Arrays.asList("H2", "H3", "H4"));
                List<String> Shiplocation2 = new ArrayList<>(Arrays.asList("E1", "F1", "G1"));
                List<String> Shiplocation3 = new ArrayList<>(Arrays.asList("B4", "B5"));
                List<String> Shiplocation4 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation5 = new ArrayList<>(Arrays.asList("F1", "F2"));

                List<String> Shiplocation6 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation7 = new ArrayList<>(Arrays.asList("C6", "C7"));
                List<String> Shiplocation8 = new ArrayList<>(Arrays.asList("A2", "A3", "A4"));
                List<String> Shiplocation9 = new ArrayList<>(Arrays.asList("G6", "H6"));

                List<String> Shiplocation10 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation11 = new ArrayList<>(Arrays.asList("C6", "C7"));
                List<String> Shiplocation12 = new ArrayList<>(Arrays.asList("A2", "A3", "A4"));
                List<String> Shiplocation13 = new ArrayList<>(Arrays.asList("G6", "H6"));

                List<String> Shiplocation14 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation15 = new ArrayList<>(Arrays.asList("C6", "C7"));
                List<String> Shiplocation16 = new ArrayList<>(Arrays.asList("A2", "A3", "A4"));
                List<String> Shiplocation17 = new ArrayList<>(Arrays.asList("G6", "H6"));

                List<String> Shiplocation18 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation19 = new ArrayList<>(Arrays.asList("C6", "C7"));
                List<String> Shiplocation20 = new ArrayList<>(Arrays.asList("A2", "A3", "A4"));
                List<String> Shiplocation21 = new ArrayList<>(Arrays.asList("G6", "H6"));

                List<String> Shiplocation22 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation23 = new ArrayList<>(Arrays.asList("C6", "C7"));

                List<String> Shiplocation24 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> Shiplocation25 = new ArrayList<>(Arrays.asList("C6", "C7"));
                List<String> Shiplocation26 = new ArrayList<>(Arrays.asList("A2", "A3", "A4"));
                List<String> Shiplocation27 = new ArrayList<>(Arrays.asList("G6", "H6"));

//Ships

                Ship ship1 = shipRepository.save(new Ship("Destroyer", gamePlayer1, Shiplocation1));
                Ship ship2 = shipRepository.save(new Ship("Submarine", gamePlayer1, Shiplocation2));
                Ship ship3 = shipRepository.save(new Ship("Patrol Boat", gamePlayer1, Shiplocation3));
                Ship ship4 = shipRepository.save(new Ship("Destroyer", gamePlayer2, Shiplocation4));
                Ship ship5 = shipRepository.save(new Ship("Patrol Boat", gamePlayer2, Shiplocation5));

                Ship ship6 = shipRepository.save(new Ship("Destroyer", gamePlayer2, Shiplocation6));
                Ship ship7 = shipRepository.save(new Ship("Patrol Boat", gamePlayer2, Shiplocation7));
                Ship ship8 = shipRepository.save(new Ship("Submarine", gamePlayer3, Shiplocation8));
                Ship ship9 = shipRepository.save(new Ship("Patrol Boat", gamePlayer3, Shiplocation9));

                Ship ship10 = shipRepository.save(new Ship("Destroyer", gamePlayer4, Shiplocation10));
                Ship ship11 = shipRepository.save(new Ship("Patrol Boat", gamePlayer4, Shiplocation11));
                Ship ship12 = shipRepository.save(new Ship("Submarine", gamePlayer5, Shiplocation12));
                Ship ship13 = shipRepository.save(new Ship("Patrol Boat", gamePlayer5, Shiplocation13));

                Ship ship14 = shipRepository.save(new Ship("Destroyer", gamePlayer6, Shiplocation10));
                Ship ship15 = shipRepository.save(new Ship("Patrol Boat", gamePlayer6, Shiplocation11));
                Ship ship16 = shipRepository.save(new Ship("Submarine", gamePlayer7, Shiplocation12));
                Ship ship17 = shipRepository.save(new Ship("Patrol Boat", gamePlayer7, Shiplocation13));

                Ship ship18 = shipRepository.save(new Ship("Destroyer", gamePlayer8, Shiplocation10));
                Ship ship19 = shipRepository.save(new Ship("Patrol Boat", gamePlayer8, Shiplocation11));
                Ship ship20 = shipRepository.save(new Ship("Submarine", gamePlayer9, Shiplocation12));
                Ship ship21 = shipRepository.save(new Ship("Patrol Boat", gamePlayer9, Shiplocation13));

                Ship ship22 = shipRepository.save(new Ship("Destroyer", gamePlayer10, Shiplocation10));
                Ship ship23 = shipRepository.save(new Ship("Patrol Boat", gamePlayer10, Shiplocation11));

                Ship ship24 = shipRepository.save(new Ship("Destroyer", gamePlayer12, Shiplocation10));
                Ship ship25 = shipRepository.save(new Ship("Patrol Boat", gamePlayer12, Shiplocation11));
                Ship ship26 = shipRepository.save(new Ship("Submarine", gamePlayer13, Shiplocation12));
                Ship ship27 = shipRepository.save(new Ship("Patrol Boat", gamePlayer13, Shiplocation13));



//Salvo Locations

                List<String> salvoLocation1 = new ArrayList<>(Arrays.asList("B5", "C5", "F1"));
                List<String> salvoLocation2 = new ArrayList<>(Arrays.asList("B4", "B5", "B6"));
                List<String> salvoLocation3 = new ArrayList<>(Arrays.asList("F2", "D5"));
                List<String> salvoLocation4 = new ArrayList<>(Arrays.asList("E1", "H3", "A2"));
                List<String> salvoLocation5 = new ArrayList<>(Arrays.asList("A2","A4","A6"));

                List<String> salvoLocation6 = new ArrayList<>(Arrays.asList("B5","D5","C7"));
                List<String> salvoLocation7 = new ArrayList<>(Arrays.asList("A3","H6"));
                List<String> salvoLocation8 = new ArrayList<>(Arrays.asList("C5","C6"));
                List<String> salvoLocation9 = new ArrayList<>(Arrays.asList("G6","H6","A4"));
                List<String> salvoLocation10 = new ArrayList<>(Arrays.asList("H1","H2","H3"));

                List<String> salvoLocation11 = new ArrayList<>(Arrays.asList("A2","A3","D8"));
                List<String> salvoLocation12 = new ArrayList<>(Arrays.asList("E1","F2","G3"));
                List<String> salvoLocation13 = new ArrayList<>(Arrays.asList("A3","A4","F7"));
                List<String> salvoLocation14 = new ArrayList<>(Arrays.asList("B5","C6","H1"));
                List<String> salvoLocation15 = new ArrayList<>(Arrays.asList("A2","G6","H6"));

                List<String> salvoLocation16 = new ArrayList<>(Arrays.asList("C5","C7","D5"));
                List<String> salvoLocation17 = new ArrayList<>(Arrays.asList("A1","A2","A3"));
                List<String> salvoLocation18 = new ArrayList<>(Arrays.asList("B5","B6","C7"));
                List<String> salvoLocation19 = new ArrayList<>(Arrays.asList("G6","G7","G8"));
                List<String> salvoLocation20 = new ArrayList<>(Arrays.asList("C6","D6","E6"));
                List<String> salvoLocation21 = new ArrayList<>(Arrays.asList("H1","H8"));

//Salvos

                Salvo salvo1  = salvoRepository.save(new Salvo(1, gamePlayer1, salvoLocation1));
                Salvo salvo2  = salvoRepository.save(new Salvo(1, gamePlayer2, salvoLocation2));
                Salvo salvo3  = salvoRepository.save(new Salvo(2, gamePlayer1, salvoLocation3));
                Salvo salvo4  = salvoRepository.save(new Salvo(2, gamePlayer2, salvoLocation4));

                Salvo salvo5  = salvoRepository.save(new Salvo(1, gamePlayer3, salvoLocation5));
                Salvo salvo6  = salvoRepository.save(new Salvo(1, gamePlayer4, salvoLocation6));
                Salvo salvo7  = salvoRepository.save(new Salvo(2, gamePlayer3, salvoLocation7));
                Salvo salvo8  = salvoRepository.save(new Salvo(2, gamePlayer4, salvoLocation8));

                Salvo salvo9  = salvoRepository.save(new Salvo(1, gamePlayer5, salvoLocation9));
                Salvo salvo10 = salvoRepository.save(new Salvo(1, gamePlayer6, salvoLocation10));
                Salvo salvo11 = salvoRepository.save(new Salvo(2, gamePlayer5, salvoLocation11));
                Salvo salvo12 = salvoRepository.save(new Salvo(2, gamePlayer6, salvoLocation12));

                Salvo salvo13 = salvoRepository.save(new Salvo(1, gamePlayer7, salvoLocation13));
                Salvo salvo14 = salvoRepository.save(new Salvo(1, gamePlayer8, salvoLocation14));
                Salvo salvo15 = salvoRepository.save(new Salvo(2, gamePlayer7, salvoLocation15));
                Salvo salvo16 = salvoRepository.save(new Salvo(2, gamePlayer8, salvoLocation16));

                Salvo salvo17 = salvoRepository.save(new Salvo(1, gamePlayer9, salvoLocation17));
                Salvo salvo18 = salvoRepository.save(new Salvo(1, gamePlayer10, salvoLocation18));
                Salvo salvo19 = salvoRepository.save(new Salvo(2, gamePlayer9, salvoLocation19));
                Salvo salvo20 = salvoRepository.save(new Salvo(2, gamePlayer10, salvoLocation20));
                Salvo salvo21 = salvoRepository.save(new Salvo(3, gamePlayer10, salvoLocation21));


            };

        }
}