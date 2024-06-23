package ca.cmpt213.gameapi.models;

/**
 * Keeps track of field specific information (occupancy and hit status).
 *     fortId: numeric ID of opponent that has its fort on this field
 *     hit: true if has been hit at least once, otherwise false
 */
public class Field {
    private final int UNOCCUPIED_FORTID = 0;
    private int fortId = UNOCCUPIED_FORTID;
    private boolean hit = false;
    public void assignTo(int fortId) {
        this.fortId = fortId;
    }
    public boolean hit() {
        hit = true;
        return fortId != UNOCCUPIED_FORTID;
    }
    public int getFortId() {
        return fortId;
    }
    public boolean isHit() {
        return hit;
    }
    public boolean isUnoccupied() {
        return fortId == UNOCCUPIED_FORTID;
    }
}