package Sudoku;
import java.util.ArrayList;
import java.util.Arrays;

public class Row {
    //row num is 1-indexed
    int rowNum;
    int[] values;
    int numSolved;
    ArrayList<Integer> missingNums = new ArrayList<>();
    ArrayList<Integer> missingNumsIndices = new ArrayList<>();
    boolean[] found = new boolean[9];
    SubMatrix[] matrices = new SubMatrix[3];

    public Row(Board board, int num) {
        this.rowNum = num;
        try {
            this.values = board.numbersOnBoard[num];
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid row number. Rows are 0-indexed");
            this.values = null;
        }
        if (this.values != null) {
            for (int i = 0; i < this.values.length; i++) {
                int val = this.values[i];
                if (val != 0)  {
                    this.numSolved++;
                    found[val-1] = true;
                } else {
                    missingNumsIndices.add(i);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!found[i]) missingNums.add(i+1);
        }
        linkAssociateMatricesToRow(board);
    }

    public Row(int[] vals, int num) {
        this.values = vals;
        this.rowNum = num;
        for (int val : this.values) {
            if (val != 0)  {
                this.numSolved++;
                found[val-1] = true;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!found[i]) missingNums.add(i+1);
        }
    }

    public String toString() {
        if (this.values != null) {
            return Arrays.toString(this.values);
        }
        return null;
    }

    public boolean contains(int num) {
        for (int x : this.values) {
            if (x == num) return true;
        }
        return false;
    }

    // this is 1-indexed
    public int getConflictingMatNumber(int rowNum, int index) {
        if (rowNum <= 3) {
            // Matrices 1-3
            if (index <= 3) {
                return 1;
            } else if (index > 3 && index <= 6) {
                return 2;
            } else {
                return 3;
            }
        } else if (rowNum > 3 && rowNum <= 6) {
            // Matrices 4-6
            if (index <= 3) {
                return 4;
            } else if (index > 3 && index <= 6) {
                return 5;
            } else {
                return 6;
            }
        } else {
            // Matrices 7-9
            if (index < 3) {
                return 7;
            } else if (index > 3 && index <= 6) {
                return 8;
            } else {
                return 9;
            }
        }
    }

    public void linkAssociateMatricesToRow(Board board) {
        // matrices 1,4,7
        if (this.rowNum == 1 || this.rowNum == 2 || this.rowNum == 3) {
            matrices[0] = board.getSubMatrix(0);
            matrices[1] = board.getSubMatrix(3);
            matrices[2] = board.getSubMatrix(6);
        } else if (this.rowNum == 4 || this.rowNum == 5 || this.rowNum == 6) {
            // matrices 2,5,8
            matrices[0] = board.getSubMatrix(1);
            matrices[1] = board.getSubMatrix(4);
            matrices[2] = board.getSubMatrix(7);
        } else {
            matrices[0] = board.getSubMatrix(2);
            matrices[1] = board.getSubMatrix(5);
            matrices[2] = board.getSubMatrix(8);
        }
    }
}