package Sudoku;
import java.util.ArrayList;
import java.util.Arrays;

public class Row {
    int rowNum;
    int[] values;
    int numSolved;
    ArrayList<Integer> missingNums = new ArrayList<>();
    boolean[] found = new boolean[9];
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

    public void printRow() {
        if (this.values != null) {
            System.out.println(Arrays.toString(this.values));
        }
    }
}