package com.udinic.accounts_auth_example;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.udinic.accounts_auth_example.ServerUtils.connect;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {

    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void submit() {

        final String userName = ((TextView) findViewById(R.id.userName)).getText().toString();
        final String userPass = ((TextView) findViewById(R.id.userPass)).getText().toString();

        String accountType = this.getIntent().getStringExtra(Consts.AUTHTOKEN_TYPE);
        if (accountType == null) {
            accountType = Consts.ACCOUNT_TYPE;
        }

        final String finalAccountType = accountType;
        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                Log.d("udini", TAG + "> Started authenticating");

                String authtoken = connect(userName, userPass);

                final Account account = new Account(userName, finalAccountType);
                mAccountManager = AccountManager.get(getBaseContext());
                mAccountManager.addAccountExplicitly(account, userPass, null);

                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, finalAccountType);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);

                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(userName, userPass, intent);
            }
        }.execute();
    }

    private void finishLogin(String userName, String password, Intent intent) {
        Log.d("udini", TAG + "> finishLogin");

        final Account account = new Account(userName, Consts.ACCOUNT_TYPE);
//        if (mRequestNewAccount) {
        mAccountManager.addAccountExplicitly(account, password, null);
            // Set contacts sync for this account.
//        } else {
//            mAccountManager.setPassword(account, mPassword);
//        }
//        final Intent intent = new Intent();
//        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
//        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Constants.ACCOUNT_TYPE);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

}
