package com.udinic.glassplayground;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {
    private static final String TAG = "Glass";
    private static final String PARAM_AUTH_TOKEN =
            "com.example.mirror.android.AUTH_TOKEN";

    private static final int REQUEST_ACCOUNT_PICKER = 1;
    private static final int REQUEST_AUTHORIZATION = 2;

    private static final String GLASS_TIMELINE_SCOPE =
            "https://www.googleapis.com/auth/glass.timeline";
    private static final String GLASS_LOCATION_SCOPE =
            "https://www.googleapis.com/auth/glass.location";
    private static final String SCOPE = String.format("oauth2: %s %s",
            GLASS_TIMELINE_SCOPE, GLASS_LOCATION_SCOPE);

    private static ExecutorService sThreadPool =
            Executors.newSingleThreadExecutor();

    private final Handler mHandler = new Handler();

    private String mAuthToken;
    private Button mStartAuthButton;
    private Button mExpireTokenButton;
    private ImageButton mNewCardButton;
    private EditText mNewCardEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define our layout
        setContentView(R.layout.activity_main);

        mAuthToken = PreferenceManager.getDefaultSharedPreferences(this).getString("auth", null);

        // Get our views
        mStartAuthButton = (Button) findViewById(R.id.oauth_button);
        mExpireTokenButton = (Button) findViewById(R.id.oauth_expire_button);
        mNewCardButton = (ImageButton) findViewById(R.id.new_card_button);
        mNewCardEditText = (EditText) findViewById(R.id.new_card_message);

        // Restore any saved instance state
        if (savedInstanceState != null) {
            onTokenResult(savedInstanceState.getString(PARAM_AUTH_TOKEN));
        } else {
            mStartAuthButton.setEnabled(true);
            mExpireTokenButton.setEnabled(false);
        }

        mStartAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Present the user with an account picker dialog with a list
                // of their Google accounts
                Intent intent = AccountPicker.newChooseAccountIntent(
                        null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                        false, null, null, null, null);
                startActivityForResult(intent, REQUEST_ACCOUNT_PICKER);
            }
        });

        mExpireTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mAuthToken)) {
                    // Expire the token, if any
                    GoogleAuthUtil.invalidateToken(MainActivity.this, mAuthToken);
                    mAuthToken = null;
                    mExpireTokenButton.setEnabled(false);
                    mStartAuthButton.setEnabled(true);
                }
            }
        });

        mNewCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTimelineItem();
            }
        });
        findViewById(R.id.contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUdiniContact();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAM_AUTH_TOKEN, mAuthToken);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        switch (requestCode) {
            case REQUEST_ACCOUNT_PICKER:
                if (RESULT_OK == resultCode) {
                    String account = data.getStringExtra(
                            AccountManager.KEY_ACCOUNT_NAME);
                    String type = data.getStringExtra(
                            AccountManager.KEY_ACCOUNT_TYPE);

                    // TODO: Cache the chosen account
                    Log.i(TAG, String.format("User selected account %s of type %s",
                            account, type));
                    fetchTokenForAccount(account);
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (RESULT_OK == resultCode) {
                    String token = data.getStringExtra(
                            AccountManager.KEY_AUTHTOKEN);

                    Log.i(TAG, String.format(
                            "Authorization request returned token %s", token));
                    onTokenResult(token);
                }
                break;
        }
    }

    private JSONObject createJson(String name, String value) throws JSONException {
        JSONObject kind = new JSONObject();
        kind.put(name, value);
        return kind;
    }

    private void createUdiniContact() {
        try {
            JSONObject json = new JSONObject();
            json.put("kind", "mirror#contact");
            json.put("id", "5456");
            json.put("displayName", "Any.do");
            json.put("speakableName", "Any do");

            JSONObject acceptCommand1 = createJson("type", "TAKE_A_NOTE");
            JSONArray acceptCommands = new JSONArray();
            acceptCommands.put(acceptCommand1);
            json.put("acceptCommands", acceptCommands);

            JSONArray imageUrls = new JSONArray();
            imageUrls.put("http://www.any.do/images/anydo-logo.png");
            json.put("imageUrls", imageUrls);

            Log.d(TAG, "JSON object:\n"+ json.toString(4));

            MirrorApiClient client = MirrorApiClient.getInstance(this);
            client.createContactItem(mAuthToken, json, new MirrorApiClient.Callback() {
                @Override
                public void onSuccess(HttpResponse response) {
                    try {
                        Log.v(TAG, "onSuccess: " + EntityUtils.toString(response.getEntity()));
                    } catch (IOException e1) {
                        // Pass
                    }
                    Toast.makeText(MainActivity.this, "Created new contact item",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(HttpResponse response, Throwable e) {
                    try {
                        Log.v(TAG, "onFailure: " + EntityUtils.toString(response.getEntity()));
                    } catch (IOException e1) {
                        // Pass
                    }
                    Toast.makeText(MainActivity.this, "Failed to create new contact item",
                            Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void createNewTimelineItem() {
        if (!TextUtils.isEmpty(mAuthToken)) {
            String message = mNewCardEditText.getText().toString();
            if (!TextUtils.isEmpty(message)) {
                try {
                    JSONObject notification = new JSONObject();
                    notification.put("level", "DEFAULT"); // Play a chime

                    JSONObject menuItem1 = new JSONObject();
                    menuItem1.put("action", "SHARE");
                    JSONObject menuItem2 = new JSONObject();
                    menuItem2.put("action", "TOGGLE_PINNED");

                    JSONArray menuItemsArray = new JSONArray();
                    menuItemsArray.put(menuItem1);
                    menuItemsArray.put(menuItem2);

                    JSONObject json = new JSONObject();
//                    json.put("id", id);
//                    json.put("text", message);

                    String html = "<article>\n" +
                            "\t<figure>\n" +
                            "\t\t<h3>Any.do Reminder</h3>\n" +
                            "\t\t<h1>"+message+"</h1>\n" +
                            "\t\tvia <img src=\"http://www.any.do/images/anydo-logo.png\"/>\n" +
                            "\t</figure>\n" +
                            "</article>";

                    String html2 = "<article class=\"photo\">\n" +
                            "  <img src=\"https://dl.dropboxusercontent.com/u/12494794/anydo.png\" align=\"right\" width=\"90px\" height=\"90px\">\n  " +
                            "  <img src=\"https://dl.dropboxusercontent.com/u/12494794/bell.gif\" align=\"left\" >\n  " +
                            "<div class=\"photo-overlay\"></div>\n  " +
                            "<section>\n    " +
//                            "<p class=\"text-auto-size\">"+message+"</p>\n  " +
                            "<p class=\"text-small\">"+"Reminder from Any.do:"+"</p>\n  " +
                            "<p class=\"text-large\">"+"Pick up the kids after work"+"</p>\n  " +
                            "</section>\n" +
                            "</article>\n";

                    String html3 = "<article class=\"photo\">\n" +
                            "  <img src=\"https://dl.dropboxusercontent.com/u/12494794/anydo.png\" style=\"padding-top:10px; padding-right: 10px;\" align=\"right\" width=\"90px\" height=\"90px\">\n" +
                            "    <img src=\"https://dl.dropboxusercontent.com/u/12494794/bell.gif\" style=\"padding-top:10px; padding-left: 10px;\" align=\"left\" >\n" +
                            "  <div class=\"photo-overlay\"></div>\n" +
                            "  <section style=\"position: absolute; bottom: 0px; width: 100%; padding-top:0px; margin-top:0px;\">\n" +
                            "    <p class=\"text-small\" style=\"padding-top:0px; padding-bottom:15px; margin-top:0px;\" >Reminder from Any.do:</p>\n" +
                            "  \t<p class=\"text-large\">Pick up the kids after work</p>\n" +
                            "  </section>\n" +
                            "</article>";

                    String html4 = "<article class=\"photo\">\n" +
                            "  <img src=\"https://s3.amazonaws.com/static.any.do/glass/anydo.png\" style=\"padding-top:10px; padding-right: 20px;\" align=\"right\" width=\"90px\" height=\"90px\">\n" +
                            "    <img src=\"https://s3.amazonaws.com/static.any.do/glass/bell.gif\" style=\"padding-top:10px; padding-left: 20px;\" align=\"left\" >\n" +
                            "  <div class=\"photo-overlay\"></div>\n" +
                            "  <section style=\"position: absolute; bottom: 0px; width: 100%; padding-top:0px; margin-top:0px;\">\n" +
                            "    <p class=\"text-small\" style=\"padding-top:0px; padding-bottom:15px; margin-top:0px;\" >Reminder from Any.do:</p>\n" +
                            "  \t<p class=\"text-large\">Pick up the kids after work</p>\n" +
                            "  </section>\n" +
                            "</article>";


                    json.put("html", html4);

                    json.put("isPinned", true);
                    json.put("title", "TitleU");
                    json.put("speakableText", "Udini is the man");
                    json.put("notification", notification);
                    json.put("menuItems", menuItemsArray);

                    Log.d(TAG, "JSON object:\n"+ json.toString(4));

                    MirrorApiClient client = MirrorApiClient.getInstance(this);
                    client.createTimelineItem(mAuthToken, json, new MirrorApiClient.Callback() {
                        @Override
                        public void onSuccess(final HttpResponse response) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Log.v(TAG, "onSuccess: " + EntityUtils.toString(response.getEntity()));
                                        } catch (IOException e1) {
                                            // Pass
                                        }
                                    }
                                }).start();
                            Toast.makeText(MainActivity.this, "Created new timeline item",
                                    Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(HttpResponse response, Throwable e) {
                            try {
                                Log.v(TAG, "onFailure: " + EntityUtils.toString(response.getEntity()));
                            } catch (IOException e1) {
                                // Pass
                            }
                            Toast.makeText(MainActivity.this, "Failed to create new timeline item",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(this, "Sorry, can't serialize that to JSON",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Sorry, can't create an empty timeline item",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sorry, can't create a new timeline card without a token",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void onTokenResult(String token) {
        Log.d(TAG, "onTokenResult: " + token);
        if (!TextUtils.isEmpty(token)) {
            mAuthToken = token;
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("auth", mAuthToken).commit();
            mExpireTokenButton.setEnabled(true);
            mStartAuthButton.setEnabled(false);
            Toast.makeText(this, "New token result", Toast.LENGTH_SHORT).show();
        } else {
            mExpireTokenButton.setEnabled(false);
            mStartAuthButton.setEnabled(true);
            Toast.makeText(this, "Sorry, invalid token result", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchTokenForAccount(final String account) {
        // We fetch the token on a background thread otherwise Google Play
        // Services will throw an IllegalStateException
        sThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // If this returns immediately the OAuth framework thinks
                    // the token should be usable
                    final String token = GoogleAuthUtil.getToken(
                            MainActivity.this, account, SCOPE);

                    if (token != null) {
                        // Pass the token back to the UI thread
                        Log.i(TAG, String.format("getToken returned token %s", token));
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                onTokenResult(token);
                            }
                        });
                    }
                } catch (final UserRecoverableAuthException e) {
                    // This means that the app hasn't been authorized by the user for access
                    // to the scope, so we're going to have to fire off the (provided) Intent
                    // to arrange for that. But we only want to do this once. Multiple
                    // attempts probably mean the user said no.
                    Log.i(TAG, "Handling a UserRecoverableAuthException");

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
                        }
                    });
                } catch (IOException e) {
                    // Something is stressed out; the auth servers are by definition
                    // high-traffic and you can't count on 100% success. But it would be
                    // bad to retry instantly, so back off
                    Log.e(TAG, "Failed to fetch auth token!", e);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    "Failed to fetch token, try again later", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (GoogleAuthException e) {
                    // Can't recover from this!
                    Log.e(TAG, "Failed to fetch auth token!", e);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    "Failed to fetch token, can't recover", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
