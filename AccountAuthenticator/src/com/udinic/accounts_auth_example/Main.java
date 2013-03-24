package com.udinic.accounts_auth_example;

import android.accounts.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 21/03/13
 * Time: 13:50
 */
public class Main extends Activity {

    private String TAG = this.getClass().getSimpleName();
    private android.os.Handler mHandler = new android.os.Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        findViewById(R.id.request1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(Consts.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        });
        findViewById(R.id.request2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(Consts.AUTHTOKEN_TYPE_READ_ONLY);
            }
        });
    }

    private void request(String authTokenType) {

        AccountManager am = AccountManager.get(this);
        Account account = new Account("udinic.testingppp@gmail.com", Consts.ACCOUNT_TYPE);

//        try {
//            String auth = am.blockingGetAuthToken(account, Consts.AUTHTOKEN_TYPE, true);
//            Log.d("udini", TAG + "> " + auth);
//        } catch (OperationCanceledException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (AuthenticatorException e) {
//            e.printStackTrace();
//        }
        final AccountManagerFuture<Bundle> future = am.getAuthToken(account, authTokenType, null, this, null,null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //To change body of implemented methods use File | Settings | File Templates.
                            Toast.makeText(getBaseContext(), ((authtoken != null) ? "SUCCESS!" : "FAIL"), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d("udini", "Bundle is " + bnd);
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AuthenticatorException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
