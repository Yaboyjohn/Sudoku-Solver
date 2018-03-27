package Sudoku;
import java.lang.reflect.Array;
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
        return colArr[colNum-1];

    }

    public SubMatrix getSubMatrix(int num) {
        return matArr[num-1];
    }

    /**
     *
     * @param colNum 1-indexed
     * @param col update column
     * @param rowNum 0-indexed
     * @param newNum
     */
    public void updateColumn(int colNum, Column col, int rowNum, int newNum) {
        //update conflicting row
        Row row = this.rowArr[rowNum];
        row.values[colNum-1] = newNum;
        Arrays.fill(row.found, 0, row.found.length, false);
        row.numSolved = 0;
        row.missingNumsIndices.clear();
        row.missingNums.clear();
        for (int i = 0; i < row.values.length; i++) {
            int val = row.values[i];
            if (val != 0)  {
                row.numSolved++;
                row.found[val-1] = true;
            } else {
                row.missingNumsIndices.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!row.found[i]) row.missingNums.add(i+1);
        }

        //update the conflicting matrix
        SubMatrix mat = getConflictingMatrix(col, row);
        mat.update(rowNum, colNum-1, newNum);
        this.matArr[mat.matrixNum-1] = mat;

        //update the column itself
        this.colArr[colNum-1] = col;
        Arrays.fill(col.found, 0, col.found.length, false);
        col.numSolved = 0;
        col.missingNumsIndices.clear();
        col.missingNums.clear();
        for (int i = 0; i < col.values.length; i++) {
            int val = col.values[i];
            if (val != 0)  {
                col.numSolved++;
                col.found[val-1] = true;
            } else {
                col.missingNumsIndices.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!col.found[i]) col.missingNums.add(i+1);
        }
    }

    public void updateRow(int rowNum, Row row, int colNum, int newNum) {
        // we replace row at index with this new updated row, update its metadata
        this.rowArr[rowNum-1] = row;
        Arrays.fill(row.found, 0, row.found.length, false);
        row.numSolved = 0;
        row.missingNumsIndices.clear();
        row.missingNums.clear();
        for (int i = 0; i < row.values.length; i++) {
            int val = row.values[i];
            if (val != 0)  {
                row.numSolved++;
                row.found[val-1] = true;
            } else {
                row.missingNumsIndices.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!row.found[i]) row.missingNums.add(i+1);
        }

        // update col (we modify in place, no need to replace)
        Column col = this.colArr[colNum];
        col.values[rowNum-1] = newNum;
        col.numSolved++;
        Arrays.fill(col.found, 0, col.found.length, false);
        col.numSolved = 0;
        col.missingNumsIndices.clear();
        col.missingNums.clear();
        for (int i = 0; i < col.values.length; i++) {
            int val = col.values[i];
            if (val != 0)  {
                col.numSolved++;
                col.found[val-1] = true;
            } else {
                col.missingNumsIndices.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!col.found[i]) col.missingNums.add(i+1);
        }

        //update mat
        SubMatrix mat = getConflictingMatrix(col, row);
        mat.update(rowNum-1, colNum, newNum);
        this.matArr[mat.matrixNum-1] = mat;
    }

    /**
     *
     * @param matNum 1-indexed
     * @param mat update matrix
     * @param index indices stored in here are 0-indexed
     */
    public void updateMat(int matNum, SubMatrix mat, SubMatrix.indexStruct index, int newNum) {
        // update board with updated matrix
        // matrix metadata already updated before this call
        this.matArr[matNum-1] = mat;

        // update col in place
        Column col = this.colArr[convertColIndexToIndex(index.colIndex, matNum)];
        col.values[convertRowIndexToIndex(index.rowIndex, matNum)] = newNum;
        col.numSolved++;
        Arrays.fill(col.found, 0, col.found.length, false);
        col.numSolved = 0;
        col.missingNumsIndices.clear();
        col.missingNums.clear();
        for (int i = 0; i < col.values.length; i++) {
            int val = col.values[i];
            if (val != 0)  {
                col.numSolved++;
                col.found[val-1] = true;
            } else {
                col.missingNumsIndices.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!col.found[i]) col.missingNums.add(i+1);
        }

        //update row in place
        Row row = this.rowArr[convertRowIndexToIndex(index.rowIndex, matNum)];
        row.values[convertColIndexToIndex(index.colIndex, matNum)] = newNum;

        Arrays.fill(row.found, 0, row.found.length, false);
        row.numSolved = 0;
        row.missingNumsIndices.clear();
        row.missingNums.clear();
        for (int i = 0; i < row.values.length; i++) {
            int val = row.values[i];
            if (val != 0)  {
                row.numSolved++;
                row.found[val-1] = true;
            } else {
                row.missingNumsIndices.add(i);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!row.found[i]) row.missingNums.add(i+1);
        }
    }

    public int convertRowIndexToIndex(int rowIndex, int matNum) {
       if (matNum <= 3) return rowIndex;
       else if (matNum <= 6) return rowIndex + 3;
       else return rowIndex + 6;
    }

    public int convertColIndexToIndex(int colIndex, int matNum) {
        if (matNum == 1 || matNum == 4 || matNum == 7) return colIndex;
        else if (matNum == 2 || matNum == 5 || matNum == 8) return colIndex + 3;
        else return colIndex + 6;
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

    public ArrayList<SubMatrix.indexStruct> cloneMat(ArrayList<SubMatrix.indexStruct> list) {
        ArrayList<SubMatrix.indexStruct> copy = new ArrayList<>();
        for (SubMatrix.indexStruct x : list) copy.add(x);
        return copy;
    }

    public SubMatrix getConflictingMatrix(Column col, Row row) {
        int colNum = col.colNum;
        int rowNum = row.rowNum;
        if (colNum <= 3) {
            if (rowNum <= 3) return getSubMatrix(1);
            else if (rowNum <= 6) return getSubMatrix(4);
            else return getSubMatrix(7);
        } else if (colNum <= 6) {
            if (rowNum <= 3) return getSubMatrix(2);
            else if (rowNum <= 6) return getSubMatrix(5);
            else return getSubMatrix(8);
        } else {
            if (rowNum <= 3) return getSubMatrix(3);
            else if (rowNum <= 6) return getSubMatrix(6);
            else return getSubMatrix(9);
        }
    }

    /**
     *
     * @param colNum the number of the column we are testing
     * @param potentialVal the number we wish to insert
     * @param missingNumIndex the index of the spot we are trying to fill (0-indexed)
     * @param potentialValList the list of potential values that can go in the index
     * @return
     */
    public ArrayList<Integer> getNumColConflicts(int colNum, int potentialVal, int missingNumIndex, ArrayList<Integer> potentialValList) {
        Column col = this.getColumn(colNum);
        // check the conflicting row and each of the associated matrices to see if we can remove some numbers from missingNums of col
        Row conflictingRow = this.getRow(missingNumIndex+1);
        SubMatrix conflictingMatrix = getConflictingMatrix(col, conflictingRow);
        // if we arent able to narrow down the potentialValList to 1, return the original potValList with all possibilities
        ArrayList<Integer> copy = this.clone(potentialValList);

        // remove potential val from the list
        if (conflictingMatrix.contains(potentialVal) || conflictingRow.contains(potentialVal) || col.contains(potentialVal)) {
            potentialValList.remove(new Integer(potentialVal));
            if (potentialValList.size() == 1) {
                return potentialValList;
            }
        }
        return copy;
    }

    public ArrayList<Integer> getNumRowConflicts(int rowNum, int potentialVal, int missingNumIndex, ArrayList<Integer> potentialValList) {
        Row row = this.getRow(rowNum);
        // check the conflicting row and each of the associated matrices to see if we can remove some numbers from missingNums of col
        Column conflictingCol = this.getColumn(missingNumIndex+1);
        SubMatrix conflictingMatrix = getConflictingMatrix(conflictingCol, row);
        // if we arent able to narrow down the potentialValList to 1, return the original potValList with all possibilities
        ArrayList<Integer> copy = this.clone(potentialValList);

        // remove potential val from the list
        if (conflictingMatrix.contains(potentialVal) || conflictingCol.contains(potentialVal) || row.contains(potentialVal)) {
            potentialValList.remove(new Integer(potentialVal));
            if (potentialValList.size() == 1) {
                return potentialValList;
            }
        }
        return copy;
    }

    /**
     * The point of this function is to try to eliminate candidates in the potentialValList by comparing against conflicting structs in same index
     * @param matNum
     * @param potentialVal
     * @param index rowIndex and colIndex are both 0-indexed
     * @param potentialValList
     * @return
     */
    public ArrayList<Integer> getNumMatConflicts(int matNum, int potentialVal, SubMatrix.indexStruct index, ArrayList<Integer> potentialValList) {
        // Col conflictingCol = this.getColumn(index.colIndex+1);
        Column conflictingCol = this.getColumn(convertColIndexToIndex(index.colIndex, matNum)+1);
        //Row conflictingRow = this.getRow(index.rowIndex+1);
        Row conflictingRow = this.getRow(convertRowIndexToIndex(index.rowIndex, matNum) +1);
        SubMatrix mat = this.getSubMatrix(matNum);
        // if we arent able to narrow down the potentialValList to 1, return copy of the original potValList with all possibilities
        ArrayList<Integer> copy = this.clone(potentialValList);

        // remove potential val from the list
        if (conflictingCol.contains(potentialVal) || conflictingRow.contains(potentialVal) || mat.contains(potentialVal)) {
            potentialValList.remove(new Integer(potentialVal));
            if (potentialValList.size() == 1) {
                return potentialValList;
            }
        }
        return copy;
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
        int[][] test1 = new int[][]{
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

        int[][] test2 = new int[][]{
                {0, 0, 0, 0, 1, 0, 0, 4, 5},
                {0, 0, 4, 9, 0, 0, 6, 0, 0},
                {0, 0, 3, 0, 0, 7, 0, 0, 0},
                {5, 1, 0, 0, 3, 0, 0, 9, 0},
                {0, 0, 0, 8, 0, 1, 0, 0, 0},
                {0, 9, 0, 0, 7, 0, 0, 8, 3},
                {0, 0, 0, 7, 0, 0, 5, 0, 0},
                {0, 0, 5, 0, 0, 8, 7, 0, 0},
                {2, 7, 0, 0, 6, 0, 0, 0, 0}
        };

        Board normalTestBoard = new Board(test2);
        System.out.println("####### STARTING BOARD ######");
        printBoardState(normalTestBoard);
        System.out.println("#############################");
        BoardSolver solver = new BoardSolver();
        solver.solve(normalTestBoard);
        //getConflictingMatNumber(4, 9);
    }
}