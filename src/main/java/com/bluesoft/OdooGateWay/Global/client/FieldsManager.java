package com.bluesoft.OdooGateWay.Global.client;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class FieldsManager {
    public static String getFieldValue(Map<String, Object> odooObject, String fieldName) {
        Object response = odooObject.get(fieldName);
        String fieldValue = response != null ? response.toString() : null;
        return fieldValue != null && !fieldValue.equals("false") ? fieldValue : null;
    }
    public static String getEntityId(Map<String, Object> odooObject, String fieldName) {
        Object odooResponse = odooObject.get(fieldName);
        String odooString = odooResponse != null ? odooResponse.toString() : null;
        String internalReference = null;
        if (odooString != null && !odooString.equals("false")) {
            internalReference = ((Object[]) odooResponse)[0].toString();
        }
        return internalReference;
    }
    public static String[] getEntityIds(Map<String, Object> odooObject, String fieldName) {
        Object[] odooResponses = (Object[]) odooObject.get(fieldName);
        String[] entitiesIds = new String[odooResponses.length];
        for (int i = 0; i < odooResponses.length; i++) {
            entitiesIds[i] = String.valueOf(odooResponses[i]);
        }
        return entitiesIds;
    }
    public static String getEntityName(Map<String, Object> odooObject, String fieldName) {
        Object odooResponse = odooObject.get(fieldName);
        String odooString = odooResponse != null ? odooResponse.toString() : null;
        String internalReference = null;
        if (odooString != null && !odooString.equals("false")) {
            internalReference = ((Object[]) odooResponse)[1].toString();
        }
        return internalReference;
    }
    public static Boolean getBooleanFieldValue(Map<String, Object> odooObject, String fieldName) {
        Object response = odooObject.get(fieldName);
        return response != null ? (Boolean) response : false;
    }
    public static float getFloatFieldValue(Map<String, Object> odooObject, String fieldName) {
        String fieldValue = getFieldValue(odooObject, fieldName);
        if (fieldValue != null) {
            return Float.parseFloat(fieldValue);
        }
        return 0;
    }
    public static double getDoubleFieldValue(Map<String, Object> odooObject, String fieldName) {
        String fieldValue = getFieldValue(odooObject, fieldName);
        if (fieldValue != null) {
            return Double.parseDouble(fieldValue);
        }
        return 0;
    }
}
