package ca.cmpt213.gameapi.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores fields in the map as a 2D list and updates fields when given field gets hit.
 * Provides different printable representation depending on the field visibility preference.
 */
public class FortMap {
    public final int MAP_ROWS;
    public final int MAP_COLUMNS;
    private final List<List<Field>> map;
    private final String FOG = "fog";
    private final String HIT = "hit";
    private final String FORT = "fort";
    private final String MISS = "miss";
    private final String FIELD = "field";

    public FortMap(int rows, int cols, int opponentCount, int fortSize) {
        // create blank map
        List<List<Field>> fortMap = new ArrayList<>();
        for(int i = 0; i < rows; i ++) {
            List<Field> row = new ArrayList<>();
            for(int j = 0; j < cols; j++) {
                row.add(new Field());
            }
            fortMap.add(row);
        }
        // place forts on the blank map
        FortMapBuilder fortMapBuilder = new FortMapBuilder();
        fortMapBuilder.build(fortMap, opponentCount, fortSize);

        this.map = fortMap;
        this.MAP_ROWS = map.size();
        this.MAP_COLUMNS = map.get(0).size();
    }
    public boolean isValidMove(int rowIdx, int colIdx) {
        boolean isValidRowIdx = rowIdx >= 0 && rowIdx < MAP_ROWS;
        boolean isValidColIDx = colIdx >= 0 && colIdx < MAP_COLUMNS;
        return isValidRowIdx && isValidColIDx;
    }
    public boolean hit(int rowIdx, int colIdx) {
        return map.get(rowIdx).get(colIdx).hit();
    }
    public boolean fieldIsHit(int rowIdx, int colIdx) {
        return map.get(rowIdx).get(colIdx).isHit();
    }
    public int getFieldFortId(int rowIdx, int colIdx) {
        return map.get(rowIdx).get(colIdx).getFortId();
    }
    public List<List<String>> getCurrentStateMap() {
        return getMapRepresentation(false);
    }
    public List<List<String>> getRevealedMap() {
        return getMapRepresentation(true);
    }
    private List<List<String>> getMapRepresentation(boolean visible) {
        List<List<String>> fortMap = new ArrayList<>();
        for(int rowIdx = 0; rowIdx < MAP_ROWS; rowIdx ++) {
            List<String> rowMap = new ArrayList<>();
            for(int colIdx = 0; colIdx < MAP_COLUMNS; colIdx++) {
                rowMap.add(getFieldDisplaySymbol(rowIdx, colIdx, visible));
            }
            fortMap.add(rowMap);
        }
        return fortMap;
    }
    private String getFieldDisplaySymbol(int rowIdx, int colIdx, boolean visible) {
        Field field = map.get(rowIdx).get(colIdx);
        int fortId = field.getFortId();
        String charFortId = FOG;
        if(visible) {
            if(fortId == 0) {
                charFortId = field.isHit() ? MISS : FIELD;
            }else {
                charFortId = field.isHit() ? HIT : FORT;
            }
        }else if(field.isHit()) {
            charFortId = fortId == 0 ? MISS : HIT;
        }
        return charFortId;
    }
}