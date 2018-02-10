package Sudoku;
import java.util.ArrayList;
import java.util.Arrays;

public class Column {
    int colNum;
    char colName;
    int[] values;
    int numSolved;
    ArrayList<Integer> missingNums = new ArrayList<>();
    boolean[] found = new boolean[9];
    public Column(Board board, char c) {
        this.colNum = c - 'A' + 1;
        this.colName = c;
        this.values = getValues(board, colNum - 1);
        if (this.values != null) {
            for (int val : this.values) {
                if (val != 0)  {
                    this.numSolved++;
                    found[val-1] = true;
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!found[i]) missingNums.add(i+1);
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
