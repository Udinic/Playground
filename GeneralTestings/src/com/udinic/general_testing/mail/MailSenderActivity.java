package com.udinic.general_testing.mail;

/**
 * Created with IntelliJ IDEA.
 * User: Udic
 * Date: 09/05/12
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.udinic.general_testing.R;

public class MailSenderActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        final Button send = (Button) this.findViewById(R.id.send);
//        send.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                // TODO Auto-generated method stub

        Log.i("SendMail", "Sending email");
                try {
                    GMailSender sender = new GMailSender("udinic.testing@gmail.com", "udipubl1c");
                    sender.sendMail("Log cat",
                            "blablabla",
                            "user@gmail.com",
                            "udic@any.do");
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }

//            }
//        });

    }
}

