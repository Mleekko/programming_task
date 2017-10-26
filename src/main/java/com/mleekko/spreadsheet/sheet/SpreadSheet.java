package com.mleekko.spreadsheet.sheet;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.rpn.ExpressionEvaluator;
import com.mleekko.spreadsheet.rpn.element.CellReference;
import com.mleekko.spreadsheet.util.CellUtil;

import java.util.*;
import java.util.function.Consumer;

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


    public void forEachCell(Consumer<Cell> cellConsumer) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                cellConsumer.accept(cell);
            }
        }
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

    void resolveReference(CellReference reference, Set<String> resolutionChain) {
        if (reference.isResolved()) {
            return;
        }

        if (reference.column >= width) {
            throw BadException.die("Column number exceeds spreadsheet's bounds: " + reference.getName());
        }
        if (reference.row >= height) {
            throw BadException.die("Row exceeds spreadsheet's bounds: " + reference.getName());
        }

        Cell cell = getCell(reference.row, reference.column);
        cell.resolveValue(this, resolutionChain, reference.getName());

        reference.setValue(cell.getValue());
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
