package com.mleekko.spreadsheet.rpn.element;

import com.mleekko.spreadsheet.rpn.ExpressionElement;

public class Constant implements ExpressionElement {
    private final double value;

    public Constant(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }
}
