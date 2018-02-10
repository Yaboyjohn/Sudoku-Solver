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

    public static Row[] getAllRows(Board board) {
        Row[] rowArr = new Row[9];
        for (int i = 1; i < 10; i++) {
            rowArr[i-1] = new Row(board, i);
        }
        return rowArr;
    }

    public static Column[] getAllColumns(Board board) {
        Column[] colArr = new Column[9];
        for (int i = 0; i < 9; i++) {
            char c = (char) (i+65);
            colArr[i] = new Column(board, c);
        }
        return colArr;
    }

    public static SubMatrix[] getAllSubMatrices(Board board) {
        SubMatrix[] matArr = new SubMatrix[9];
        for (int i = 1; i < 10; i++) {
            matArr[i-1] = new SubMatrix(board, i);
        }
        return matArr;
    }
}
