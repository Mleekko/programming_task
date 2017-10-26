package com.mleekko.spreadsheet;

import com.mleekko.spreadsheet.ex.CyclicReferenceException;
import com.mleekko.spreadsheet.sheet.SpreadSheet;
import com.mleekko.spreadsheet.sheet.SpreadSheetReader;
import com.mleekko.spreadsheet.sheet.SpreadSheetWriter;

public class Application {


    /**
     * For expected inout format see {@link SpreadSheetReader}
     */
    public static void main(String[] args) {
        try {
            SpreadSheetReader reader = new SpreadSheetReader();
            SpreadSheetWriter writer = new SpreadSheetWriter();

            SpreadSheet spreadsheet = reader.read(System.in);

            spreadsheet.resolve();

            System.out.println(writer.asString(spreadsheet));
        } catch (CyclicReferenceException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

}
