package model;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Validation {
//(name.charAt(i) < 'A' || name.charAt(i) > 'Z') && (name.charAt(i) < 'a' || name.charAt(i) > 'z')
    public static boolean checkName(String name) {
        
        for (int i = 0; i < name.length(); i++) {
            if ( !Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
                return false;
            }
        }
        
        return true;
    }

    public static boolean isLetter(String p) {
        for (int i = 0; i < p.length(); i++) {
            if (!Character.isLetter(p.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkEmail(String email) {
        email = email.replaceAll("\\s+", " ");
        Pattern p = Pattern.compile("^[a-z0-9A-Z]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}+$");
        return p.matcher(email).find();
    }

    public static int ageValid(Date day) {
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(day);
        if (dob.after(now)) {
            return -1;
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }

    public static boolean checkPhone(String stringPhone) {
        Pattern p = Pattern.compile("^[0-9]{9,20}$");
        return p.matcher(stringPhone).find();
    }

    public static String convertName(String name) {
        name = name.replaceAll("\\s+", " ").trim();
        String str = "";
        str = str + Character.toUpperCase(name.charAt(0));
        for (int i = 1; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                str = str + name.charAt(i) + Character.toUpperCase(name.charAt(i + 1));
                i++;
            } else {
                str = str + Character.toLowerCase(name.charAt(i));
            }
        }
        
        return str;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();
        System.out.println(Validation.checkName(s));
    }
}
