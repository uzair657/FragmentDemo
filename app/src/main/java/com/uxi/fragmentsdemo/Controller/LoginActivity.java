package com.uxi.fragmentsdemo.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uxi.fragmentsdemo.API.ApiClient;
import com.uxi.fragmentsdemo.HomeActivity;
import com.uxi.fragmentsdemo.Model.LoginRequest;
import com.uxi.fragmentsdemo.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email,pass;
    GoogleSignInClient mGoogleSignInClient;
    LoginButton loginFcaebook;
    CallbackManager callbackManager;
    int RC_SIGN_IN = 0;
    int FB_SIGN_IN = 0;
    private static final String EMAIL = "email";
    CircleImageView userPhoto;
    TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Mapping Variable:
        userPhoto = findViewById(R.id.userPhoto);
        Register = findViewById(R.id.register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                //finish();
            }
        });

        Picasso.get().load("https://orders.sleeplovers.ca/public/Order1630871179299.jpg").into(userPhoto);

        login = findViewById(R.id.Login);
        email = (EditText) findViewById(R.id.Email);
        pass = (EditText) findViewById(R.id.Password);

        // Set the dimensions of the sign-in button.
        Button signInButton = findViewById(R.id.sign_in_button);
        /*signInButton.setSize(SignInButton.SIZE_STANDARD);
*/
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;

                }
            }
        });


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //below this Code
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUserName(email.getText().toString().toLowerCase().trim());
                loginRequest.setEmail(email.getText().toString().toLowerCase().trim());
                loginRequest.setPassword( pass.getText().toString().toLowerCase().trim());
                AsyncLogin(loginRequest);
            }
        });
        // Facebook Login Code......##################################
        loginFcaebook = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        loginFcaebook.setPermissions(Arrays.asList("email"));
        //loginFcaebook.setPublishPermissions(Arrays.asList("email"));
        loginFcaebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Register Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "Register Error", Toast.LENGTH_SHORT).show();
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(LoginActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    }
                });
        AccessTokenTracker t = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
               if(currentAccessToken == null){
                   Toast.makeText(LoginActivity.this, "Hi I am Email"+"NULL and Now Logout", Toast.LENGTH_SHORT).show();
               }else{
                   loadUserProfile(currentAccessToken);
               }

            }
        };
    }

    private void AsyncLogin(LoginRequest loginRequest) {
        // Cal Api Client Service
        //Call<JsonObject> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        Call<JsonObject> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        // Response Call Enqueue
        loginResponseCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if(response.isSuccessful()){
                    Log.d("ResponseLogin:",response.body().toString());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this,"Welcome",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this,"UserName/Password Incorrect",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Check your Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserProfile(AccessToken accessToken) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        if(object !=null){
                            try{
                                String str = object.getString("email");
                                Toast.makeText(LoginActivity.this, "Email = "+str, Toast.LENGTH_SHORT).show();
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "email");
        request.setParameters(parameters);
        request.executeAsync();
    }
    /*
    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    */

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Toast.makeText(LoginActivity.this, "Google SigIn OnActivityResult = "+requestCode, Toast.LENGTH_SHORT).show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        if (requestCode == FB_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Toast.makeText(LoginActivity.this, "Facebook SigIn OnActivityResult = "+requestCode, Toast.LENGTH_SHORT).show();

        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
          Intent i = new Intent(LoginActivity.this,HomeActivity.class);
          startActivity(i);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }



}