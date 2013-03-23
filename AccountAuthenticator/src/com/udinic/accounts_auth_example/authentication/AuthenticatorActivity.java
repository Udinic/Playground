package com.udinic.accounts_auth_example.authentication;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.udinic.accounts_auth_example.R;

import static com.udinic.accounts_auth_example.authentication.ServerUtils.connect;

public class AuthenticatorActivity extends AccountAuthenticatorActivity {

    public final static String PARAM_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String PARAM_AUTH_TYPE = "AUTH_TYPE";
    private final String TAG = this.getClass().getSimpleName();

    private AccountManager mAccountManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ((TextView)findViewById(R.id.type)).setText(getIntent().getStringExtra(PARAM_ACCOUNT_TYPE));

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

        final String accountType = this.getIntent().getStringExtra(PARAM_ACCOUNT_TYPE);
        final String authType = this.getIntent().getStringExtra(PARAM_AUTH_TYPE);

//        if (accountType == null) {
//            Log.w("udini", TAG + "> No account type was found. Defaulting to " + Consts.ACCOUNT_TYPE1);
//            accountType = Consts.ACCOUNT_TYPE1;
//        }
//
//        final String finalAccountType = accountType;
        new AsyncTask<String, Void, Intent>() {

            @Override
            protected Intent doInBackground(String... params) {

                Log.d("udini", TAG + "> Started authenticating");

                String authtoken = connect(userName, userPass, authType, accountType);

                final Account account = new Account(userName, accountType);
                mAccountManager = AccountManager.get(getBaseContext());
                mAccountManager.addAccountExplicitly(account, userPass, null);

                final Intent res = new Intent();
                res.putExtra(AccountManager.KEY_ACCOUNT_NAME, userName);
                res.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                res.putExtra(AccountManager.KEY_AUTHTOKEN, authtoken);

                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                finishLogin(userName, userPass, accountType, intent);
            }
        }.execute();
    }

    private void finishLogin(String userName, String password, String accountType, Intent intent) {
        Log.d("udini", TAG + "> finishLogin");

        final Account account = new Account(userName, accountType);
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
