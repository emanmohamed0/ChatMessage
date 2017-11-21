package com.example.emyeraky.chatmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Emy Eraky on 11/8/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {
    EditText EmailInput, PasswordInput;

    Button login;
    static String temail, tpass;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login =(Button)findViewById(R.id.login_with_password);
        EmailInput = (EditText) findViewById(R.id.edit_text_email);
        PasswordInput = (EditText) findViewById(R.id.edit_text_password);


        myAuth = FirebaseAuth.getInstance();

        if (myAuth.getCurrentUser() == null) {
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

       login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_with_password:
                login();
                break;


            default:
        }
    }
    public void onSignUpPressed(View view) {
        Intent intent = new Intent(Login.this, Signin.class);
        startActivity(intent);
    }

    private void login() {
        temail = EmailInput.getText().toString();
        tpass = PasswordInput.getText().toString();
        if (temail != null && tpass != null) {
            myAuth.signInWithEmailAndPassword(temail, tpass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "SuccefulLogIn", Toast.LENGTH_SHORT).show();
                        Intent ii = new Intent(Login.this, MainActivity.class);
                        ii.putExtra("name", EmailInput.getText().toString());
                        startActivity(ii);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "unSuccefulLogin", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("Login", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
