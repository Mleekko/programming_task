package com.mleekko.spreadsheet.sheet;

import java.util.Formatter;
import java.util.Locale;

public class SpreadSheetWriter {

    public String asString(SpreadSheet sheet) {
        StringBuilder sb = new StringBuilder();

        sb.append(sheet.width).append('\n');
        sb.append(sheet.height).append('\n');


        sheet.forEachCell(cell -> {
            String formattedValue = format(cell.getValue());
            sb.append(formattedValue).append('\n');
        });

        return sb.toString();
    }


    private static String format(double value) {
        Formatter formatter = new Formatter(new Locale("en", "US"));
        return formatter.format("%.5f", value).toString();
    }

}
