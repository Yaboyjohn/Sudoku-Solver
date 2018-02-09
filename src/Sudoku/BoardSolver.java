package Sudoku;
import java.util.*;
import static Sudoku.BoardStruct.*;

public class BoardSolver {

    // Idea: greedy, perform checking on row/col/matrix with most already filled in numbers
    // helper function for checking which part of board has most filled in?
    // if we fill one in, do we continue with the same part and try to fill the rest in? or do we scan again

    int minNumConflicts;
    ArrayList<BoardStruct>[] bucket = new ArrayList[9];
    public void solve(Board board) {

    }

    public void updateBuckets(ArrayList<BoardStruct>[] bucket, Board board) {
        Row[] rows = getAllRows(board);
        Column[] cols = getAllColumns(board);
        SubMatrix[] mats = getAllSubMatrices(board);
        for (Row r : rows) {
            int index = r.numSolved;
            if (bucket[index] == null) {
                bucket[index] = new ArrayList<>();
            }
            bucket[index].add(r);
        }
    }
}
