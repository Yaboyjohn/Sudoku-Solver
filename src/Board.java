import java.util.*;
public class Board {
    public static int[][] numbersOnBoard;

    public Board(int[][] startingNumbers) {
        numbersOnBoard = startingNumbers;
    }

    // Printing Methods
    public static void printTemplate() {
        int[][] template = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                template[i][j] = 0;
            }
        }
        for (int[] x : template) {
            System.out.println(Arrays.toString(x));
        }
    }

    public static void printBoardState(Board board) {
        int vertCounter = 0;
        int horizCounter = 0;
        for (int[] x : board.numbersOnBoard) {
            for (int y : x) {
                if (vertCounter == 3) {
                    System.out.print("| ");
                }
                if (vertCounter == 6) {
                    System.out.print("| ");
                    vertCounter = -3;
                }
                if (y == 0) {
                    System.out.print("* ");
                } else {
                    System.out.print(y + " ");
                }
                vertCounter++;
            }
            horizCounter++;
            if (horizCounter == 3) {
                System.out.println();
                System.out.println("---------------------");
            } else if (horizCounter == 6) {
                System.out.println();
                System.out.println("---------------------");
            } else {
                System.out.println();
            }
        }
    }

    public static void printBoardState(int[][] board) {
        int vertCounter = 0;
        int horizCounter = 0;
        for (int[] x : board) {
            for (int y : x) {
                if (vertCounter == 3) {
                    System.out.print("| ");
                }
                if (vertCounter == 6) {
                    System.out.print("| ");
                    vertCounter = -3;
                }
                if (y == 0) {
                    System.out.print("* ");
                } else {
                    System.out.print(y + " ");
                }
                vertCounter++;
            }
            horizCounter++;
            if (horizCounter == 3) {
                System.out.println();
                System.out.println("---------------------");
            } else if (horizCounter == 6) {
                System.out.println();
                System.out.println("---------------------");
            } else {
                System.out.println();
            }
        }
    }

    public static boolean validateSolution() {
        int[][] numbersMatrix = numbersOnBoard;

        // Rows Check
        for (int i = 0; i < numbersOnBoard.length; i++) {
            int[] row = numbersOnBoard[i];
            if (!validateIndividualRow(row, i)) {
                return false;
            }
        }
        // Columns Check
        for (int j = 0; j < numbersOnBoard.length; j++) {
            if (!validateIndividualColumns(numbersOnBoard, j)) {
                return false;
            }
        }

        // Matrices Check
        validateSubMatrices(numbersOnBoard);
        System.out.println("Good job! This is a valid sudoku solution");
        return true;
    }

    public static boolean validateIndividualRow(int[] row, int rowNumber) {
        int[] rowChecker = new int[9];
        for (int i = 0; i < row.length; i++) {
            int num = row[i];
            try {
                rowChecker[num-1]++;
            } catch (IndexOutOfBoundsException ex) {
                String errorMessage = ex.toString();
                String incorrectNumberString = errorMessage.substring(errorMessage.indexOf(':') + 2, errorMessage.length());
                int incorrectNumber = Integer.parseInt(incorrectNumberString) + 1;
                System.out.println("Invalid Solution: Number " + incorrectNumber + " at row " + (rowNumber + 1) + " column " + (i+1) + " is not valid");
                return false;
            }
        }
        for (int j = 0; j < rowChecker.length; j++) {
            if (rowChecker[j] != 1) {
                System.out.println("Row " + (rowNumber+1) + " is invalid: " + Arrays.toString(row));
                return false;
            }
        }
        return true;
    }

    public static boolean validateIndividualColumns(int[][] board, int colNumber) {
        // Col Array is used to print out faulty columns
        int[] colArray = new int[9];

        // Colchecker is to actually check that the column is valid
        int[] colChecker = new int[9];

        // This for loop iterates through each row and grabs the number at the specified column number that was passed in
        for (int i = 0; i < board.length; i++) {
            int[] row = board[i];
            int num = row[colNumber];
            try {
                colChecker[num-1]++;
                colArray[i] = num;
            } catch (IndexOutOfBoundsException ex) {
                String errorMessage = ex.toString();
                String incorrectNumberString = errorMessage.substring(errorMessage.indexOf(':') + 2, errorMessage.length());
                int incorrectNumber = Integer.parseInt(incorrectNumberString) + 1;
                System.out.println("Invalid Solution: Number " + incorrectNumber + " at row " + (i + 1) + " column " + (colNumber+1) + " is not valid");
                return false;
            }
        }
        for (int j = 0; j < colChecker.length; j++) {
            if (colChecker[j] != 1) {
                System.out.println("Column " + (colNumber+1) + " is invalid: " + Arrays.toString(colArray));
                return false;
            }
        }
        return true;
    }

    public static boolean validateSubMatrices(int[][] board) {
        // 1 2 3
        // 4 5 6
        // 7 8 9
        int[] matrixChecker = new int[9];

        // Matrix 1
        int[][] matrix1 = new int[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int num = board[row][col];
                matrix1[row][col] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 1) {
                System.out.println("Matrix 1 is invalid: ");
                printMatrix(matrix1);
                return false;
            }
        }

        // Matrix 2
        int[][] matrix2 = new int[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < 6; col++) {
                int num = board[row][col];
                matrix2[row][col-3] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 2) {
                System.out.println("Matrix 2 is invalid: ");
                printMatrix(matrix2);
                return false;
            }
        }

        // Matrix 3
        int[][] matrix3 = new int[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 6; col < 9; col++) {
                int num = board[row][col];
                matrix3[row][col-6] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 3) {
                System.out.println("Matrix 3 is invalid: ");
                printMatrix(matrix3);
                return false;
            }
        }

        // Matrix 4
        int[][] matrix4 = new int[3][3];
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 3; col++) {
                int num = board[row][col];
                matrix4[row-3][col] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 4) {
                System.out.println("Matrix 4 is invalid: ");
                printMatrix(matrix4);
                return false;
            }
        }

        // Matrix 5
        int[][] matrix5 = new int[3][3];
        for (int row = 3; row < 6; row++) {
            for (int col = 3; col < 6; col++) {
                int num = board[row][col];
                matrix5[row-3][col-3] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 5) {
                System.out.println("Matrix 5 is invalid: ");
                printMatrix(matrix5);
                return false;
            }
        }

        // Matrix 6
        int[][] matrix6 = new int[3][3];
        for (int row = 3; row < 6; row++) {
            for (int col = 6; col < 9; col++) {
                int num = board[row][col];
                matrix6[row-3][col-6] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 6) {
                System.out.println("Matrix 6 is invalid: ");
                printMatrix(matrix6);
                return false;
            }
        }

        // Matrix 7
        int[][] matrix7 = new int[3][3];
        for (int row = 6; row < 9; row++) {
            for (int col = 0; col < 3; col++) {
                int num = board[row][col];
                matrix7[row-6][col-0] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 7) {
                System.out.println("Matrix 7 is invalid: ");
                printMatrix(matrix7);
                return false;
            }
        }

        // Matrix 8
        int[][] matrix8 = new int[3][3];
        for (int row = 6; row < 9; row++) {
            for (int col = 3; col < 6; col++) {
                int num = board[row][col];
                matrix8[row-6][col-3] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 8) {
                System.out.println("Matrix 8 is invalid: ");
                printMatrix(matrix8);
                return false;
            }
        }

        // Matrix 8
        int[][] matrix9 = new int[3][3];
        for (int row = 6; row < 9; row++) {
            for (int col = 6; col < 9; col++) {
                int num = board[row][col];
                matrix9[row-6][col-6] = num;
                matrixChecker[num-1]++;
            }
        }
        for (int i = 0; i < matrixChecker.length; i++) {
            if (matrixChecker[i] != 9) {
                System.out.println("Matrix 9 is invalid: ");
                printMatrix(matrix9);
                return false;
            }
        }
        return true;
    }

    // HELPER METHODS
    public static void printMatrix(int[][]matrix) {
        for (int[] x : matrix) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
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
        normalTestBoard.validateSolution();


    }
}
