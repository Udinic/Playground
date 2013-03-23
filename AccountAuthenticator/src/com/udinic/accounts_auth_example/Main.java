package com.udinic.accounts_auth_example;

import android.accounts.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
        findViewById(R.id.request1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(Consts.ACCOUNT_TYPE1);
            }
        });
        findViewById(R.id.request2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(Consts.ACCOUNT_TYPE1);
            }
        });
    }

    private void request(String accountType) {

        AccountManager am = AccountManager.get(this);

        Account account = new Account("udinic.testingppp@gmail.com", accountType);

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

        final AccountManagerFuture<Bundle> future = am.getAuthToken(account, Consts.AUTHTOKEN_TYPE_FULL_ACCESS, null, this, null,null);
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
