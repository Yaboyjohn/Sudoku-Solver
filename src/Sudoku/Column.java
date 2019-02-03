package Sudoku;
import java.util.ArrayList;
import java.util.Arrays;

public class Column {
    // colNum is 1-indexed
    int colNum;
    char colName;
    int[] values;
    int numSolved;
    ArrayList<Integer> missingNums = new ArrayList<>();
    ArrayList<Integer> missingNumsIndices = new ArrayList<>();
    boolean[] found = new boolean[9];
    SubMatrix[] matrices = new SubMatrix[3];

    public Column(Board board, char c) {
        // colNum is 0-indexed
        this.colNum = c - 'A';
        this.colName = c;
        this.values = getValues(board, colNum);
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
        linkAssociateMatricesToColumn(board);
    }

    // num is 0-indexed??
    public Column(int[] vals, int num) {
        this.colNum = num;
        this.colName = (char) (num+64);
        this.values = vals;
        if (this.values != null) {
            for (int val : this.values) {
                if (val != 0)  {
                    this.numSolved++;
                    found[val-1] = true;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!found[i]) {
                missingNums.add(i+1);
            }
        }
        for (int i = 0; i < 9; i++) {
            if (this.values[i] == 0) {
                missingNumsIndices.add(i);
            }
        }
    }

    /** colNum here is 0-indexed **/
    public int[] getValues(Board board, int colNum) {
        int[] arr = new int[9];
        for (int i = 0; i < 9; i++) {
            try {
                arr[i] = board.numbersOnBoard[i][colNum];
            } catch (IndexOutOfBoundsException err) {
                System.out.println("Invalid column name. Columns range from A - I or from 1-9");
                return null;
            }
        }
        return arr;
    }

    public String toString() {
        if (this.values != null) {
            return Arrays.toString(this.values);
        }
        return null;
    }

    public int getConflictingMatNumber(int colNum, int index) {
        if (colNum <= 3) {
            // Matrices 1,4,7
            if (index <= 3) {
                return 1;
            } else if (index > 3 && index <= 6) {
                return 4;
            } else {
                return 7;
            }
        } else if (colNum > 3 && colNum <= 6) {
            // Matrices 2,5,8
            if (index <= 3) {
                return 2;
            } else if (index > 3 && index <= 6) {
                return 5;
            } else {
                return 8;
            }
        } else {
            // Matrices 3,6,9
            if (index < 3) {
                return 3;
            } else if (index > 3 && index <= 6) {
                return 6;
            } else {
                return 9;
            }
        }
    }

    public boolean contains(int num) {
        for (int x : this.values) {
            if (x == num) return true;
        }
        return false;
    }

    public void linkAssociateMatricesToColumn(Board board) {
        // matrices 1,4,7
        if (this.colNum == 0 || this.colNum == 1 || this.colNum == 2) {
            matrices[0] = board.getSubMatrix(0);
            matrices[1] = board.getSubMatrix(3);
            matrices[2] = board.getSubMatrix(6);
        } else if (this.colNum == 3 || this.colNum == 4 || this.colNum == 5) {
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
