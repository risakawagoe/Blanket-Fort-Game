package ca.cmpt213.gameapi.controllers;

import ca.cmpt213.gameapi.models.Game;
import ca.cmpt213.gameapi.restapi.ApiBoardDTO;
import ca.cmpt213.gameapi.restapi.ApiGameDTO;
import ca.cmpt213.gameapi.restapi.ApiLocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameAPI {
    private final List<Game> games = new ArrayList<>();
    @GetMapping("/api/about")
    @ResponseBody
    public String getAbout() {
        return "a cute little sheep:)";
    }
    // Games
    @GetMapping("/api/games")
    @ResponseBody
    public ResponseEntity<List<ApiGameDTO>> getGames() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        games.stream()
                                .map(game -> ApiGameDTO.createInstance(game, games.indexOf(game)))
                                .toList()
                );
    }
    @PostMapping("/api/games")
    @ResponseBody
    public ResponseEntity<ApiGameDTO> createGame() {
        try {
            games.add(Game.createInstance());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiGameDTO.createInstance(games.getLast(), games.size() - 1));
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/api/games/{gameID}")
    public ResponseEntity<ApiGameDTO> getGame(@PathVariable String gameID) {
        int intGameID;
        try {
            intGameID = Integer.parseInt(gameID);
            ApiGameDTO game = ApiGameDTO.createInstance(games.get(intGameID), intGameID);
            return ResponseEntity.status(HttpStatus.OK).body(game);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Board
    @GetMapping("/api/games/{gameID}/board")
    public ResponseEntity<ApiBoardDTO> getBoardState(@PathVariable String gameID) {
        int intGameID;
        try {
            intGameID = Integer.parseInt(gameID);
            ApiBoardDTO board = ApiBoardDTO.createInstance(
                    games.get(intGameID).fortMap,
                    games.get(intGameID).cheatMode);
            return ResponseEntity.status(HttpStatus.OK).body(board);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Moves
    @PostMapping("/api/games/{gameID}/moves")
    public ResponseEntity<Object> makeMove(@PathVariable String gameID, @RequestBody ApiLocationDTO locationDTO) {
        try {
            int intGameID = Integer.parseInt(gameID);
            Game game = games.get(intGameID);
            if(locationDTO == null || !game.fortMap.isValidMove(locationDTO.row, locationDTO.col)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            game.pointsManager.calculateResult(locationDTO.row, locationDTO.col, game.fortMap);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Cheats
    @PostMapping("/api/games/{gameID}/cheatstate")
    public ResponseEntity<Object> activateCheatState(@PathVariable String gameID, @RequestBody String cheatCommand) {
        final String CHEAT_KEY = "SHOW_ALL";

        try {
            int intGameID = Integer.parseInt(gameID);
            Game game = games.get(intGameID);
            if(cheatCommand == null || !cheatCommand.equals(CHEAT_KEY)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            game.activateCheatMode();
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (IndexOutOfBoundsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    private void log(String request) {
        System.out.println("SYSTEM LOG: " + request);
    }
}
