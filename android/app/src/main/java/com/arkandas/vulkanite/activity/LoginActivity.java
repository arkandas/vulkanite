package com.arkandas.vulkanite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arkandas.vulkanite.BuildConfig;
import com.arkandas.vulkanite.R;
import com.arkandas.vulkanite.data.model.response.LoginResponse;
import com.arkandas.vulkanite.data.remote.APIService;
import com.arkandas.vulkanite.data.remote.APIUtils;
import com.arkandas.vulkanite.data.util.PasswordHash;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private APIService mAPIService;
    ProgressDialog progressDialog;

    TextView appVersionName;
    EditText userNameInput;
    EditText userPasswordInput;
    Button submitLoginButton;
    TextView registerTextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appVersionName = findViewById(R.id.appVersion);
        appVersionName.setText(BuildConfig.VERSION_NAME);

        userNameInput = findViewById(R.id.editTextUserName);
        userPasswordInput = findViewById(R.id.editTextUserPassword);
        submitLoginButton = findViewById(R.id.loginButton);

        mAPIService = APIUtils.getAPIService();

        registerTextButton = findViewById(R.id.registerTextButton);
        String textRegister = "Don't have an account? Register now";
        SpannableString ss = new SpannableString(textRegister);
        ForegroundColorSpan fcsCopper = new ForegroundColorSpan(Color.parseColor("#a96b30"));
        ss.setSpan(fcsCopper, 23, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerTextButton.setText(ss);

        LoginWithSharedPreferences();

        registerTextButton.setOnClickListener(v -> goToRegisterActivity());

        submitLoginButton.setOnClickListener(v -> {
        UserPreferencesService.hideKeyboard(LoginActivity.this);
        String userNameLogin = userNameInput.getText().toString().trim();
            String userPasswordLogin = PasswordHash.sha512PasswordEncoder(userPasswordInput.getText().toString().trim());
            Log.i(TAG, userPasswordLogin);

            if(validateLogin(userNameLogin, userPasswordLogin)){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("username", userNameLogin);
                jsonObject.addProperty("password", userPasswordLogin);
                doLogin(jsonObject);
            }
        });
    }


    public void LoginWithSharedPreferences(){
        SharedPreferences sharedPref = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        Log.d(TAG, "username: " + sharedPref.getString("username", null));
        Log.d(TAG, "password: " + sharedPref.getString("password", null));
        if((sharedPref.getString("username", null) != null) &&  (sharedPref.getString("password", null) !=null)){
            // Create json Object to send login info to the backend library
            JsonObject jsonOb = new JsonObject();
            jsonOb.addProperty("username",sharedPref.getString("username", null));
            jsonOb.addProperty("password",sharedPref.getString("password", null));
            doLogin(jsonOb);
        }
    }


    private Boolean validateLogin(String username, String password){
        if(username == null || username.length() == 0 ) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null ||  password.length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void doLogin(final JsonObject jsonObject){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Trying to log in");
        progressDialog.show();

        mAPIService.getUserToken(jsonObject).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG, String.valueOf(response.code()));
                if(response.isSuccessful()){
                    SharedPreferences sharedPref = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putString("token", "Bearer " + response.body().getToken());
                    editor.putString("username",jsonObject.get("username").getAsString());
                    editor.putString("password", jsonObject.get("password").getAsString());
                    editor.commit();

                    editor.commit();
                    progressDialog.dismiss();
                    goToMainActivity();
                }else{
                    Toast toast = Toast.makeText(LoginActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT);
                    toast.show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast toast = Toast.makeText(LoginActivity.this, "Unable to login, check connection", Toast.LENGTH_SHORT);
                toast.show();
                Log.i(TAG,call.request().url().toString());

                progressDialog.dismiss();
            }
        });
    }

    public void goToMainActivity() {
        Intent goToMainActivity = new Intent(this, MainActivity.class);
        startActivity(goToMainActivity);
        finish();
    }

    public void goToRegisterActivity(){
        Intent goToRegisterActivity = new Intent(this, RegisterActivity.class);
        startActivity(goToRegisterActivity);
    }



}