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

                Player jBauer = new Player("j.bauer@ctu.gov");
                Player cObrian = new Player("c.obrian@ctu.gov");
                Player kBauer = new Player("t.almeida@cut.gov");
                Player tAlmeida = new Player("d.palmer@whitehouse.gov");

                playerRepository.save(jBauer);
                playerRepository.save(cObrian);
                playerRepository.save(kBauer);
                playerRepository.save(tAlmeida);

//Games

                Game game1 = new Game(Date.from(new Date().toInstant().plusSeconds(3600)));
                Game game2 = new Game(Date.from(new Date().toInstant().plusSeconds(7200)));
                Game game3 = new Game(Date.from(new Date().toInstant().plusSeconds(10800)));
                Game game4 = new Game(Date.from(new Date().toInstant().plusSeconds(14400)));
                Game game5 = new Game(Date.from(new Date().toInstant().plusSeconds(18000)));
                Game game6 = new Game(Date.from(new Date().toInstant().plusSeconds(21600)));

                gameRepository.save(game1);
                gameRepository.save(game2);
                gameRepository.save(game3);
                gameRepository.save(game4);
                gameRepository.save(game5);
                gameRepository.save(game6);

//GamePlayers

                GamePlayer gamePlayer1 = new GamePlayer(jBauer, game1);
                GamePlayer gamePlayer2 = new GamePlayer(cObrian, game1);
                GamePlayer gamePlayer3 = new GamePlayer(jBauer, game2);
                GamePlayer gamePlayer4 = new GamePlayer(cObrian, game2);
                GamePlayer gamePlayer5 = new GamePlayer(cObrian, game3);
                GamePlayer gamePlayer6 = new GamePlayer(tAlmeida, game3);
                GamePlayer gamePlayer7 = new GamePlayer(jBauer, game4);
                GamePlayer gamePlayer8 = new GamePlayer(cObrian, game4);
                GamePlayer gamePlayer9 = new GamePlayer(tAlmeida, game5);
                GamePlayer gamePlayer10 = new GamePlayer(jBauer, game5);
                GamePlayer gamePlayer11 = new GamePlayer(tAlmeida, game6);

                gamePlayerRepository.save(gamePlayer1);
                gamePlayerRepository.save(gamePlayer2);
                gamePlayerRepository.save(gamePlayer3);
                gamePlayerRepository.save(gamePlayer4);
                gamePlayerRepository.save(gamePlayer5);
                gamePlayerRepository.save(gamePlayer6);
                gamePlayerRepository.save(gamePlayer7);
                gamePlayerRepository.save(gamePlayer8);
                gamePlayerRepository.save(gamePlayer9);
                gamePlayerRepository.save(gamePlayer10);
                gamePlayerRepository.save(gamePlayer11);

//ShipLocations

                List<String> location1 = new ArrayList<>(Arrays.asList("H2", "H3", "H4"));
                List<String> location2 = new ArrayList<>(Arrays.asList("E1", "F1", "G1"));
                List<String> location3 = new ArrayList<>(Arrays.asList("B4", "B5"));
                List<String> location4 = new ArrayList<>(Arrays.asList("B5", "C5", "D5"));
                List<String> location5 = new ArrayList<>(Arrays.asList("F1", "F2"));

//Ships

                Ship ship1 = new Ship("Destroyer", gamePlayer1, location1);
                Ship ship2 = new Ship("Submarine", gamePlayer1, location2);
                Ship ship3 = new Ship("Patrol Boat", gamePlayer1, location3);
                Ship ship4 = new Ship("Destroyer", gamePlayer2, location4);
                Ship ship5 = new Ship("Patrol Boat", gamePlayer2, location5);

                shipRepository.save(ship1);
                shipRepository.save(ship2);
                shipRepository.save(ship3);
                shipRepository.save(ship4);
                shipRepository.save(ship5);

//Turns

                long turn1 = 1;
                long turn2 = 2;

//SalvosLocations

                List<String> salvoLocation1 = new ArrayList<>(Arrays.asList("B5", "C5", "F1"));
                List<String> salvoLocation2 = new ArrayList<>(Arrays.asList("B4", "B5", "B6"));
                List<String> salvoLocation3 = new ArrayList<>(Arrays.asList("F2", "D5"));
                List<String> salvoLocation4 = new ArrayList<>(Arrays.asList("E1", "H3", "A2"));

//Salvos

                Salvo salvo1 = new Salvo(turn1, gamePlayer1, salvoLocation1);
                Salvo salvo2 = new Salvo(turn1, gamePlayer2, salvoLocation2);
                Salvo salvo3 = new Salvo(turn2, gamePlayer1, salvoLocation3);
                Salvo salvo4 = new Salvo(turn2, gamePlayer2, salvoLocation4);

                salvoRepository.save(salvo1);
                salvoRepository.save(salvo2);
                salvoRepository.save(salvo3);
                salvoRepository.save(salvo4);


                };

        }
}