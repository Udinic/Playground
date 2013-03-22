package com.udinic.accounts_auth_example;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 21/03/13
 * Time: 21:02
 */
public enum AuthTokenType {

    READ_ONLY("See list of tasks", "auth1 label:"),
    READ_WRITE("Add and manipulate task list", "auth2 label:");

    String authType;
    String label;
    AuthTokenType(String authType, String label) {
        this.label = label;
        this.authType = authType;
    }


}
