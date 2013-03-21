package com.udinic.accounts_auth_example;

import android.accounts.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.logging.Handler;

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
        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                }).start();
            }
        });

    }

    private void request() {

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

        final AccountManagerFuture<Bundle> future = am.getAuthToken(account, Consts.AUTHTOKEN_TYPE, null, this, null,null);
//        final AccountManagerFuture<Bundle> future = am.getAuthToken(account, Consts.AUTHTOKEN_TYPE, null, this, new AccountManagerCallback<Bundle>() {
//            @Override
//            public void run(AccountManagerFuture<Bundle> future) {
//
//                Log.d("udini", TAG + "> in AccountManagerCallback");
//                try {
//                    Bundle bundle = future.getResult();
//                    Log.d("udini", TAG + "> got the results");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, mHandler);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();
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
