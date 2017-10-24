package com.mleekko.spreadsheet.rpn;

public interface ExpressionElement {

    default boolean isOperator() {
        return false;
    }

    default boolean isReference() {
        return false;
    }

    double getValue();
}
