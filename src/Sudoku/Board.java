package Sudoku;
import java.util.*;
import static Sudoku.BoardTester.*;

public class Board {
    public static int[][] numbersOnBoard;

    public Board(int[][] startingNumbers) {
        numbersOnBoard = startingNumbers;
    }

    public static void main(String[] args) {
        int[][] original = new int[][]{
                {3, 1, 2, 4, 5, 6, 7, 8, 9},
                {1, 2, 3, 2, 2, 2, 2, 2, 2},
                {0, 0, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println("DISCLAIMER: INDICES ARE 1-INDEXED");
        int[][] normaltest = new int[][]{
                {2, 9, 6, 3, 1, 8, 5, 7, 4},
                {5, 8, 4, 9, 7, 2, 6, 1, 3},
                {7, 1, 3, 6, 4, 5, 2, 8, 9},
                {6, 2, 5, 8, 9, 7, 3, 4, 1},
                {9, 3, 1, 4, 2, 6, 8, 5, 7},
                {4, 7, 8, 5, 3, 1, 9, 2, 6},
                {1, 6, 7, 2, 5, 3, 4, 9, 8},
                {8, 5, 9, 7, 6, 4, 1, 3, 2},
                {3, 4, 2, 1, 8, 9, 7, 6, 5},
        };
        Board normalTestBoard = new Board(normaltest);
        //printBoardState(test);
        //normalTestBoard.validateSolution();
        validateSolution();
    }
}
