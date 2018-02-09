package Sudoku;
import java.util.*;
import static Sudoku.BoardTester.*;
import static Sudoku.BoardUtils.*;

public class Board {
    public static int[][] numbersOnBoard;

    public Board(int[][] startingNumbers) {
        numbersOnBoard = startingNumbers;
    }

    public static class Cell {
        char letter;
        int num;
        public Cell(char s, int n) {
            this.letter = s;
            this.num = n;
        }
        public int getCol() {
            return this.letter - 'A' + 1;
        }

        public int getRow() {
            return this.num;
        }
        public void name() {
            System.out.println(letter+num);
        }
    }

    public static class Row {
        int rowNum;
        int[] values;
        public Row(Board board, int num) {
            this.rowNum = num;
            try {
                this.values = board.numbersOnBoard[num-1];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid row number. Rows are 1-indexed");
                this.values = null;
            }
        }

        public void printRow() {
            if (this.values != null) {
                System.out.println(Arrays.toString(this.values));
            }
        }
    }

    public static class Column {
        int colNum;
        char colName;
        int[] values;
        public Column(Board board, char c) {
            this.colNum = c - 'A';
            this.colName = c;
            this.values = getValues(board, colNum);
        }

        public int[] getValues(Board board, int colNum) {
            int[] arr = new int[9];
            for (int i = 0; i < 9; i++) {
                try {
                    arr[i] = board.numbersOnBoard[i][colNum];
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("Invalid column name. Columns range from A - I");
                    return null;
                }
            }
            return arr;
        }

        public void printCol() {
            if (this.values != null) {
                System.out.println(Arrays.toString(this.values));
            }
        }
    }

    public static class SubMatrix {
        int matrixNum;
        int[][] values;
        public SubMatrix(Board board, int num) {
            this.matrixNum = num;
            this.values = getValues(board, this.matrixNum);
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
        Row r2 = new Row(normalTestBoard, 0);
        //r2.printRow();
        Column colB = new Column(normalTestBoard, 'J');
        //colB.printCol();
        SubMatrix s = new SubMatrix(normalTestBoard, 5);
        s.printSubMatrix();

        //validateSolution();
    }
}
