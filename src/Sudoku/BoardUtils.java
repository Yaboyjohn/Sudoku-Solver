package Sudoku;

import java.util.*;

public class BoardUtils {
    public static void printMatrix(int[][]matrix) {
        for (int[] x : matrix) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public static void printBucket(ArrayList<BoardStruct>[] bucket) {
        for (int i = 0; i < 9; i++) {
            if (bucket[i] != null) {
                ArrayList<BoardStruct> arr = bucket[i];
                for (BoardStruct bs : arr) {
                    System.out.println("BUCKET: " + i + " = " + (i+1) + " solved | " + bs.type + " " + bs.num + " " + bs.name);
                }
                System.out.println("Num structs in bucket: " + i + " | " +  arr.size());
            }

        }
    }

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
}
