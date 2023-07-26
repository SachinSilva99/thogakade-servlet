package com.sachin.thogakdeserveltbackend.regex;




import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {



    private static final Pattern idPattern = Pattern.compile("^C00\\d+$");
    private static final Pattern namePattern = Pattern.compile("^[A-Za-z\\s'-]+$");
    private static final Pattern addressPattern = Pattern.compile("^[0-9A-Za-z\\s',.-]+$");

    public  boolean match(String field, Validates validate){
        switch (validate){
            case CUSTOMER_ID:{
                Matcher matcher = idPattern.matcher(field);
                return matcher.matches();
            }
            case NAME:{
                Matcher matcher = namePattern.matcher(field);
                return matcher.matches();
            }
            case ADDRESS:{
                Matcher matcher = addressPattern.matcher(field);
                return matcher.matches();
            }
            default:{
                return false;
            }
        }
    }
}
