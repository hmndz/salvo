package com.accenture.salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository repoGames;


    @RequestMapping("/games")
    public List<Object> getId() {
        return repoGames.findAll().stream()
                .map(this::gameDTO)
                .collect(Collectors.toList());
    }

    private Map<String, Object> gameDTO(Game games) {
        Map<String, Object> dto = new LinkedHashMap <String, Object>();
        dto.put("Id", games.getId());
        dto.put("Created", games.getCreationDate());
        dto.put("GamePlayers", games.getGamePlayers());
        return dto;
    }

   /* private Map<String, Object> gameDTO(Player players) {

        Map<String, Object> pto = new LinkedHashMap <String, Object>();
        pto.put("First", players.getFirstName());
        pto.put("Last", players.getLastName());
        pto.put("User", players.getUserName());
        return pto;
    }*/




}

   /* @RequestMapping("api/games")
    public Map<String, Object> IDyCreatedMetodo(Game game, Authentication authentication) {
        ArrayList IDyCreatedList = new ArrayList();
        List<Game> repoGamesfindAll = repoGames.findAll();
        String nameAuth = "NombreSinLog";
        String IDAuth = "IDSinLog";

        if (authentication != null) {
            nameAuth = authentication.getName();
            IDAuth = "" + repoPlayers.findByUserName(nameAuth).get(0).getId();
        }
        Set<GamePlayer> gamePlayers = game.getGamePlayers();

        for (int i = 0; i < repoGamesfindAll.size(); i++) {
            Map<String, Object> IDyCreatedMap = new HashMap<String, Object>();
            IDyCreatedMap.put("gameID", repoGamesfindAll.get(i).getId());
            IDyCreatedMap.put("gameCreated", repoGamesfindAll.get(i).getFechaV());

            IDyCreatedMap.put("gamePlayers", repoGamesfindAll.get(i).getGamePlayers().stream()
                    .map(gameLambda -> gamePlayerDTO(gameLambda))
                    .collect(Collectors.toList()));

            IDyCreatedList.add(IDyCreatedMap);
        }
        Map<String, String> playerLogueado = new HashMap<>();

        playerLogueado.put("ID", IDAuth);

        playerLogueado.put("name", nameAuth);

        Map<String, Object> gamesYplayLog = new HashMap<>();

        gamesYplayLog.put("games", IDyCreatedList);

        gamesYplayLog.put("playerLogueado", playerLogueado);

        return gamesYplayLog;
    }*/

