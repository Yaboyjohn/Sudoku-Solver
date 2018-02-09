package Sudoku;

import java.util.Arrays;

public class BoardStruct {
    public static Row[] getAllRows(Board board) {
        Row[] rowArr = new Row[9];
        for (int i = 1; i < 10; i++) {
            rowArr[i-1] = new Row(board, i);
        }
        return rowArr;
    }

    public static Column[] getAllColumns(Board board) {
        Column[] colArr = new Column[9];
        for (int i = 0; i < 9; i++) {
            char c = (char) (i+65);
            colArr[i] = new Column(board, c);
        }
        return colArr;
    }

    public static SubMatrix[] getAllSubMatrices(Board board) {
        SubMatrix[] matArr = new SubMatrix[9];
        for (int i = 1; i < 10; i++) {
            matArr[i-1] = new SubMatrix(board, i);
        }
        return matArr;
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
        int numSolved;
        public Row(Board board, int num) {
            this.rowNum = num;
            try {
                this.values = board.numbersOnBoard[num - 1];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid row number. Rows are 1-indexed");
                this.values = null;
            }
            if (this.values != null) {
                for (int val : this.values) {
                    if (val != 0) this.numSolved++;
                }
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
        int numSolved;
        public Column(Board board, char c) {
            this.colNum = c - 'A';
            this.colName = c;
            this.values = getValues(board, colNum);
            if (this.values != null) {
                for (int val : this.values) {
                    if (val != 0) this.numSolved++;
                }
            }
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
}
