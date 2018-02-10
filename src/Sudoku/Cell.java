package Sudoku;

public class Cell {
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