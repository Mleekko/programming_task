package com.mleekko.spreadsheet.rpn;

public interface Operator extends ExpressionElement {

    @Override
    default boolean isOperator() {
        return true;
    }

    @Override
    default double getValue() {
        return 0;
    }

    double apply(double left, double right);
}
