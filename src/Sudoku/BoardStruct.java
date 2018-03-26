package Sudoku;

import java.util.Arrays;

public class BoardStruct {
    public enum TYPE {
        ROW, COLUMN, MATRIX;
    }
    TYPE type;
    Row row;
    Column col;
    SubMatrix mat;
    int num;
    char name = ' ';
    int bucketIndex;

    public BoardStruct(Row row) {
        this.type = TYPE.ROW;
        this.row = row;
        this.col = null;
        this.mat = null;
        this.num = row.rowNum;
    }

    public BoardStruct(Column col) {
        this.type = TYPE.COLUMN;
        this.row = null;
        this.col = col;
        this.mat = null;
        this.num = col.colNum;
        this.name = col.colName;
    }

    public BoardStruct(SubMatrix mat) {
        this.type = TYPE.MATRIX;
        this.row = null;
        this.col = null;
        this.mat = mat;
        this.num = mat.matrixNum;
    }
}
