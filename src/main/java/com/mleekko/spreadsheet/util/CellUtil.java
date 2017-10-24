package com.mleekko.spreadsheet.util;

import com.mleekko.spreadsheet.ex.BadException;
import com.mleekko.spreadsheet.rpn.element.CellReference;

public abstract class CellUtil {
    private static final char A = "A".charAt(0);


    public static String coordsToCellName(int m, int n) {
        return String.valueOf((char) (A + m)) + (n + 1);
    }

    public static String cellReferenceToName(CellReference ref) {
        return coordsToCellName(ref.column, ref.row);
    }

    public static CellReference parse(String cellName) {
        char rowName = cellName.charAt(0);
        String columnNumber = cellName.substring(1);

        try {
            return new CellReference(rowName - A, Integer.parseInt(columnNumber) - 1);
        } catch (NumberFormatException e) {
            throw BadException.die("Invalid cell name: " + cellName);
        }

    }

}
