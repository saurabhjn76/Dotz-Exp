package com.orca.dotz.exception;

/**
 * Created by master on 12/6/16.
 */
public class Exceptions {

    //Auth related Exceptions
    public static final String ERROR_AUTH_USER_DISABLED = "NA";
    public static final String ERROR_AUTH_USER_NOT_FOUND = "There is no user record corresponding to this identifier. The user may have been deleted.";
    public static final String ERROR_AUTH_REPEATED_INVALID_LOGIN = "We have blocked all requests from this device due to unusual activity. Try again later.";
    public static final String ERROR_AUTH_EMAIL_ALREADY_USED = "The email address is already in use by another account.";
    public static final String ERROR_AUTH_USER_TOKEN_EXPIRED = "NA";
    public static final String ERROR_AUTH_PASSWORD_INVALID = "The password is invalid or the user does not have a password.";
    public static final String ERROR_AUTH_INVALID_USER_TOKEN = "NA";
    public static final String ERROR_AUTH_UNCLASSIFIED = "Unknown error";


    //Corresponding Exception messages to be shown to the user
    public static final String MESSAGE_USER_DISABLED = "Sorry your account is blocked";
    public static final String MESSAGE_USER_NOT_FOUND = "Email not found in our database";
    public static final String MESSAGE_REPEATED_INVALID_LOGIN = "Try again after some time";
    public static final String MESSAGE_EMAIL_ALREADY_USED = "The email address is already in use. Please try a different one";
    public static final String MESSAGE_USER_TOKEN_EXPIRED = "Login again";
    public static final String MESSAGE_PASSWORD_INVALID = "The email or password is invalid";
    public static final String MESSAGE_INVALID_USER_TOKEN = "NA";
    public static final String MESSAGE_UNCLASSIFIED = "Something went wrong! Please try again later";

}
