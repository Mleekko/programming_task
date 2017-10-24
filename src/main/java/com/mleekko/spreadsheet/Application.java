package com.mleekko.spreadsheet;

import com.mleekko.spreadsheet.ex.CyclicReferenceException;

public class Application {


    /**
     * For expected inout format see {@link SpreadSheetReader}
     */
    public static void main(String[] args) {
        try {
            SpreadSheetReader reader = new SpreadSheetReader();
            SpreadSheet spreadsheet = reader.read(System.in);

            spreadsheet.resolve();

            System.out.println(spreadsheet.asString());
        } catch (CyclicReferenceException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }
    }

}
