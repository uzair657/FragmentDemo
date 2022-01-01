package com.uxi.fragmentsdemo.Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uxi.fragmentsdemo.API.ApiClient;
import com.uxi.fragmentsdemo.Model.ResponseSignUp;
import com.uxi.fragmentsdemo.Model.SignUpDetails;
import com.uxi.fragmentsdemo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    EditText UserName,Email,Password,Phone;
    Button SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SignUp = findViewById(R.id.SignUp);
        UserName = findViewById(R.id.UserName);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Phone = findViewById(R.id.PhoneNumber);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveUser();
            }
        });
    }

    private void SaveUser() {
        ResponseSignUp signUp = new ResponseSignUp();
        signUp.UserName = UserName.getText().toString().toLowerCase().trim();
        signUp.Phone = Phone.getText().toString().trim();
        signUp.Email = Email.getText().toString().toLowerCase().trim();
        signUp.Password = Password.getText().toString().toLowerCase().trim();

        Call<SignUpDetails> registerResponseCall =  ApiClient.getService().SaveOrder(signUp);
        registerResponseCall.enqueue(new Callback<SignUpDetails>() {
            @Override
            public void onResponse(Call<SignUpDetails> call, Response<SignUpDetails> response) {
                ResetSignUpForm();
                Toast.makeText(ProfileActivity.this,"Successfully to Register",Toast.LENGTH_LONG).show();
                //startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                //finish();
            }
            @Override
            public void onFailure(Call<SignUpDetails> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"Fail to Register",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ResetSignUpForm() {
        UserName.setText("");
        Email.setText("");
        Password.setText("");
        Phone.setText("");
    }
}