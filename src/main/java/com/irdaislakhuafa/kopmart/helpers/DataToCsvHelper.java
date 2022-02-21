package com.irdaislakhuafa.kopmart.helpers;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVWriter;

public class DataToCsvHelper {
    // write data to file CSV using arrays
    public static void writeDataToCsv(
            PrintWriter fileCsvWriter,
            List<Map<String, Object>> listData,
            String... ignoredFields)
            throws Exception {

        // object to save list text for file csv
        List<String[]> listTexts = new ArrayList<>();

        // create object CSV Writer
        CSVWriter csvWriter = new CSVWriter(
                // writer
                fileCsvWriter,
                // separator
                CSVWriter.DEFAULT_SEPARATOR,
                // quote character
                CSVWriter.NO_ESCAPE_CHARACTER,
                // escape caracter
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                // line end
                CSVWriter.DEFAULT_LINE_END);

        // write fields header
        listTexts.add(
                // get key fields from HashMap
                ClassHelper.getFieldsFromHashMap(
                        // get data from list index 0
                        listData.get(0),
                        // fields text mode
                        FieldsTextMode.UPPERCASE,
                        // input ignored fields (optional)
                        ignoredFields)
                        // convert return to array
                        .toArray(
                                // create new string array to save to listTexts
                                new String[listData.get(0).entrySet().size()]));

        listData.forEach((data) -> {
            // temp data to save temporary data from listData
            List<String> tempData = new ArrayList<>();

            // save data from each field to tempData
            data.entrySet().forEach((entry) -> {
                tempData.add(String.valueOf(entry.getValue()));
            });

            listTexts.add(
                    // convert tempData (ArrayList) to normal String array
                    tempData.toArray(
                            // create new String array to add to listTexts
                            new String[listData.get(0).size()]));
        });

        // write data to csv file
        csvWriter.writeAll(listTexts);
        csvWriter.close();
    }

    // generate csv file name
    public static String generateFileCsvName(String fileName) {
        return String
                .valueOf(UserHelper.getCurrentUser().get().getEmail() + " "
                        + new SimpleDateFormat("dd MMMM YYYY HH:mm:ss").format(new Date()) + " => " + fileName);
    }
}
