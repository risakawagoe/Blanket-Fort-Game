package ca.cmpt213.gameapi.restapi;

import ca.cmpt213.gameapi.models.FortMap;

import java.util.List;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiBoardDTO {
    public int boardWidth;
    public int boardHeight;

    private ApiBoardDTO(int boardWidth, int boardHeight, String[][] cellStates) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.cellStates = cellStates;
    }

    // celState[row]col] = {"fog", "hit", "fort", "miss", "field"}
    public String[][] cellStates;

    public static ApiBoardDTO createInstance(FortMap fortMap, boolean cheatMode) {
        List<List<String>> map = cheatMode ? fortMap.getRevealedMap() : fortMap.getCurrentStateMap();
        String[][] cellStates = map.stream()
                .map(row -> row.toArray(new String[0]))
                .toArray(String[][]::new);
        return new ApiBoardDTO(fortMap.MAP_ROWS, fortMap.MAP_COLUMNS, cellStates);
    }
}
