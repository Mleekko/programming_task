package com.mleekko.spreadsheet;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.rpn.ExpressionEvaluator;
import com.mleekko.spreadsheet.util.CellUtil;

import java.util.*;

public class SpreadSheet {


    public final ExpressionEvaluator evaluator = new ExpressionEvaluator();
    public final int width;
    public final int height;

    private final Cell[][] cells;


    public SpreadSheet(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[height][width];
    }

    public void setCell(int m, int n, String expression) {
        cells[m][n] = new Cell(expression);
    }

    public Cell getCell(int m, int n) {
        Cell cell = cells[m][n];
        if (cell == null) {
            throw BadException.die("Unexpected error occurred: cell " + CellUtil.coordsToCellName(m, n) + " was not initialized!");
        }
        return cell;
    }


    public void resolve() {
        for (int m = 0; m < cells.length; m++) {
            Cell[] row = cells[m];
            for (int n = 0; n < row.length; n++) {
                Cell cell = getCell(m, n);

                if (cell.getValue() == null) {
                    Set<String> resolutionChain = new LinkedHashSet<>();

                    cell.resolveValue(this, resolutionChain, CellUtil.coordsToCellName(m, n));
                }
            }
        }
    }


    public String asString() {

        StringBuilder sb = new StringBuilder();

        sb.append(cells.length).append('\n');
        sb.append(cells[0].length).append('\n');

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                sb.append(format(cell.getValue())).append('\n');
            }
        }
        return sb.toString();
    }

    private static String format(double value) {
        Formatter formatter = new Formatter(new Locale("en", "US"));
        return formatter.format("%.5f", value).toString();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Cell[] cell : cells) {
            sb.append(Arrays.toString(cell)).append('\n');
        }

        return sb.toString();
    }

}
