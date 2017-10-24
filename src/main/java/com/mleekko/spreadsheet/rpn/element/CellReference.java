package com.mleekko.spreadsheet.rpn.element;

import com.mleekko.spreadsheet.Cell;
import com.mleekko.spreadsheet.SpreadSheet;
import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.rpn.ExpressionElement;
import com.mleekko.spreadsheet.util.CellUtil;

import java.util.Set;

/**
 * Holds 0-based coordinates to other cell in spreadsheet.
 */
public class CellReference implements ExpressionElement {
    public final int row;
    public final int column;

    private double value;

    public CellReference(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void resolve(SpreadSheet sheet, Set<String> resolutionChain) {
        if (column >= sheet.width) {
            throw BadException.die("Column number exceeds spreadsheet's bounds: " + CellUtil.cellReferenceToName(this));
        }
        if (row >= sheet.height) {
            throw BadException.die("Row exceeds spreadsheet's bounds: " + CellUtil.cellReferenceToName(this));
        }

        Cell cell = sheet.getCell(row, column);
        cell.resolveValue(sheet, resolutionChain, CellUtil.cellReferenceToName(this));

        value = cell.getValue();
    }

    @Override
    public boolean isReference() {
        return true;
    }

    @Override
    public double getValue() {
        return value;
    }
}
