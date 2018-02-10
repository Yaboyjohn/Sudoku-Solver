package Sudoku;
import java.util.Arrays;

public class Row {
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