package Sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader {
    String csvFile = "/Users/JohnAng/Documents/Sudoku Solver/src/Sudoku/sudoku.csv";
    BufferedReader br = null;
    String line = "";
    String csvSplitBy = ",";

    int count = 0;

    StringToIntArrayConverter converter = new StringToIntArrayConverter();

    public ArrayList<String> readPuzzles(int puzzleLimit) {
        ArrayList<String> puzzleArr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();
            count++;
            while ((line = br.readLine()) != null && count != puzzleLimit+1) {
                String[] input = line.split(csvSplitBy);
                String puzzle = input[0];
                puzzleArr.add(puzzle);
                count++;

            }
            return puzzleArr;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> readSol(int puzzleLimit) {
        ArrayList<String> solArr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            line = br.readLine();
            count++;
            while ((line = br.readLine()) != null && count != puzzleLimit+1) {
                String[] input = line.split(csvSplitBy);
                String sol = input[1];
                solArr.add(sol);
                count++;

            }
            return solArr;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        reader.readPuzzles(5);
    }
}
