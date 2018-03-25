package Sudoku;

import java.util.*;

import static Sudoku.Board.*;
import static Sudoku.BoardUtils.*;
import static Sudoku.SubMatrix.*;


public class BoardTester {
    public static boolean validateSolution(Board board) {
        // Rows Check
        for (int i = 0; i < board.numbersOnBoard.length; i++) {
            int[] row = board.numbersOnBoard[i];
            if (!validateIndividualRow(row, i)) {
                return false;
            }
        }
        // Columns Check
        for (int j = 0; j < board.numbersOnBoard.length; j++) {
            if (!validateIndividualColumns(board.numbersOnBoard, j)) {
                return false;
            }
        }

        // Matrices Check
        validateSubMatrices(board.numbersOnBoard);
        System.out.println("Good job! This is a valid sudoku solution");
        return true;
    }

    public static int numPossiblSpotsInMatrix(Board board, int matNumber) {
        int[][] mat = getValues(board, matNumber);
        int numSpots = 0;
        System.out.println("matrix: ");
        BoardUtils.printMatrix(mat);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int num = mat[row][col];
                if (num == 0) {
                    numSpots++;
                    continue;
                }
                else if (num < 0 || num > 9) {
                    System.out.println("Number out of bounds");
                    return -1;
                }
            }
        }
        return numSpots;
    }

    public static int numPossibleSpotsInCol(Board board, int colNum) {
        int numSpots = 0;
        for (int val : board.getColumn(colNum).values) {
            if (val == 0) {
                numSpots++;
                continue;
            }
            else if (val < 0 || val > 9) {
                System.out.println("Number out of bounds");
                return -1;
            }
        }
        return numSpots;
    }

    public static int numPossibleSpotsInRow(Board board,  int rowNum) {
        int numSpots = 0;
        for (int val : board.getRow(rowNum).values) {
            if (val == 0) {
                numSpots++;
                continue;
            } else if (val < 0 || val > 9) {
                System.out.println("Number out of bounds");
                return -1;
            }
        }
        return numSpots;
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

    /** This is 0-indexed **/
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

        // Matrix 9
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
}
