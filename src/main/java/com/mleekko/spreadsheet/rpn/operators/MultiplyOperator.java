package com.mleekko.spreadsheet.rpn.operators;

import com.mleekko.spreadsheet.rpn.Operator;

public class MultiplyOperator implements Operator {

    @Override
    public double apply(double left, double right) {
        return left * right;
    }
}
