package com.mleekko.spreadsheet.rpn;

import com.mleekko.spreadsheet.rpn.operators.DivideOperator;
import com.mleekko.spreadsheet.rpn.operators.MinusOperator;
import com.mleekko.spreadsheet.rpn.operators.MultiplyOperator;
import com.mleekko.spreadsheet.rpn.operators.PlusOperator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

public class RPN {

    private static final Pattern SPACES = Pattern.compile("\\s+");

    private final Map<String, Operator> operators = new HashMap<>();

    public RPN() {
        operators.put("+", new PlusOperator());
        operators.put("-", new MinusOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("/", new DivideOperator());
    }

    public double resolve(String expression) {
        Stack<Double> stack = new Stack<>();

        String[] args = SPACES.split(expression.trim());

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            try {
                // if it is a number - push to stack
                Integer operand = Integer.parseInt(arg);
                stack.push(operand.doubleValue());
            } catch (IllegalArgumentException e) {
                // it's an operator - resolve

                Operator operator = operators.get(arg);

                if (operator == null) {
                    throw new RuntimeException("Unsupported operator: `" + arg + "`. Should be one of: " + operators.keySet());
                }

                Double right = stack.pop();
                Double left = stack.pop();
                double result = operator.apply(left, right);

                stack.push(result);
            }
        }

        return stack.peek();
    }

}
