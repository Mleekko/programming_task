package com.mleekko.spreadsheet.rpn;

import org.junit.Assert;
import org.junit.Test;


public class ExpressionEvaluatorTest {

    @Test
    public void testSimpleEvaluation() {
        ExpressionEvaluator evaluator = new ExpressionEvaluator();


        assertEquals(4, evaluator.evaluateSimple("4"));
        assertEquals(20, evaluator.evaluateSimple("4 5 *"));
        assertEquals(8.666, evaluator.evaluateSimple("4 5 * 3 / 2 +"));
        assertEquals(1.5, evaluator.evaluateSimple("39 4 5 * 3 / 2 + 3 * /"));

        assertEquals(5, evaluator.evaluateSimple("15 7 1 1 + - / 3 * 2 1 1 + + -"));
    }

    private void assertEquals(double expected, double actual) {
        Assert.assertEquals(expected, actual, 0.001);
    }
}
