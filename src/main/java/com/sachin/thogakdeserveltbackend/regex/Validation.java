package com.sachin.thogakdeserveltbackend.regex;


import java.util.regex.Pattern;

public class Validation {


    private static final Pattern idPattern = Pattern.compile("^C00\\d+$");
    private static final Pattern itemCodePattern = Pattern.compile("^P00\\d+$");
    private static final Pattern itemDes = Pattern.compile("^[A-Za-z0-9\\s'-]+$");
    private static final Pattern itemPrice = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    private static final Pattern itemQty = Pattern.compile("^\\d+$");
    private static final Pattern namePattern = Pattern.compile("^[A-Za-z\\s'-]+$");
    private static final Pattern addressPattern = Pattern.compile("^[0-9A-Za-z\\s',.-]+$");

    public boolean match(String field, Validates validate) {
        switch (validate) {
            case CUSTOMER_ID: {
                return idPattern.matcher(field).matches();
            }
            case NAME: {
                return namePattern.matcher(field).matches();
            }
            case ADDRESS: {
                return addressPattern.matcher(field).matches();
            }
            case ITEM_CODE: {
                return itemCodePattern.matcher(field).matches();
            }
            case ITEM_DESCRIPTION:{
                return itemDes.matcher(field).matches();
            }
            case PRICE:{
                return itemPrice.matcher(field).matches();
            }
            case QTY:{
                return itemQty.matcher(field).matches();
            }
            default: {
                return false;
            }
        }
    }
}
