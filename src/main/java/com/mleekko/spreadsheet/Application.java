package com.mleekko.spreadsheet;

import com.mleekko.spreadsheet.util.InputUtil;

import java.util.Arrays;
import java.util.Scanner;

public class Application {

    /**
     * Expects input:
     *  line 1 - number of columns (width N), unlimited
     *  line 2 - number of rows    (height M), max 26
     *  line 3..NxM+2 - expressions
     */
    // TODO: WHY columns are unlimited (1-N) but only 16 rows ??? @mleekko
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        try {
            int width = InputUtil.readInt(sc);
            int height = InputUtil.readInt(sc);

            validateDimensions(width, height);

            String[][] spreadsheet = new String[height][width];

            for (int m = 0; m < spreadsheet.length; m++) {
                String[] row = spreadsheet[m];
                for (int n = 0; n < row.length; n++) {
                    String line = sc.nextLine();
                    if (line == null) {
                        throw new RuntimeException("Input too short. Expected a line for m=" + m + " n=" + n);
                    }
                    row[n] = line.trim();
                }
            }

            for (int i = 0; i < spreadsheet.length; i++) {
                System.out.println(Arrays.toString(spreadsheet[i]));
            }

        } finally {
            sc.close();
        }
    }

    private static void validateDimensions(int width, int height) {
        if (width <= 0) {
            throw new RuntimeException("Spreadsheet width expected to be > 0. Found: " + width);
        }

        if (height <= 0 || height > 26) {
            throw new RuntimeException("Spreadsheet width expected to be in range (0, 26]. Found: " + height);
        }

    }




}
