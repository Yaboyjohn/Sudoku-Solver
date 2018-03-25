package Sudoku;
import java.util.*;
import static Sudoku.BoardTester.*;
import static Sudoku.BoardStruct.*;
import static Sudoku.BoardUtils.*;
import Sudoku.BoardSolver.*;
import static Sudoku.Row.*;

public class Board {
    public int[][] numbersOnBoard;
    public Row[] rowArr;
    public Column[] colArr;
    public SubMatrix[] matArr;

    public Board(int[][] startingNumbers) {
        this.numbersOnBoard = startingNumbers;
        this.matArr = getAllSubMatrices();
        this.rowArr = getAllRows();
        this.colArr = getAllColumns();
    }

    /** These getters are all 1-indexed **/
    public Row getRow(int rowNum) {
        return rowArr[rowNum-1];
    }

    public Cell getCell(int rowNum, char colName) {
        Cell c = new Cell(colName, rowNum);
        return c;
    }

    public Column getColumn(char colName) {
        return colArr[colName - 'A'];
    }

    public Column getColumn(int colNum) {
        char c = (char) (colNum+64);
        Column col = new Column(this, c);
        return colArr[colNum-1];

    }

    public SubMatrix getSubMatrix(int num) {
        return matArr[num-1];
    }

    /**
     *
     * @param colNum 1-indexed
     * @param col
     * @param rowNum 0-indexed
     * @param newNum
     */
    public void setColumn(int colNum, Column col, int rowNum, int newNum) {
        this.colArr[colNum-1] = col;
        Row row = this.rowArr[rowNum];
        row.values[colNum-1] = newNum;
        SubMatrix mat = getConflictingMatrix(col, getRow(rowNum+1));
        mat.update(rowNum, colNum-1, newNum);
        this.matArr[mat.matrixNum-1] = mat;
    }


    public Board clone() {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = this.numbersOnBoard[i][j];
            }
        }
        return new Board(copy);
    }

    public void setBoard(Board board) {
        numbersOnBoard = board.numbersOnBoard;
        matArr = board.getAllSubMatrices();
        rowArr = board.getAllRows();
        colArr = board.getAllColumns();

    }

    public Row[] getAllRows() {
        Row[] rowArr = new Row[9];
        for (int i = 1; i < 10; i++) {
            rowArr[i-1] = new Row(this, i);
        }
        return rowArr;
    }

    public Column[] getAllColumns() {
        Column[] colArr = new Column[9];
        for (int i = 0; i < 9; i++) {
            char c = (char) (i+65);
            colArr[i] = new Column(this, c);
        }
        return colArr;
    }

    public SubMatrix[] getAllSubMatrices() {
        SubMatrix[] matArr = new SubMatrix[9];
        for (int i = 1; i < 10; i++) {
            matArr[i-1] = new SubMatrix(this, i);
        }
        return matArr;
    }

    public ArrayList<Integer> clone(ArrayList<Integer> list) {
        ArrayList<Integer> copy = new ArrayList<>();
        for (int x : list) copy.add(x);
        return copy;
    }

    public SubMatrix getConflictingMatrix(Column col, Row row) {
        int colNum = col.colNum;
        int rowNum = row.rowNum;
        if (colNum <= 3) {
            if (rowNum <= 3) return getSubMatrix(1);
            else if (rowNum < 6) return getSubMatrix(4);
            else return getSubMatrix(7);
        } else if (colNum < 6) {
            if (rowNum <= 3) return getSubMatrix(2);
            else if (rowNum < 6) return getSubMatrix(5);
            else return getSubMatrix(8);
        } else {
            if (rowNum <= 3) return getSubMatrix(3);
            else if (rowNum < 6) return getSubMatrix(6);
            else return getSubMatrix(9);
        }
    }

    // if this returns 0 it means that placing num in struct is guaranteed to be correct
    // index here is 0-indexed
    // modifies the board passed in
    public int getNumConflicts(Row row, int num, int index) {
        int[] newVals = row.values;
        newVals[index] = num;
        Row newRow = new Row(newVals, row.rowNum);
//        Column conflictingCol = board.getColumn(index+1);
//        if (numPossiblSpotsInMatrix(board, conflictingMatNumber) == 0) {
//            return 0;
//        } else {
//            if (num)
//        }
//                && checkColConflict(board, conflictingCol)) {
//            return 0;
//        }
        return 1;
    }

    /**
     *
     * @param colNum the number of the column we are testing
     * @param potentialVal the number we wish to insert
     * @param missingNumIndex the index of the spot we are trying to fill (0-indexed)
     * @param potentialValList the list of potential values that can go in the index
     * @return
     */
    public ArrayList<Integer> getNumConflicts(int colNum, int potentialVal, int missingNumIndex, ArrayList<Integer> potentialValList) {
        Column col = this.getColumn(colNum);
        // check the conflicting row and each of the associated matrices to see if we can remove some numbers from missingNums of col
        Row conflictingRow = this.getRow(missingNumIndex+1);
        SubMatrix conflictingMatrix = getConflictingMatrix(col, conflictingRow);

        // remove potential val from the list
        if (conflictingMatrix.contains(potentialVal) || conflictingRow.contains(potentialVal)) {
            potentialValList.remove(new Integer(potentialVal));
        }
        return potentialValList;
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
        System.out.println("####### STARTING BOARD ######");
        printBoardState(normalTestBoard);
        System.out.println("#############################");
        BoardSolver solver = new BoardSolver();
        solver.solve(normalTestBoard);
        //getConflictingMatNumber(4, 9);
    }
}