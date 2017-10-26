package com.mleekko.spreadsheet.rpn.element;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.rpn.ExpressionElement;
import com.mleekko.spreadsheet.util.CellUtil;

/**
 * Holds 0-based coordinates to other cell in spreadsheet.
 */
public class CellReference implements ExpressionElement {
    public final int row;
    public final int column;

    private Double value;

    public CellReference(int row, int column) {
        this.row = row;
        this.column = column;
    }


    @Override
    public boolean isReference() {
        return true;
    }

    @Override
    public double getValue() {
        if (!isResolved()) {
            throw BadException.die("CellReference value asked before resolution: " + this.getName());
        }
        return value;
    }

    public boolean isResolved() {
        return value != null;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getName() {
        return CellUtil.cellReferenceToName(this);
    }
}
