package com.orca.dotz.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.orca.dotz.R;
import com.orca.dotz.utils.Constants;

/**
 * A Welcome (launcher) activity containing a splash screen with the timeout <b>SPLASH_SCREEN_TIMEOUT</b>
 * {@link com.orca.dotz.utils.Constants} using the {@link android.os.Handler}.
 * <p>This class also check whether any user is currently logged in or not.</p>
 */
public class WelcomeActivity extends AppCompatActivity {

    private boolean userLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(Constants.SPLASH_DONE, Constants.SPLASH_SCREEN_TIMEOUT);


    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.SPLASH_DONE:
                    splashDone();
                    break;

            }
        }
    };

    private void splashDone() {
        if (isUserLoggedIn()) {
            Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(WelcomeActivity.this, LoginSignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean isUserLoggedIn() {
        /**
         * Rahul will add a method to check whether the user is currently logged in.
         */
        return userLoggedIn;
    }
}
