package ca.cmpt213.gameapi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Keeps track of each opponents points by calculating and updating opponents fort size.
 * Has knowledge of point system (relation between fields-remaining-in-fort and point).
 * Determines when game has ended (winning/losing criteria)
 */
public class OpponentsPointsManager {
    public final int opponentCount;
    public final int REQUIRED_POINTS_TO_WIN = 2500;
    private int totalPoints = 0;
    private final SortedMap<Integer, Integer> remainingOpponents = new TreeMap<>();
    private final List<Integer> pointSystem;
    public int[] lastOpponentPoints = new int[]{};

    public OpponentsPointsManager(List<Integer> pointSystem, int opponentCount) {
        this.pointSystem = pointSystem;
        this.opponentCount = opponentCount;
        int maxFortSize = pointSystem.size() - 1;
        for(int i = 0; i < opponentCount; i++) {
            remainingOpponents.put(i + 1, maxFortSize);
        }
    }
    public List<Integer> getOpponentsPoints() {
        List<Integer> points = new ArrayList<>();
        for(Integer opponentId : remainingOpponents.keySet()) {
            points.add(pointSystem.get(remainingOpponents.get(opponentId)));
        }
        return points;
    }
    public int getTotalPoints() {
        return totalPoints;
    }
    public boolean lost() {
        // losing condition: opponents earn required points to win
        return totalPoints >= REQUIRED_POINTS_TO_WIN;
    }
    public boolean won() {
        // winning condition: defeat all opponents
        return remainingOpponents.isEmpty();
    }

    /**
     * rowidx and colIdx validity must be checked by client code.
     * @param rowIdx
     * @param colIdx
     * @param map
     */
    public void calculateResult(int rowIdx, int colIdx, FortMap map) {
        boolean wasPreviouslyHit = map.fieldIsHit(rowIdx, colIdx);
        boolean success = map.hit(rowIdx, colIdx);
        if(!wasPreviouslyHit) {
            updateOpponentState(map.getFieldFortId(rowIdx, colIdx));
        }
        updateTotalPoints();
    }
    private void updateTotalPoints() {
        List<Integer> opponentsPoints = getOpponentsPoints();
        lastOpponentPoints = opponentsPoints.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        opponentsPoints.forEach(point -> {
            totalPoints += point;
        });
    }
    private void updateOpponentState(int opponentId) {
        if(remainingOpponents.containsKey(opponentId)) {
            int updatedFortSize = remainingOpponents.get(opponentId) - 1;
            remainingOpponents.put(opponentId, updatedFortSize);
            // eliminate opponent if fort is completely destroyed
            if(updatedFortSize < 1) {
                remainingOpponents.remove(opponentId);
            }
        }
    }
}