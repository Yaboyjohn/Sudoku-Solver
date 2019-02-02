package Sudoku;

public class StringToIntArrayConverter {
    public int[][] convert(String str) {
        int xCoord = 0;
        int yCoord = 0;
        int[][] numbersArray = new int[9][9];
        for (int i = 0; i < str.length(); i++) {
            if (yCoord == 9) {
                xCoord++;
                yCoord = 0;
            }
            if (xCoord == 9) {
                break;
            }
            int number = Character.getNumericValue(str.charAt(i));
            numbersArray[xCoord][yCoord] = number;
            yCoord++;
        }
        return numbersArray;
    }
}
