package com.udinic.general_testing;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by IntelliJ IDEA.
 * User: udic
 * Date: 27/03/12
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public class LoginLogIn extends Activity {
    private Spinner accountsSpinner;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_sign_in);


//        TextView title = (TextView) findViewById(R.id.title);
//        title.setTypeface(ThemeManager.getFont(ThemeAttribute.GENERAL_FONT_DIN_DISPLAY_LIGHT));

//        mPasswordEditText  = (EditText) findViewById(R.id.password);
//        mPasswordEditText.setTypeface(ThemeManager.getFont(ThemeAttribute.GENERAL_FONT_DIN_DISPLAY_LIGHT));

    }
}
