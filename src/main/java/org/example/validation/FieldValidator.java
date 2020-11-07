package org.example.validation;

public class FieldValidator {

//    public static void checkStringPattern(String str) {
//        String regex = "^[a-zA-Z]+$";
//        if (!str.matches(regex)) {
//            throw new RuntimeException("There is a mismatch. You must enter a string.");
//        }
//    }

    public static void mobileValidate(String str) {
        String regex = "^09[0,1,3,9][0-9]{8}$";
        if (!str.matches(regex)) {
            throw new RuntimeException("There is a mismatch. You must enter a valid mobile number.");
        }
    }

    public static void zoneValidate(int zone){
        if (zone <=0 ){
            throw new RuntimeException("There is a mismatch. You must enter a valid zone number.");
        }
    }
}
