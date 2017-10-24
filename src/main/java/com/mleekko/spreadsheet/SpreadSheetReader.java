package com.mleekko.spreadsheet;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.util.InputUtil;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Expects input:
 * line 1 - number of columns (width N), unlimited
 * line 2 - number of rows    (height M), max 26
 * line 3..NxM+2 - expressions
 */
public class SpreadSheetReader {

    public SpreadSheet read(InputStream source) {
        try (Scanner sc = new Scanner(source)) {
            int width = InputUtil.readInt(sc);
            int height = InputUtil.readInt(sc);

            validateDimensions(width, height);

            SpreadSheet spreadsheet = new SpreadSheet(width, height);

            for (int m = 0; m < height; m++) {
                for (int n = 0; n < width; n++) {
                    String line = sc.nextLine();
                    if (line == null) {
                        throw BadException.die("Input too short. Expected a line for m=" + m + " n=" + n);
                    }
                    spreadsheet.setCell(m, n, line.trim());
                }
            }

            return spreadsheet;
        }
    }

    private static void validateDimensions(int width, int height) {
        if (width <= 0) {
            throw BadException.die("Spreadsheet width expected to be > 0. Found: " + width);
        }

        if (height <= 0 || height > 26) {
            throw BadException.die("Spreadsheet width expected to be in range (0, 26]. Found: " + height);
        }

    }
}
