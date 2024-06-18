import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Student ID: 20222070/W1985534
 * Name: Thilaganathan Prajoathayan
 *
 * The class that consists the method to find the shortest path between nodes
 */

public class FindPath {

    //costs vertical / horizontal moves
    public static final int V_H_COST = 1;

    //Cells of grid
    private Cell[][] grid;
    //priorty queue for open cells
    //Open Cells: the set of nodes to be evaluated

    private PriorityQueue<Cell> openCells;
    //Closed Cells : the set of nodes already evaluated
    private boolean[][] closedCells;
    //Start Cell
    private int startI, startJ;

    //End Cell
    private int endI, endJ;

    //Constructor to intiate the grid, closedcells, openCells
    public FindPath(Cell[][] grid){
        this.grid = grid;
        closedCells = new boolean[grid.length][grid[0].length];
        openCells = new PriorityQueue<>((Cell c1, Cell c2) -> {
            return c1.finalCost < c2.finalCost ? -1 : c1.finalCost > c2.finalCost ? 1 : 0;
        });

        //Find the starting point and the ending point
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j].value.equals("S")){
                    startI = i;
                    startJ = j;
                }
                if(grid[i][j].value.equals("F")){
                    endI = i;
                    endJ= j;
                }
            }
        }
        // init heuristic and cells
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].heuristicCost = Math.abs(i - endI) + Math.abs(j - endJ);
                grid[i][j].solution = false;
            }
        }
        grid[startI][startJ].finalCost = 0;
    }
    //To update the final cost for each cell
    public void updateCostIfNeeded(Cell current, Cell t, int cost) {
        if (t.value.equals("0") || closedCells[t.i][t.j]) {
            return;
        }

        int tFinalCost = t.heuristicCost + cost;
        boolean isOpen = openCells.contains(t);

        if (!isOpen || tFinalCost < t.finalCost) {
            t.finalCost = tFinalCost;
            t.parent = current; // Assign the parent cell

            if (!isOpen) {
                openCells.add(t);
            }
        }
    }

    //A* star algorithm to find the shortest path
    public void process() {
        // We add the start location to open list
        openCells.add(grid[startI][startJ]);
        Cell current;

        while (true) {
            current = openCells.poll();

            if (current.value.equals("0") || closedCells[current.i][current.j]) {
                break;
            }

            closedCells[current.i][current.j] = true;

            if (current.equals(grid[endI][endJ])) {
                return;
            }

            // Handle sliding in each direction
            slide(current, -1, 0); // Slide up
            slide(current, 1, 0);  // Slide down
            slide(current, 0, -1); // Slide left
            slide(current, 0, 1);  // Slide right
        }
    }

    //Method to travel through a direction until finds a rock or the wall
    private void slide(Cell current, int dI, int dJ) {
        int i = current.i + dI;
        int j = current.j + dJ;
        int cost = 0;

        // Continue sliding until hitting a wall or a rock
        while (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && !grid[i][j].value.equals("0")) {
            // Update cost for sliding
            cost += V_H_COST;
            // Break if we reach the end cell
            if (i == endI && j == endJ) {
                updateCostIfNeeded(current, grid[i][j], current.finalCost + cost);
                return;
            }

            // Move to the next cell
            i += dI;
            j += dJ;

        }

        // Update cost for the last reachable cell in the sliding direction
        if (i - dI >= 0 && i - dI < grid.length && j - dJ >= 0 && j - dJ < grid[0].length) {
            Cell t = grid[i - dI][j - dJ];
            updateCostIfNeeded(current, t, current.finalCost + cost);
        }
    }
    //Method to print the path to the start point to end
    public void displaySolution() {
        if (closedCells[endI][endJ]) {
            // Track back the path
            System.out.println("Path: ");
            Cell current = grid[endI][endJ];
            List<Cell> path = new ArrayList<>();
            path.add(current);

            while (current.parent != null) {
                path.add(0, current.parent);
                current = current.parent;
            }

            // Print the path
            System.out.println("1. Start at (" + (path.get(0).j + 1) + "," + (path.get(0).i + 1) + ")");
            for (int i = 1; i < path.size(); i++) {
                String direction = getDirection(path.get(i - 1), path.get(i));
                System.out.println((i + 1) + ". Move " + direction + " to (" + (path.get(i).j + 1) + "," + (path.get(i).i + 1) + ")");
            }
            System.out.println((path.size() + 1) + ". Done!");

        } else {
            System.out.println("No Possible Path");
        }
    }

    //To get the direction to be printed in the solution
    private static String getDirection(Cell from, Cell to) {
        if (to.i < from.i) return "up";
        if (to.i > from.i) return "down";
        if (to.j < from.j) return "left";
        if (to.j > from.j) return "right";
        return "undefined"; // Should not happen
    }

}
