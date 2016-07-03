package com.orca.dotz.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.orca.dotz.R;

/**
 * LoginSignUpActivity for implementing login and signup functionality
 * by using firebase authentication
 *
 * @author Rahul will implement login and signup.
 */
public class LoginSignUpActivity extends AppCompatActivity {

    private EditText phone;
    private FloatingActionButton ok;
    private TextWatcher textWatcher;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        logo = (ImageView) findViewById(R.id.appIcon);
        phone = (EditText) findViewById(R.id.phone);
        addTextWatchertoPhoneEdittext();
        phone.addTextChangedListener(textWatcher);
        ok = (FloatingActionButton) findViewById(R.id.ok);
        disableOK();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginSignUpActivity.this, "Number Verification byPassed", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(LoginSignUpActivity.this, UserProfile.class);
                i.putExtra("phone", phone.getText().toString());
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(LoginSignUpActivity.this).load((R.drawable.logo)).centerCrop().into(logo);

    }

    private void addTextWatchertoPhoneEdittext() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10)
                    enableOK();
                else disableOK();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private void enableOK() {
        ok.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9C27B0")));
        ok.setEnabled(true);
    }

    private void disableOK() {
        ok.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        ok.setEnabled(false);
    }


}
