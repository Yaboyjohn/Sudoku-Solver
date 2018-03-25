package Sudoku;
import java.util.*;
import static Sudoku.BoardStruct.*;
import static Sudoku.BoardUtils.*;
import static Sudoku.Row.*;
import static Sudoku.BoardTester.*;
import static Sudoku.Board.*;

public class BoardSolver {

    // Idea: greedy, perform checking on row/col/matrix with most already filled in numbers
    // helper function for checking which part of board has most filled in?
    // if we fill one in, do we continue with the same part and try to fill the rest in? or do we scan again

    private int fewestRemainingIndex;
    private ArrayList<BoardStruct>[] buckets = new ArrayList[9];
    private Board currBoard;
    public void solve(Board board) {
        // each index of the bucket corresponds to number of solved entries
        // there are arraylists holding the structs with that number of solved entries
        // we grab the arraylist with the max number of solved entries (fewestRemainingIndex)
        // take the first boardstruct in the arraylist at fewestRemainingIndex
        // for each 0 slot, we check all conflicting neighboring structs to see if we can find a guaranteed correct location
        currBoard = board;
        instantiateBuckets(buckets, board);
        ArrayList<BoardStruct> minBucket = buckets[fewestRemainingIndex];
        BoardStruct minStruct = minBucket.get(0);
        if (minStruct.type == TYPE.ROW) {
            System.out.println("HERE");
            Row currRow = minStruct.row;
            for (int i = 0; i < 9; i++) {
                // this is an empty spot
                if (currRow.values[i] == 0) {
                    // check how good inserting an elem is here
                    for (int potentialVal : currRow.missingNums) {
                        Board testBoard = currBoard.clone();
                        if (testBoard.getNumConflicts(currRow, potentialVal, i) == 0) {
                            // set currBoard to this modified board with potentialVal
                            currBoard = testBoard;
                            // update buckets as well
                            // recursively call solve with this new board?
                            break;
                        }
                    }
                }
            }

        } else if (minStruct.type == TYPE.COLUMN) {
            Column currCol = minStruct.col;
            ArrayList<Integer> missingNumsList = board.clone(currCol.missingNums);
            for (int i : currCol.missingNumsIndices) {
                for (int missingNum : missingNumsList) {
                    Board testBoard = currBoard.clone();
                    System.out.println("before: " + currCol.missingNums.toString());
                    ArrayList<Integer> potentialValsList = testBoard.getNumConflicts(currCol.colNum, missingNum, i, currCol.missingNums);
                    System.out.println("after: " + currCol.missingNums.toString());

                    // this means there is only 1 spot this val to go in
                    if (potentialValsList.size() == 1) {
                        int[] updatedColVals = testBoard.getColumn(currCol.colNum).values;
                        updatedColVals[i] = potentialValsList.get(0);
                        Column newCol = new Column(updatedColVals, currCol.colNum);
                        System.out.println("before:");
                        BoardUtils.printBoardState(currBoard);
                        testBoard.setColumn(currCol.colNum, newCol, i, potentialValsList.get(0));
                        currBoard = testBoard;
                        System.out.println("after");
                        BoardUtils.printBoardState(currBoard);
                        break;
                    }
                }
            }
        } else {

        }

    }

    public void instantiateBuckets(ArrayList<BoardStruct>[] bucket, Board board) {
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
//        Row r2 = rows[0];
//        System.out.println("before");
//        BoardUtils.printBoardState(board);
//        getNumConflicts(board, r2, 3, 2);
//        System.out.println("after");
//        BoardUtils.printBoardState(board);
    }




}
