package com.example.social.classes;

/**
 * Created by Maxim on 03.05.2017.
 */

public class Data {
    // Информация о текущем пользователе
    public static int accessLevel;
    public static int accessLevelMenu;

    public static final int InterviewerRole = 0;
    public static final int ControllerRole = 1;
    public static final int AdminRole = 2;

    public static String token;
    public static int userId;
    public static String login;
    public static String firstName;
    public static String lastName;
    public static String middleName;
    public static String role;

    public static int surveyId;

    public static Survey targetSurvey;


    public static void setAccessLevel() {
        switch (role) {
            case "Interviewer":
                accessLevel = InterviewerRole;
                break;
            case "Controller":
                accessLevel = ControllerRole;
                break;
            case "Admin":
                accessLevel = AdminRole;
                break;
            default:
                accessLevel = InterviewerRole;
                break;
        }
    }

    public static final String URL = "http://socialsurvey.azurewebsites.net/";
}
