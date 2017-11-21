package com.example.emyeraky.chatmessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Emy Eraky on 11/8/2017.
 */

public class Signin extends AppCompatActivity implements View.OnClickListener {
    private Button reg;
    private TextView tvLogin;
    private EditText etEmail, etPass, etName;
    FirebaseAuth myAuth;
    private ImageButton mPhotoPickerButton;
    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;

    private FirebaseStorage mFirebaseStorage;
    private StorageReference mChatPhotosStorageReference;

    Uri selectedImageUri;
    Uri downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        myAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();//based database
        mFirebaseStorage = FirebaseStorage.getInstance();//object from FirebaseStorage

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("users");//spacefic database reference and
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_profile");//reference to spacific location

        reg = (Button) findViewById(R.id.btn_create_account_final);

        tvLogin = (TextView) findViewById(R.id.tv_sign_in);

        etEmail = (EditText) findViewById(R.id.edit_text_email_create);
        etPass = (EditText) findViewById(R.id.edit_text_password_create);
        etName = (EditText) findViewById(R.id.edit_text_username_create);

        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);

        reg.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 2);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_account_final:
                register();
                break;
            case R.id.tv_sign_in:
                startActivity(new Intent(Signin.this, Login.class));
                finish();
                break;
            default:

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            selectedImageUri = data.getData();
            // Get a reference to store file at chat_photos/<FILENAME>
            StorageReference photoRef = mChatPhotosStorageReference.child(selectedImageUri.getLastPathSegment());
            // Upload file to Firebase Storage
            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {//taskSnapshot to get url of file for just sent to storage
                            // When the image has successfully uploaded, we get its download URL
                            downloadUrl = taskSnapshot.getDownloadUrl();
                            // Set the download URL to the message box, so that the user can send it to the database
                            User n = new User();
                            n.setPhoto(downloadUrl.toString());

                        }
                    });
        }
    }

    private void register() {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String name = etName.getText().toString();

        if (name.isEmpty() && email.isEmpty() && pass.isEmpty() && downloadUrl.toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Message_when_EmptyUserName_And_Password", Toast.LENGTH_SHORT).show();
        } else {
            registerFirebase(name, email, pass, downloadUrl.toString());
        }
    }

    private void registerFirebase(final String name, final String email, final String pass, final String photo) {
        myAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signin.this, "Message_success_Login", Toast.LENGTH_SHORT).show();
                    addDataToFirebase(name, email, photo);
                } else {
                    if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                        Toast.makeText(Signin.this, "Message_If_Signin_before", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Signin.this, "Message_If_failLoad_from_FireBase", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addDataToFirebase(String name, String email, String photo) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("users");

        User user = new User(name, email, photo);

        posts.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signin.this, "CorrectSignin", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Signin.this, "Message_Fail_ToAdd_ToFireBase", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
