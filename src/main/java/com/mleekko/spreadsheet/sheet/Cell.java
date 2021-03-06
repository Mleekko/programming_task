package com.mleekko.spreadsheet.sheet;

import com.mleekko.spreadsheet.ex.CyclicReferenceException;
import com.mleekko.spreadsheet.rpn.ExpressionElement;
import com.mleekko.spreadsheet.rpn.element.CellReference;

import java.util.List;
import java.util.Set;

public class Cell {

    private final String expression;

    private transient Double value;


    public Cell(String expression) {
        this.expression = expression;
    }

    public void resolveValue(SpreadSheet sheet, Set<String> resolutionChain, String cellName) {
        if (value != null) {
            return;
        }

        boolean hasCycle = !resolutionChain.add(cellName);
        if (hasCycle) {
            throw new CyclicReferenceException("Cyclic reference detected. Chain: " + getCycleChain(resolutionChain, cellName));
        }

        List<ExpressionElement> parsedExpression = sheet.evaluator.parseExpression(expression);
        for (ExpressionElement element : parsedExpression) {
            if (element.isReference()) {
                if (element instanceof CellReference) {
                    sheet.resolveReference(((CellReference) element), resolutionChain);
                } else {
                    throw new Error("Unsupported reference class: " + element.getClass());
                }
            }
        }

        value = sheet.evaluator.evaluate(parsedExpression);
    }


    public Double getValue() {
        return value;
    }

    private static String getCycleChain(Set<String> resolutionChain, String cellName) {
        StringBuilder sb = new StringBuilder();

        boolean startFound = false;
        for (String s : resolutionChain) {
            if (startFound || cellName.equals(s)) {
                startFound = true;
                sb.append(s).append(" -> ");
            }
        }

        return sb.append(cellName).toString();
    }


    @Override
    public String toString() {
        return expression;
    }
}
