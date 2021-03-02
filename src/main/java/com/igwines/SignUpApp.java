package com.igwines;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpApp {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Map<String, String> tokenCache = new HashMap<>();

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String email = typeEmail(in);
        tokenCache.put(email, UUID.randomUUID().toString());
        getToken(in);
        System.out.print("To sign up type email: ");
        String emailFromMap = getEmailFromMap(in);
        String tokenFromMap = tokenCache.get(emailFromMap);
        System.out.print("type token code: ");
        String token = in.next();
        if (tokenFromMap.equals(token)) {
            System.out.print("You are sucessfuly signed in!");
        } else {
            System.out.print("Unfortunately, token is wrong :-(");
        }
    }

    private static void getToken(Scanner in) {
        System.out.print("Do you want to get the token to sign up?\nPlease print Y - yes or N -no\n");
        String answer = in.next();
        if (answer.equals("Y")) {
            String emailFromMap = getEmailFromMap(in);
            System.out.println("token:  " + tokenCache.get(emailFromMap));
            return;
        } else if (answer.equals("N")) {
            System.out.println("Do you want to sign up with another email?\nPlease print Y - yes or N -no\n");
            String anotherAnswer = in.next();
            if (anotherAnswer.equals("Y")) {
                String email = typeEmail(in);
                tokenCache.put(email, UUID.randomUUID().toString());
                getToken(in);
            } else if (anotherAnswer.equals("N")) {
                getToken(in);
            } else {
                System.out.println("Wrong answer, please try again");
                getToken(in);
            }
        } else {
            System.out.println("Wrong answer, please try again");
            getToken(in);
        }
    }

    private static String getEmailFromMap(Scanner in) {
        String email = typeEmail(in);
        if (tokenCache.containsKey(email)) {
            return email;
        } else {
            System.out.println("Can not find this email, please try again");
            getEmailFromMap(in);
        }
        return null;
    }

    private static String typeEmail(Scanner in) {
        System.out.print("To get token, type your email: ");
        String email = in.next();
        if (isValid(email)) {
            return email;
        } else {
            System.out.println("email is invalid !");
            typeEmail(in);
        }
        return null;
    }

    private static boolean isValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
