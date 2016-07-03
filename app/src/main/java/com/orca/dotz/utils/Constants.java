package com.orca.dotz.utils;

/** Constants store most important strings and paths of the app.
 * @author Amit Kumar
 */
public class Constants {

    public static final String PACKAGE_NAME = "com.orca.dotz";

    /**
     * Constants related to Splash screen
     */
    public static final int SPLASH_SCREEN_TIMEOUT = 300; // timeout for splash screen in ms.
    public static final int SPLASH_DONE = 1;


    /**
     * Constants related to locations in Firebase, such as the name of the node
     * where user lists are stored (ie "userLists")
     */
    public static final String FIREBASE_LOCATION_HAIR_STYLE_DATA = "data";
    public static final String FIREBASE_SALON_DATA = "salonData";
    public static final String FIREBASE_LOCATION_USER_LISTS = "userLists";

    /**
     * Constants for bundles, extras and shared preferences keys
     */
    public static final int TYPE_UNISEX = 0;
    public static final int TYPE_MALE = 1;
    public static final int TYPE_FEMALE = 2;

    public static final String TYPE_UNISEX_STRING = "Unisex";
    public static final String TYPE_MALE_STRING = "Male";
    public static final String TYPE_FEMALE_STRING = "Female";


    /**
     * Returns a human readable String corresponding to a detected activity type.
     */
    public static String getStyleType(int type){
        switch (type){
            case TYPE_UNISEX:
                return TYPE_UNISEX_STRING;
            case TYPE_MALE:
                return TYPE_MALE_STRING;
            case TYPE_FEMALE:
                return TYPE_FEMALE_STRING;
            default:
                return TYPE_UNISEX_STRING;
        }
    }

}
