/**
 * Student ID: 20222070/W1985534
 * Name: Thilaganathan Prajoathayan
 *
 * The class to create an object named cell
 */

public class Cell {
    // coordinates
    public int i, j;
    public String value;

    // parent cell for path
    public Cell parent;

    // Heuristic cost of the current cell
    public int heuristicCost;

    //Final cost = G(n) cost + Heuristic cost
    public int finalCost; //G + H with

    public boolean solution; //if cell is part of the solution path
    public Cell(int i, int j, String value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + i + ", " + j + "] -> " + value ;
    }
}
