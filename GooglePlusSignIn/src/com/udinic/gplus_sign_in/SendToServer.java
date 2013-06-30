package com.udinic.gplus_sign_in;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 26/06/13
 * Time: 11:51
 */
public class SendToServer extends Activity implements View.OnClickListener {
    private static final int REQUEST_AUTHORIZATION = 20;
    private static final int REQUEST_ACCOUNT_PICKER = 10;
    String accountName = "udinic.2testing@gmail.com";

    // Docs:
    // http://android-developers.blogspot.co.il/2012/09/google-play-services-and-oauth-identity.html
    // http://stackoverflow.com/questions/12689858/newly-released-authentication-with-google-play-services-problems-with-getting
    // http://android-developers.blogspot.co.il/2013/01/verifying-back-end-calls-from-android.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button signing = new Button(this);
        signing.setText("Sign in mothafucka!");
        signing.setOnClickListener(this);

        setContentView(signing);
    }

    @Override
    public void onClick(View v) {

        Bundle appActivities = new Bundle();
        appActivities.putString(GoogleAuthUtil.KEY_REQUEST_VISIBLE_ACTIVITIES,
                "com.udinic.gplus_sign_in.SendToServer");

        getCode();

    }

    private void getCode() {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                String serverClientIdUdi = "526672862249-k7qjcg4n88d59h2r5kdiklucs4oakcko.apps.googleusercontent.com";

                String serverClientIdAnydo = "543675644531-2teck7757en2bfn1dpaqs31931cb0ukd.apps.googleusercontent.com";
                String scopes = "https://www.googleapis.com/auth/plus.login";
                //                String scopes = "https://www.googleapis.com/auth/userinfo.email";
                String oauth2 = "oauth2:server:client_id:" + serverClientIdAnydo + ":api_scope:" + scopes;
                try {
                    String code = GoogleAuthUtil.getToken(
                            getBaseContext(),                             // Context context
                            accountName,     // String accountName
                            oauth2
                    );
                    Log.d("udini", "code = " + code);
                } catch (IOException transientEx) {
                    // network or server error, the call is expected to succeed if you try again later.
                    // Don't attempt to call again immediately - the request is likely to
                    // fail, you'll hit quotas or back-off.
                    Log.e("udini", "", transientEx);
                    return;
                } catch (UserRecoverableAuthException e) {
                    // Recover
                    startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);

                    Log.e("udini", "", e);
                } catch (GoogleAuthException authEx) {
                    // Failure. The call is not expected to ever succeed so it should not be
                    // retried.
                    Log.e("udini", "", authEx);
                    return;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        getCode();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == Activity.RESULT_OK) {

                    Log.d("udini", "onActivityResult> RESULT_OK");
                    getCode();

                } else {
                    Log.d("udini", "onActivityResult> FAILED:" + requestCode);
                }
                break;
        }
    }

}
