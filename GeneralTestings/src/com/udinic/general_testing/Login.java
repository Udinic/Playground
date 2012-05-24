/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package com.udinic.general_testing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class Login extends Activity {
  private VideoView video;
    private View replay;
  
  @Override
  public void onCreate(Bundle icicle) {

      getWindow().requestFeature(Window.FEATURE_NO_TITLE);

      super.onCreate(icicle);
      getWindow().setFormat(PixelFormat.TRANSLUCENT);
      setContentView(R.layout.act_login);

      String uri = "android.resource://" + getPackageName() + "/" + R.raw.test;

      replay = findViewById(R.id.replay);
      replay.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              video.seekTo(0);
              video.start();
              Animation fade = AnimationUtils.loadAnimation(Login.this, android.R.anim.fade_out);
              fade.setAnimationListener(new Animation.AnimationListener() {
                  public void onAnimationEnd(Animation animation) {
                      replay.setVisibility(View.GONE);
                  }

                  public void onAnimationStart(Animation animation) {
                  }

                  public void onAnimationRepeat(Animation animation) {
                  }
              });
              v.startAnimation(fade);
          }
      });

      Button btnLogin = (Button) findViewById(R.id.login);
      btnLogin.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              startActivity(new Intent(Login.this, LoginLogIn.class));
          }
      });

      video = (VideoView) findViewById(R.id.video);
      video.setVideoURI(Uri.parse(uri));
      video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
          public void onPrepared(MediaPlayer mp) {
              mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  public void onCompletion(MediaPlayer mp) {
                      Animation fade = AnimationUtils.loadAnimation(Login.this, android.R.anim.fade_in);
                      fade.setAnimationListener(new Animation.AnimationListener() {
                          public void onAnimationEnd(Animation animation) {
                          }

                          public void onAnimationStart(Animation animation) {
                              replay.setVisibility(View.VISIBLE);
                          }

                          public void onAnimationRepeat(Animation animation) {
                          }
                      });
                      replay.startAnimation(fade);
                  }
              });
          }
      });


  }

    @Override
    protected void onStart() {
        super.onStart();    //To change body of overridden methods use File | Settings | File Templates.
          video.requestFocus();

        // Initializes the replay button (w/o the animation it doesn't really work :-/ )
        Animation fade = AnimationUtils.loadAnimation(Login.this, android.R.anim.fade_out);
        fade.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                replay.setVisibility(View.GONE);
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        fade.setDuration(0);
        replay.startAnimation(fade);

        video.seekTo(0);
          video.start();
    }

}