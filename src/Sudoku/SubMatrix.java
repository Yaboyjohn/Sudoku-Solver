package Sudoku;

public class SubMatrix {
    int matrixNum;
    int[][] values;
    int numSolved;
    public SubMatrix(Board board, int num) {
        this.matrixNum = num;
        this.values = getValues(board, this.matrixNum);
        if (this.values != null) {
            for (int[] row : this.values) {
                for (int val : row) {
                    if (val != 0) this.numSolved++;
                }
            }
        }
    }

    public void printSubMatrix() {
        if (this.values != null) {
            BoardUtils.printMatrix(this.values);
        }
    }

    public int[][] getValues(Board board, int loc) {
        if (loc == 1) {
            this.values = new int[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row][col] = num;
                }
            }
            return this.values;
        } else if (loc == 2) {
            this.values = new int[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 3; col < 6; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row][col-3] = num;
                }
            }
            return this.values;
        } else if (loc == 3) {
            this.values = new int[3][3];
            for (int row = 0; row < 3; row++) {
                for (int col = 6; col < 9; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row][col-6] = num;
                }
            }
            return this.values;
        } else if (loc == 4) {
            this.values = new int[3][3];
            for (int row = 3; row < 6; row++) {
                for (int col = 0; col < 3; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row-3][col] = num;
                }
            }
            return this.values;
        } else if (loc == 5) {
            this.values = new int[3][3];
            for (int row = 3; row < 6; row++) {
                for (int col = 3; col < 6; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row-3][col-3] = num;
                }
            }
            return this.values;
        } else if (loc == 6) {
            this.values = new int[3][3];
            for (int row = 3; row < 6; row++) {
                for (int col = 6; col < 9; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row-3][col-6] = num;
                }
            }
            return this.values;
        } else if (loc == 7) {
            this.values = new int[3][3];
            for (int row = 6; row < 9; row++) {
                for (int col = 0; col < 3; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row-6][col] = num;
                }
            }
            return this.values;
        } else if (loc == 8) {
            this.values = new int[3][3];
            for (int row = 6; row < 9; row++) {
                for (int col = 3; col < 6; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row-6][col-3] = num;
                }
            }
            return this.values;
        } else if (loc == 9) {
            this.values = new int[3][3];
            for (int row = 6; row < 9; row++) {
                for (int col = 6; col < 9; col++) {
                    int num = board.numbersOnBoard[row][col];
                    this.values[row-6][col-6] = num;
                }
            }
            return this.values;
        } else {
            System.out.println("Invalid sub matrix number. Numbers must be between 1 and 9");
        }
        return null;
    }
}
