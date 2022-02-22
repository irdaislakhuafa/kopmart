package com.irdaislakhuafa.kopmart.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClassHelper {
    public static List<String> getFieldsFrom(Class<?> cls, FieldsTextMode textMode, String... ignoredFields) {
        List<String> fields = new ArrayList<>();
        try {
            // get fields from class
            Arrays.asList(cls.getDeclaredFields()).forEach((field) -> {
                fields.add(
                        (textMode.equals(FieldsTextMode.UPPERCASE)) ? field.getName().toUpperCase()
                                : (textMode.equals(FieldsTextMode.LOWERCASE)) ? field.getName().toLowerCase()
                                        : field.getName());
            });

            // remove ignored fields
            Arrays.asList(ignoredFields).forEach((ignoreField) -> {
                fields.removeIf((field) -> field.equalsIgnoreCase(ignoreField));
            });
        } catch (Exception e) {
            UserHelper.errorLog("failed to get fields from class", cls);
        }
        return fields;
    }

    // get fields from HashMap
    public static List<String> getFieldsFromHashMap(Map<String, Object> map, FieldsTextMode fieldsTextMode,
            String... ignoredFields) {
        List<String> fields = new ArrayList<>();
        // save key map to fields
        map.entrySet().forEach((field) -> {
            fields.add(
                    (fieldsTextMode.equals(FieldsTextMode.UPPERCASE)) ? field.getKey().toUpperCase()
                            : (fieldsTextMode.equals(FieldsTextMode.LOWERCASE)) ? field.getKey().toLowerCase()
                                    : field.getKey());
        });
        // remove ignored fields
        fields.removeAll(Arrays.asList(ignoredFields));
        return fields;
    }
}
