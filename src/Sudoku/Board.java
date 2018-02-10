package Sudoku;
import java.util.*;
import static Sudoku.BoardTester.*;
import static Sudoku.BoardStruct.*;
import static Sudoku.BoardUtils.*;
import static Sudoku.BoardSolver.*;

public class Board {
    public static int[][] numbersOnBoard;
    public Row[] rowArr;
    public Column[] colArr;
    public SubMatrix[] matArr;

    public Board(int[][] startingNumbers) {
        numbersOnBoard = startingNumbers;
        this.rowArr = getAllRows(this);
        this.colArr = getAllColumns(this);
        this.matArr = getAllSubMatrices(this);
    }

    public Row getRow(int rowNum) {
        Row r = new Row(this, rowNum);
        return r;
    }

    public Cell getCell(int rowNum, char colName) {
        Cell c = new Cell(colName, rowNum);
        return c;
    }

    public Column getColumn(char colName) {
        Column col = new Column(this, colName);
        return col;
    }

    public SubMatrix getSubMatrix(int num) {
        SubMatrix s = new SubMatrix(this, num);
        return s;
    }

    public static void main(String[] args) {
        // DONT TOUCH
        int[][] original = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        // ###################################
        int[][] test = new int[][]{
                {0, 4, 0, 5, 0, 0, 0, 9, 7},
                {3, 0, 9, 1, 0, 0, 0, 6, 8},
                {5, 1, 0, 0, 9, 0, 0, 0, 3},
                {7, 0, 0, 0, 6, 0, 0, 0, 0},
                {0, 0, 6, 3, 5, 9, 2, 0, 0},
                {0, 0, 0, 0, 4, 0, 0, 0, 9},
                {8, 0, 0, 0, 1, 0, 0, 4, 5},
                {9, 5, 0, 0, 0, 7, 3, 0, 2},
                {2, 7, 0, 0, 0, 5, 0, 8, 0}
        };
        Board normalTestBoard = new Board(test);
        printBoardState(normalTestBoard);
        //r2.printRow();
        Column colB = new Column(normalTestBoard, 'B');
        //System.out.println(colB.numSolved);
        SubMatrix s = new SubMatrix(normalTestBoard, 8);
        //validateSolution();
        solve(normalTestBoard);
        SubMatrix r2 = normalTestBoard.getSubMatrix(1);
    }
}
