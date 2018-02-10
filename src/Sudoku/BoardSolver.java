package Sudoku;
import java.util.*;
import static Sudoku.BoardStruct.*;
import static Sudoku.BoardUtils.*;

public class BoardSolver {

    // Idea: greedy, perform checking on row/col/matrix with most already filled in numbers
    // helper function for checking which part of board has most filled in?
    // if we fill one in, do we continue with the same part and try to fill the rest in? or do we scan again

    public static int fewestRemainingIndex;
    public static ArrayList<BoardStruct>[] bucket = new ArrayList[9];
    public static void solve(Board board) {
        instantiateBuckets(bucket, board);

    }

    public static void instantiateBuckets(ArrayList<BoardStruct>[] bucket, Board board) {
        Row[] rows = board.rowArr;
        Column[] cols = board.colArr;
        SubMatrix[] mats = board.matArr;
        for (Row r : rows) {
            int index = r.numSolved;
            if (bucket[index] == null) {
                bucket[index] = new ArrayList<>();
            }
            bucket[index].add(new BoardStruct(r));
        }
        for (Column c : cols) {
            int index = c.numSolved;
            if (bucket[index] == null) {
                bucket[index] = new ArrayList<>();
            }
            bucket[index].add(new BoardStruct(c));
        }
        for (SubMatrix s : mats) {
            int index = s.numSolved;
            if (bucket[index] == null) {
                bucket[index] = new ArrayList<>();
            }
            bucket[index].add(new BoardStruct(s));
        }
        for (int i = 8; i >= 0; i--) {
            if (bucket[i] != null)  {
                fewestRemainingIndex = i;
                break;
            }
        }
        printBucket(bucket);
    }
}
