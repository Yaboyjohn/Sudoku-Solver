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
    public Board solve(Board board) {
        // each index of the bucket corresponds to number of solved entries
        // there are arraylists holding the structs with that number of solved entries
        // we grab the arraylist with the max number of solved entries (fewestRemainingIndex)
        // take the first boardstruct in the arraylist at fewestRemainingIndex
        // for each 0 slot, we check all conflicting neighboring structs to see if we can find a guaranteed correct location
        currBoard = board;
        int count = 0;
        int attempt = 0;
        instantiateBuckets(buckets, currBoard);
        while (!validateSolution(currBoard)) {
            boolean foundConfirmedNumber = false;
            count++;
            ArrayList<BoardStruct> minBucket = buckets[fewestRemainingIndex];
            printBucket(buckets);
            //printBoardState(currBoard);
            //System.out.println("FI: " + fewestRemainingIndex);
            BoardStruct minStruct = minBucket.get(0);
            System.out.println("MIN STRUCT: " + minStruct.type + " " + minStruct.num);
            //printBoardState(currBoard);
            if (minStruct.type == TYPE.ROW) {
                Row currRow = minStruct.row;
                ArrayList<Integer> missingNumsList = board.clone(currRow.missingNums);
                ArrayList<Integer> missingNumsIndicesList = board.clone(currRow.missingNumsIndices);
                System.out.println("missinign INdices: "  + missingNumsIndicesList);
                System.out.println("missingNums: " + missingNumsList);

                // for each empty spot, try to place each missing num and check conflicting boardStructs to see if we can confirm placement
                for (int missingNumsIndex : missingNumsIndicesList) {
                    System.out.println("index: " + missingNumsIndex);
                    for (int missingNum : currRow.missingNums) {
                        System.out.println("missing num: " + missingNum);
                        Board testBoard = currBoard.clone();
                        ArrayList<Integer> potentialValsList = testBoard.getNumRowConflicts(currRow.rowNum, missingNum, missingNumsIndex, missingNumsList);
                        System.out.println("potvallist: " + potentialValsList.toString());
                        // this means there is only 1 spot this val to go in, it has to go in that spot
                        if (potentialValsList.size() == 1) {
                            foundConfirmedNumber = true;
                            // insert the new value into this row and update all row meta data and affected boardstructs
                            Row rowToUpdate = testBoard.getRow(currRow.rowNum);
                            rowToUpdate.values[missingNumsIndex] = potentialValsList.get(0);
                            testBoard.updateRow(currRow.rowNum, rowToUpdate, missingNumsIndex, potentialValsList.get(0));
                            currBoard = testBoard;
                            //reset the list
                            missingNumsList = board.clone(currRow.missingNums);
                            missingNumsList.remove(new Integer(potentialValsList.get(0)));
                            currRow.missingNums = missingNumsList;
                            break;
                        }
                    }
                    missingNumsList = board.clone(currRow.missingNums);
                }
                // if we didn't find a guaranteed spot, we move this struct to end of its bucket
                if (!foundConfirmedNumber) {
//                    System.out.println("hi: " + buckets[fewestRemainingIndex].size());
//                    printBucket(buckets);
//                    printBoardState(currBoard);
                    // there is only 1 struct at the fewestIndex but it couldn't confirm any spots. Demote fewest remaining index to
                    // see if we can confirm spots in a lesser bucket
                    if (buckets[fewestRemainingIndex].size() == 1) {
                        fewestRemainingIndex--;
                    } else {
                        if (attempt == 2) {
                            fewestRemainingIndex = updateFewestRemainingIndex(buckets, fewestRemainingIndex-1);
                            attempt = 0;
                        } else {
                            buckets[fewestRemainingIndex].remove(minStruct);
                            buckets[fewestRemainingIndex].add(minStruct);
                            attempt++;
                        }
                    }
                } else {
                    instantiateBuckets(buckets, currBoard);
                }
            } else if (minStruct.type == TYPE.COLUMN) {
                //printBoardState(currBoard);
                Column currCol = minStruct.col;
                ArrayList<Integer> missingNumsList = board.clone(currCol.missingNums);
                ArrayList<Integer> missingNumsIndicesList = board.clone(currCol.missingNumsIndices);
                for (int i : missingNumsIndicesList) {
                    for (int missingNum : currCol.missingNums) {
                        Board testBoard = currBoard.clone();
                        ArrayList<Integer> potentialValsList = testBoard.getNumColConflicts(currCol.colNum, missingNum, i, missingNumsList);

                        // this means there is only 1 spot this val to go in, it has to go in that spot
                        if (potentialValsList.size() == 1) {
                            foundConfirmedNumber = true;
                            int[] updatedColVals = testBoard.getColumn(currCol.colNum).values;
                            updatedColVals[i] = potentialValsList.get(0);
                            Column newCol = new Column(updatedColVals, currCol.colNum);
                            testBoard.updateColumn(currCol.colNum, newCol, i, potentialValsList.get(0));
                            currBoard = testBoard;
                            //reset the list
                            missingNumsList = board.clone(currCol.missingNums);
                            missingNumsList.remove(new Integer(potentialValsList.get(0)));
                            currCol.missingNums = missingNumsList;
                            break;
                        }
                    }
                    missingNumsList = board.clone(currCol.missingNums);
                }
                if (!foundConfirmedNumber) {
                    if (buckets[fewestRemainingIndex].size() == 1) {
                        fewestRemainingIndex--;
                    } else {
                        if (attempt == 2) {
                            fewestRemainingIndex = updateFewestRemainingIndex(buckets, fewestRemainingIndex-1);
                            attempt = 0;
                        } else {
                            buckets[fewestRemainingIndex].remove(minStruct);
                            buckets[fewestRemainingIndex].add(minStruct);
                            attempt++;
                        }
                    }
                } else {
                    instantiateBuckets(buckets, currBoard);
                }
            } else {
                SubMatrix mat = minStruct.mat;
                ArrayList<Integer> missingNumsList = board.clone(mat.missingNums);
                ArrayList<SubMatrix.indexStruct> missingNumsIndicesList = board.cloneMat(mat.missingNumsIndices);
                for (SubMatrix.indexStruct index : missingNumsIndicesList) {
                    for (int missingNum : mat.missingNums) {
                        Board testBoard = currBoard.clone();
                        ArrayList<Integer> potentialValsList = testBoard.getNumMatConflicts(mat.matrixNum, missingNum, index, missingNumsList);

                        // this means there is only 1 spot this val to go in, it has to go in that spot
                        if (potentialValsList.size() == 1) {
                            foundConfirmedNumber = true;
                            mat.update(index.rowIndex, index.colIndex, potentialValsList.get(0));
                            testBoard.updateMat(mat.matrixNum, mat, index, potentialValsList.get(0));
                            currBoard = testBoard;
                            //reset the list
                            missingNumsList = board.clone(mat.missingNums);
                            missingNumsList.remove(new Integer(potentialValsList.get(0)));
                            mat.missingNums = missingNumsList;
                            break;
                        }
                    }
                    missingNumsList = board.clone(mat.missingNums);
                }
                if (!foundConfirmedNumber) {
                    if (buckets[fewestRemainingIndex].size() == 1) {
                        fewestRemainingIndex--;
                    } else {
                        // there were more than 1 struct in this bucket, append to end to see if other member can confirm a spot
                        if (attempt == 2) {
                            fewestRemainingIndex = updateFewestRemainingIndex(buckets, fewestRemainingIndex-1);
                            attempt = 0;
                        } else {
                            buckets[fewestRemainingIndex].remove(minStruct);
                            buckets[fewestRemainingIndex].add(minStruct);
                            attempt++;
                        }
                    }
                } else {
                    instantiateBuckets(buckets, currBoard);
                }
            }
        }
        System.out.println("steps: " + count);
        printBoardState(currBoard);
        return currBoard;
    }

    // called prior to while loop
    // counts the number of solved spots for each col/row/matrix and places them into the buckets
    // CREATE NEW UPDATE BUCKETS FUNCTION THAT GOES IN WHILE LOOP (takes in row/col/mat to update the buckets)
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

        // we start at bucket 7 (8 solved) and try to find the bucket with the fewest remaining spots needed to be filled
        for (int i = 7; i >= 0; i--) {
            if (buckets[i] != null && buckets[i].size() != 0)  {
                fewestRemainingIndex = i;
                break;
            }
        }
        printBoardState(board);
        printBucket(buckets);
    }

    public int updateFewestRemainingIndex(ArrayList<BoardStruct>[] buckets, int currIndex) {
        while (buckets[currIndex] == null || buckets[currIndex].size() == 0) {
            currIndex--;
        }
        return currIndex;
    }

    /**
     *
     * @param buckets
     * @param struct the struct we just finished modifying, use its num to determine what other structs we have to update
     * @param index the index of the spot of the struct we modified
     */
    public void updateBuckets(ArrayList<BoardStruct>[] buckets, BoardStruct struct, int index, Board board) {

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
