package com.example.attendancesystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.attendancesystem.databinding.LoginBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

public class  LoginPage extends AppCompatActivity {
    LoginBinding binding;
    private Handler mHandler = new Handler();
    EditText usernameText;
    EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=LoginBinding.inflate(getLayoutInflater());

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(binding.getRoot());
        usernameText= (EditText) findViewById(R.id.usernameEditText);
        passwordText = (EditText)findViewById(R.id.passwordEditText);
        Spinner userTypeSpinner=(Spinner) findViewById(R.id.userTypeSpinner);

        Button loginButton=(Button) findViewById(R.id.loginButton);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Reached ",userTypeSpinner.getItemAtPosition(0).toString());

                String usertype = userTypeSpinner.getSelectedItem().toString();

                if(usertype.equals("Administrator")){
                    isUser("Administrator");
//                    Intent adminIntent=new Intent(LoginPage.this,AdminPage.class);
//                    startActivity(adminIntent);
                }
                if(usertype.equals("Student")){
                    isUser("Students");
                    //Intent studentIntent=new Intent(LoginPage.this,StudentPage.class);
                    //startActivity(studentIntent);
                }
            }
        });

    }

    private void isUser(String usertype){
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        try {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(usertype);

            Query checkUser;
            if(usertype.equals("Administrator")){
                checkUser = reference.orderByChild("adminID").equalTo(username);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Log.d("snapshot", snapshot.toString());
                            String passwordDB = snapshot.child(username).child("password").getValue(String.class);

                            if(passwordDB.equals(password)){
                                TastyToast.makeText(getApplicationContext(), "Logged in successfully!",
                                        TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                mHandler.postDelayed(toastDelay,1500);
                                String name = snapshot.child(username).child("name").getValue(String.class);

                                //send username and name
                                Intent adminIntent=new Intent(LoginPage.this,AdminPage.class);

                                adminIntent.putExtra("name", name);
                                adminIntent.putExtra("adminID",username);
                                startActivity(adminIntent);
                            }else{
                                TastyToast.makeText(getApplicationContext(), "Check Password!",
                                        TastyToast.LENGTH_LONG,
                                        TastyToast.ERROR);
                                mHandler.postDelayed(toastDelay,1500);
                            }
                        }else{
                            TastyToast.makeText(getApplicationContext(), "User Doesnt exists",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR);
                            mHandler.postDelayed(toastDelay,1500);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(), "Try again",
                                TastyToast.LENGTH_LONG,
                                TastyToast.ERROR);
                        mHandler.postDelayed(toastDelay,1500);
                    }
                });
            }
            else if(usertype.equals("Students")){
                checkUser = reference.orderByChild("regno").equalTo(username);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Log.d("snapshot", snapshot.toString());
                            String passwordDB = snapshot.child(username).child("password").getValue(String.class);

                            if(passwordDB.equals(password)){
                                TastyToast.makeText(getApplicationContext(), "Logged in successfully!",
                                        TastyToast.LENGTH_LONG,
                                        TastyToast.SUCCESS);
                                mHandler.postDelayed(toastDelay,1500);
                                String name = snapshot.child(username).child("name").getValue(String.class);

                                //send username and name
                                Intent studentIntent=new Intent(LoginPage.this,StudentPage.class);

                                studentIntent.putExtra("name", name);
                                studentIntent.putExtra("regno",username);
                                startActivity(studentIntent);
                            }else{
                                TastyToast.makeText(getApplicationContext(), "Check Password!",
                                        TastyToast.LENGTH_LONG,
                                        TastyToast.ERROR);
                                mHandler.postDelayed(toastDelay,1500);
                            }
                        }else{
                            TastyToast.makeText(getApplicationContext(), "User Doesn't exists",
                                    TastyToast.LENGTH_LONG,
                                    TastyToast.ERROR);
                            mHandler.postDelayed(toastDelay,1500);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        TastyToast.makeText(getApplicationContext(), "Try again",
                                TastyToast.LENGTH_LONG,
                                TastyToast.ERROR);
                        mHandler.postDelayed(toastDelay,1500);
                    }
                });
            }else if(usertype.equals("Teacher")){
                checkUser = reference.orderByChild("teachId").equalTo(username);
            }else{
                throw new Exception();
            }

        }catch(Exception e){
            TastyToast.makeText(getApplicationContext(), "Internal Server Error !",
                    TastyToast.LENGTH_LONG,
                    TastyToast.ERROR);
            mHandler.postDelayed(toastDelay,1500);
        }

    }

    private Runnable toastDelay=new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}