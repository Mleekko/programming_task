package com.mleekko.spreadsheet;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.ex.CyclicReferenceException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class SpreadSheetTest {

    @Test
    public void testReadSpreadSheet() throws Exception {
        SpreadSheet spreadSheet = create("" +
                "3\n" +
                "2\n" +
                "A1\n" +
                "A2\n" +
                "A3\n" +
                "B1\n" +
                "B2\n" +
                "B3");

        assertEquals("" +
                "[A1, A2, A3]\n" +
                "[B1, B2, B3]\n", spreadSheet.toString());
    }

    @Test
    public void testInvalidRows() throws Exception {
        try {
            create("" +
                            "3\n" +
                            "200\n" +
                            "A1\n");
        } catch (BadException e) {
            // expected
            assertTrue(e.getMessage().contains("200"));
            return;
        }

        fail("Exception should have been thrown.");
    }

    @Test
    public void testCyclicReference() throws Exception {
        SpreadSheet sheet = create("" +
                "2\n" +
                "1\n" +
                "A2\n" +
                "A1\n");

        try {
            sheet.resolve();
        } catch (CyclicReferenceException e) {
            // expected
            return;
        }

        fail("Exception should have been thrown.");
    }

    @Test
    public void testCyclicReference2() throws Exception {
        SpreadSheet sheet = create("" +
                "1\n" +
                "4\n" +
                "100\n" +
                "C1\n" +
                "D1\n" +
                "B1\n");

        try {
            sheet.resolve();
        } catch (CyclicReferenceException e) {
            // expected
            return;
        }

        fail("Exception should have been thrown.");
    }

    @Test
    public void testResolveWithEvaluation() throws Exception {
        SpreadSheet sheet = create("" +
                "3\n" +
                "2\n" +
                "A2\n" +
                "4 5 *\n" +
                "A1\n" +
                "A1 B2 / 2 +\n" +
                "3\n" +
                "39 B1 B2 * /\n");

        sheet.resolve();

        assertEquals("" +
                "2\n" +
                "3\n" +
                "20.00000\n" +
                "20.00000\n" +
                "20.00000\n" +
                "8.66667\n" +
                "3.00000\n" +
                "1.50000\n", sheet.asString());
    }


    private static SpreadSheet create(String input) throws UnsupportedEncodingException {
        SpreadSheetReader reader = new SpreadSheetReader();

        ByteArrayInputStream stream = new ByteArrayInputStream(
                input.getBytes("utf-8"));

        return reader.read(stream);
    }


}
