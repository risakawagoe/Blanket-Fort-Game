package ca.cmpt213.gameapi.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    public final FortMap fortMap;
    public final OpponentsPointsManager pointsManager;
    public boolean cheatMode = false;

    private Game(FortMap fortMap, OpponentsPointsManager pointsManager) {
        this.fortMap = fortMap;
        this.pointsManager = pointsManager;
    }
    public void activateCheatMode() {
        cheatMode = true;
    }
    public static Game createInstance() {
        final int DEFAULT_OPPONENT_COUNT = 5;
        final int MAP_ROWS = 10;
        final int MAP_COLUMNS = 10;
        List<Integer> pointSystem = new ArrayList<>(Arrays.asList(0, 1, 2, 5, 20, 20));

        try {
            OpponentsPointsManager pointsManager = new OpponentsPointsManager(pointSystem, DEFAULT_OPPONENT_COUNT);
            FortMap fortMap = new FortMap(MAP_ROWS, MAP_COLUMNS, pointsManager.opponentCount, pointSystem.size() - 1);
            return new Game(fortMap, pointsManager);
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
