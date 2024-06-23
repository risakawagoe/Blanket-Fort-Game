package ca.cmpt213.gameapi.restapi;

import ca.cmpt213.gameapi.models.Game;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiGameDTO {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int opponentPoints;
    public long numActiveOpponentForts;

    private ApiGameDTO(
            int gameNumber,
            boolean isGameWon,
            boolean isGameLost,
            int opponentPoints,
            long numActiveOpponentForts,
            int[] lastOpponentPoints) {
        this.gameNumber = gameNumber;
        this.isGameWon = isGameWon;
        this.isGameLost = isGameLost;
        this.opponentPoints = opponentPoints;
        this.numActiveOpponentForts = numActiveOpponentForts;
        this.lastOpponentPoints = lastOpponentPoints;
    }

    // Amount of points that the opponents scored on the last time they fired.
    // If opponents have not yet fired, then it should be an empty array (0 size).
    public int[] lastOpponentPoints;
    public static ApiGameDTO createInstance(Game game, int gameID) {
        return new ApiGameDTO(
                gameID,
                game.pointsManager.won(),
                game.pointsManager.lost(),
                game.pointsManager.getTotalPoints(),
                // if requirement is fortress health then return (REQUIRED_POINTS_TO_WIN - total points)
                game.pointsManager.getOpponentsPoints().size(),
                game.pointsManager.lastOpponentPoints);
    }
}
