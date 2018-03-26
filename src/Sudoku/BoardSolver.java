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
    // buckets index 0 = 1 solved .... index 8 = 9 solved
    private ArrayList<BoardStruct>[] buckets = new ArrayList[9];
    private Board currBoard;
    public void solve(Board board) {
        // each index of the bucket corresponds to number of solved entries
        // there are arraylists holding the structs with that number of solved entries
        // we grab the arraylist with the max number of solved entries (fewestRemainingIndex)
        // take the first boardstruct in the arraylist at fewestRemainingIndex
        // for each 0 slot, we check all conflicting neighboring structs to see if we can find a guaranteed correct location
        currBoard = board;
        int count = 0;
        while (count != 5) {
            instantiateBuckets(buckets, currBoard);
            count++;
            ArrayList<BoardStruct> minBucket = buckets[fewestRemainingIndex];
            BoardStruct minStruct = minBucket.get(0);
            System.out.println("MIN STRUCT: " + minStruct.type + " " + minStruct.num);
            if (minStruct.type == TYPE.ROW) {
                System.out.println("ROW TYPE");
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
                ArrayList<Integer> missingNumsIndicesList = board.clone(currCol.missingNumsIndices);
                //System.out.println(missingNumsList);
                //System.out.println(missingNumsIndicesList);
                for (int i : missingNumsIndicesList) {
                    System.out.println("index: " + i);
                    for (int missingNum : currCol.missingNums) {
                        Board testBoard = currBoard.clone();
                        //System.out.println("ccmissingnumsbefore: "+ missingNum + " " + currCol.missingNums.toString());
                        //System.out.println("copybefore: " + missingNumsList.toString());
                        ArrayList<Integer> potentialValsList = testBoard.getNumConflicts(currCol.colNum, missingNum, i, missingNumsList);
                        //System.out.println("ccmissingnumsafter: " + currCol.missingNums.toString());
                        //System.out.println("copyafter: " + missingNumsList.toString());

                        // this means there is only 1 spot this val to go in
                        if (potentialValsList.size() == 1) {
                            int[] updatedColVals = testBoard.getColumn(currCol.colNum).values;
                            updatedColVals[i] = potentialValsList.get(0);
                            Column newCol = new Column(updatedColVals, currCol.colNum);
                            //System.out.println("before:");
                            //BoardUtils.printBoardState(currBoard);
                            testBoard.updateColumn(currCol.colNum, newCol, i, potentialValsList.get(0));
                            currBoard = testBoard;
                            System.out.println("after");
                            BoardUtils.printBoardState(currBoard);
                            //buckets[fewestRemainingIndex].remove(minStruct);
                            //reset the list
                            missingNumsList = board.clone(currCol.missingNums);
                            missingNumsList.remove(new Integer(potentialValsList.get(0)));
                            currCol.missingNums = missingNumsList;
                            //updateBuckets(buckets, new BoardStruct(newCol), i, currBoard);
                            break;
                        }
                    }
                    missingNumsList = board.clone(currCol.missingNums);
                }
            } else {
                System.out.println("MARIX");
            }
        }
    }

    // instantiate buckets in beginnnign of solve function NOT IN WHILE LOOP
    // CREAATE NEW UPDATE BUCKETS FUNCTION THAT GOES IN WHILE LOOP (takes in row/col/mat to update the buckets)
    public void instantiateBuckets(ArrayList<BoardStruct>[] buckets, Board board) {
        Arrays.fill(buckets, null);
        Row[] rows = board.rowArr;
        Column[] cols = board.colArr;
        SubMatrix[] mats = board.matArr;
        for (Row r : rows) {
            int index = r.numSolved-1;
            if (index == 9) continue;
            if (buckets[index] == null) {
                buckets[index] = new ArrayList<>();
            }
            BoardStruct rowStruct = new BoardStruct(r);
            rowStruct.bucketIndex = index;
            buckets[index].add(rowStruct);
        }
        for (Column c : cols) {
            int index = c.numSolved-1;
            if (index == 9) continue;
            if (buckets[index] == null) {
                buckets[index] = new ArrayList<>();
            }
            BoardStruct colStruct = new BoardStruct(c);
            colStruct.bucketIndex = index;
            buckets[index].add(colStruct);
        }
        for (SubMatrix s : mats) {
            int index = s.numSolved-1;
            if (index == 9) continue;
            if (buckets[index] == null) {
                buckets[index] = new ArrayList<>();
            }
            BoardStruct matStruct = new BoardStruct(s);
            matStruct.bucketIndex = index;
            buckets[index].add(matStruct);
        }
        for (int i = 7; i >= 0; i--) {
            if (buckets[i] != null && buckets[i].size() != 0)  {
                fewestRemainingIndex = i;
                break;
            }
        }
        printBucket(buckets);
    }

    /**
     *
     * @param buckets
     * @param struct the struct we just finished modifying, use its num to determine what other structs we have to update
     * @param index the index of the spot of the struct we modified
     */
    public void updateBuckets(ArrayList<BoardStruct>[] buckets, BoardStruct struct, int index, Board board) {
        //if i update col 1, spot1, i have to update mat1 and row1
        int structNum = struct.num;
        if (struct.type == TYPE.ROW) {

        } else if (struct.type == TYPE.COLUMN) {
            Row row = board.getRow(index+1);
            int rowBucketIndex = row.numSolved-2;
            System.out.println("row update: " + row.toString() + " index: " + rowBucketIndex);
            BoardStruct rowStruct = getStruct(buckets, new BoardStruct(row), rowBucketIndex);
            buckets[rowBucketIndex].remove(rowStruct);
            if (rowBucketIndex < 8) {
                if (buckets[rowBucketIndex+1] == null) buckets[rowBucketIndex+1] = new ArrayList<>();
                buckets[rowBucketIndex+1].add(rowStruct);
            }
            SubMatrix mat = board.getConflictingMatrix(struct.col, row);
            mat.printSubMatrix();
            int matBucketIndex = mat.numSolved-2;
            System.out.println("index: " + matBucketIndex);
            BoardStruct matStruct = getStruct(buckets, new BoardStruct(mat), matBucketIndex);
            buckets[matBucketIndex].remove(matStruct);
            if (matBucketIndex < 8) {
                if (buckets[matBucketIndex+1] == null) buckets[matBucketIndex+1] = new ArrayList<>();
                buckets[matBucketIndex+1].add(matStruct);
            }
            int colBucketIndex;
            if (struct.col.numSolved == 9) {
                colBucketIndex = 8;
            } else {
                colBucketIndex = struct.col.numSolved-2;
            }
            System.out.println("col update: " + struct.col.toString() + " index: " + colBucketIndex);
            BoardStruct colStruct = getStruct(buckets, struct, colBucketIndex);
            buckets[colBucketIndex].remove(colStruct);
            if (colBucketIndex < 8) {
                if (buckets[colBucketIndex+1] == null) buckets[colBucketIndex+1] = new ArrayList<>();
                buckets[colBucketIndex+1].add(struct);
            }
            for (int i = 7; i >= 0; i--) {
                if (buckets[i] != null && buckets[i].size() != 0)  {
                    fewestRemainingIndex = i;
                    break;
                }
            }
            System.out.println("UPDATED###############");
            printBucket(buckets);
            System.out.println("######################");
        } else {

        }
    }

    public BoardStruct getStruct(ArrayList<BoardStruct>[] buckets, BoardStruct struct, int bucketIndex) {
        System.out.println(buckets[bucketIndex] == null);
        ArrayList<BoardStruct> bucket = buckets[bucketIndex];
        for (BoardStruct bs : bucket) {
            if (bs.num == struct.num && bs.type == struct.type) return bs;
        }
        System.out.println("OH NO DIDNT FIND STRUCT");
        return null;
    }

    public boolean contains(BoardStruct struct, ArrayList<BoardStruct>[] bucket, int index) {
        for (BoardStruct bs : bucket[index]) {
            if (bs.num == struct.num) return true;
        }
        return false;
    }




}
