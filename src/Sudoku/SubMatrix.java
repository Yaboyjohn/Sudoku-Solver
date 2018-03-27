package Sudoku;

import java.util.ArrayList;
import java.util.Arrays;

public class SubMatrix {
    public class indexStruct {
        public indexStruct(int row, int col) {
            this.rowIndex = row;
            this.colIndex = col;
        }
        int rowIndex;
        int colIndex;
    }
    int matrixNum;
    int[][] values;
    int numSolved;
    ArrayList<indexStruct> missingNumsIndices = new ArrayList<>();
    ArrayList<Integer> missingNums = new ArrayList<>();
    private boolean[] found = new boolean[9];

    public SubMatrix(Board board, int num) {
        this.matrixNum = num;
        this.values = getValues(board, this.matrixNum);
        if (this.values != null) {
            for (int i = 0; i < this.values.length; i++) {
                for (int j = 0; j < this.values[0].length; j++) {
                    int val = values[i][j];
                    if (val != 0) {
                        this.numSolved++;
                        this.found[val-1] = true;
                    } else {
                        indexStruct missingIndex = new indexStruct(i, j);
                        this.missingNumsIndices.add(missingIndex);
                    }
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!found[i]) missingNums.add(i+1);
        }
    }

    /**
     *
     * @param rowNum 0-indexed
     * @param colNum 0-indexed
     * @param newNum
     */
    public void update(int rowNum, int colNum, int newNum) {
        this.values[rowNum % 3][colNum % 3] = newNum;
        this.numSolved = 0;
        this.missingNums.clear();
        this.missingNumsIndices.clear();
        Arrays.fill(this.found, 0, this.found.length, false);

        for (int i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values[0].length; j++) {
                int val = values[i][j];
                if (val != 0) {
                    this.numSolved++;
                    this.found[val-1] = true;
                } else {
                    indexStruct missingIndex = new indexStruct(i, j);
                    this.missingNumsIndices.add(missingIndex);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!found[i]) missingNums.add(i+1);
        }
    }


    public void printSubMatrix() {
        if (this.values != null) {
            BoardUtils.printMatrix(this.values);
        }
    }

    public boolean contains(int num) {
        for (int i = 0; i < this.values.length; i++) {
            for (int j = 0; j < this.values[0].length; j++) {
                if (this.values[i][j] == num) return true;
            }
        }
        return false;
    }

    public static int[][] getValues(Board board, int loc) {
        if (loc == 1) {
            int[][] vals = new int[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row][col] = num;
                }
            }
            return vals;
        } else if (loc == 2) {
            int[][]vals = new int[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 3; col < 6; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row][col-3] = num;
                }
            }
            return vals;
        } else if (loc == 3) {
            int[][] vals = new int[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 6; col < 9; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row][col-6] = num;
                }
            }
            return vals;
        } else if (loc == 4) {
            int[][] vals = new int[3][3];
            for (int row = 3; row < 6; row++) {
                for (int col = 0; col < 3; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row-3][col] = num;
                }
            }
            return vals;
        } else if (loc == 5) {
            int[][] vals = new int[3][3];
            for (int row = 3; row < 6; row++) {
                for (int col = 3; col < 6; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row-3][col-3] = num;
                }
            }
            return vals;
        } else if (loc == 6) {
            int[][] vals = new int[3][3];
            for (int row = 3; row < 6; row++) {
                for (int col = 6; col < 9; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row-3][col-6] = num;
                }
            }
            return vals;
        } else if (loc == 7) {
            int[][]vals = new int[3][3];
            for (int row = 6; row < 9; row++) {
                for (int col = 0; col < 3; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row-6][col] = num;
                }
            }
            return vals;
        } else if (loc == 8) {
            int[][]vals = new int[3][3];
            for (int row = 6; row < 9; row++) {
                for (int col = 3; col < 6; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row-6][col-3] = num;
                }
            }
            return vals;
        } else if (loc == 9) {
            int[][]vals = new int[3][3];
            for (int row = 6; row < 9; row++) {
                for (int col = 6; col < 9; col++) {
                    int num = board.numbersOnBoard[row][col];
                    vals[row-6][col-6] = num;
                }
            }
            return vals;
        } else {
            System.out.println("Invalid sub matrix number. Numbers must be between 1 and 9");
        }
        return null;
    }
}
