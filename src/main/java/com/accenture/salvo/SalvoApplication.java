package com.accenture.salvo;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class);
	}

	@Bean
    public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository,
                                      GamePlayerRepository gamePlayerRepository) {
	    return (args) -> {

	        //sample of users

            Player player1 = (new Player("Jack", "Bauer", "JB@gmail.com"));
            Player player2 = (new Player("Chloe", "O'brian", "CO@gmail.com"));
            Player player3 = (new Player("Kim", "Bauer", "KB@gmail.com"));
            Player player4 = (new Player("Tony", "Almeida", "TA@gmail.com"));

            playerRepository.save(player1);
            playerRepository.save(player2);
            playerRepository.save(player3);
            playerRepository.save(player4);

            Date date = new Date();
            Date date1 = Date.from(date.toInstant().plusSeconds(3600));
            Date date2 = Date.from(date.toInstant().plusSeconds(7200));
            Game Game1 = new Game(date);
            Game Game2 = new Game(date1);
            Game Game3 = new Game(date2);

            gameRepository.save(Game1);
            gameRepository.save(Game2);
            gameRepository.save(Game3);

            Date joinDate = new Date();

            GamePlayer gamePlayer1 = new GamePlayer(Game1, player1, joinDate);
            GamePlayer gamePlayer2 = new GamePlayer(Game2, player2, joinDate);
            GamePlayer gamePlayer3 = new GamePlayer(Game3, player3, joinDate);

            gamePlayerRepository.save(gamePlayer1);
            gamePlayerRepository.save(gamePlayer2);
            gamePlayerRepository.save(gamePlayer3);

        };
    }

}
