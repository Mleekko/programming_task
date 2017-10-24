package com.mleekko.spreadsheet.rpn;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.rpn.element.CellReference;
import com.mleekko.spreadsheet.rpn.element.Constant;
import com.mleekko.spreadsheet.rpn.operators.MinusOperator;
import com.mleekko.spreadsheet.rpn.operators.DivideOperator;
import com.mleekko.spreadsheet.rpn.operators.MultiplyOperator;
import com.mleekko.spreadsheet.rpn.operators.PlusOperator;
import com.mleekko.spreadsheet.util.CellUtil;

import java.util.*;
import java.util.regex.Pattern;

/**
 *  Reverse Polish notation (RPN) expression evaluator.
 */
public class RPN {

    private static final Pattern SPACES = Pattern.compile("\\s+");

    private final Map<String, Operator> operators = new HashMap<>();

    public RPN() {
        operators.put("+", new PlusOperator());
        operators.put("-", new MinusOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("/", new DivideOperator());
    }

    public List<ExpressionElement> parseExpression(String expression) {
        String[] args = SPACES.split(expression.trim());

        List<ExpressionElement> elements = new ArrayList<>(args.length);

        for (String arg : args) {
            // check if it is an operator
            Operator operator = operators.get(arg);
            if (operator != null) {
                elements.add(operator);
            } else {
                try {
                    // try if it is a number
                    Integer operand = Integer.parseInt(arg);
                    elements.add(new Constant(operand.doubleValue()));
                } catch (IllegalArgumentException e) {
                    // assume it is a cell reference or fail
                    CellReference reference = CellUtil.parse(arg);
                    elements.add(reference);
                }
            }
        }

        return elements;
    }

    public double evaluate(List<ExpressionElement> parsedExpression) {
        Stack<Double> stack = new Stack<>();

        for (ExpressionElement el : parsedExpression) {
            if (!el.isOperator()) {
                stack.push(el.getValue());
            } else {
                if (el instanceof Operator) {
                    Double right = stack.pop();
                    Double left = stack.pop();
                    double result = ((Operator)el).apply(left, right);

                    stack.push(result);
                } else {
                    throw BadException.die("Unsupported operator class: " + el.getClass());
                }
            }
        }

        return stack.peek();
    }

    public double evaluateSimple(String expression) {
        return evaluate(parseExpression(expression));
    }
}
