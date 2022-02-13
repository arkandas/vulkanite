package com.arkandas.vulkanite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.arkandas.vulkanite.R;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.arkandas.vulkanite.data.model.request.RegisterTransaction;
import com.arkandas.vulkanite.data.remote.APIService;
import com.arkandas.vulkanite.data.remote.APIUtils;
import com.arkandas.vulkanite.data.util.PasswordHash;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    EditText registerUsernameTextInput;
    EditText registerFullNameTextInput;
    EditText registerEmailTextInput;
    EditText registerTextUserPassword;
    EditText registerTextUserPasswordConfirm;
    Button registerButton;
    ProgressDialog progressDialog;
    private APIService mAPIService;

    private static final String TAG = RegisterActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsernameTextInput = findViewById(R.id.registerUsernameTextInput);
        registerFullNameTextInput = findViewById(R.id.registerFullNameTextInput);
        registerEmailTextInput = findViewById(R.id.registerEmailTextInput);
        registerTextUserPassword = findViewById(R.id.registerTextUserPassword);
        registerTextUserPasswordConfirm = findViewById(R.id.registerTextUserPasswordConfirm);
        registerButton = findViewById(R.id.registerButton);
        mAPIService = APIUtils.getAPIService();


        registerButton.setOnClickListener(v -> {

            if (checkRegisterFieldValidity()) {
                RegisterTransaction registerUserTransaction = new RegisterTransaction();
                registerUserTransaction.setEmail(registerEmailTextInput.getText().toString().trim());
                registerUserTransaction.setFullname(registerFullNameTextInput.getText().toString().trim());
                registerUserTransaction.setUsername(registerUsernameTextInput.getText().toString().trim());
                registerUserTransaction.setPassword(PasswordHash.sha512PasswordEncoder(registerTextUserPassword.getText().toString().trim()));
                registerNewUser(registerUserTransaction);
            }

        });

        ImageButton back = (ImageButton)findViewById(R.id.backToMain);
        back.setOnClickListener(v -> onBackPressed());
    }

    private boolean checkRegisterFieldValidity() {

        Boolean sendTransaction = true;
//        Reset errors
        registerUsernameTextInput.setError(null);
        registerFullNameTextInput.setError(null);
        registerEmailTextInput.setError(null);
        registerTextUserPassword.setError(null);
        registerTextUserPasswordConfirm.setError(null);

        if(registerUsernameTextInput.getText().toString().equals("")){
            registerUsernameTextInput.setError("Username cannot be empty");
            sendTransaction = false;
        }
        if(registerFullNameTextInput.getText().toString().equals("")){
            registerFullNameTextInput.setError("User cannot be empty");
            sendTransaction = false;
        }
        if(registerEmailTextInput.getText().toString().equals("")){
            registerEmailTextInput.setError("Email cannot be empty");
            sendTransaction = false;
        }
        if(registerTextUserPassword.getText().toString().equals("")){
            registerTextUserPassword.setError("Password cannot be empty");
            sendTransaction = false;
        }
//        Check that password and verification are equal
        if(!registerTextUserPassword.getText().toString().equals(registerTextUserPasswordConfirm.getText().toString())){
            registerTextUserPasswordConfirm.setError("Invalid password verification");
            sendTransaction = false;
        }
        return sendTransaction;
    }

    public void registerNewUser(RegisterTransaction registerUserTransaction){
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Trying to register new account");
        progressDialog.show();

        mAPIService.registerUser(registerUserTransaction).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e(TAG, String.valueOf(response.code()));
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    gotoLoginActivity();
                }else{
                    registerUsernameTextInput.setError("Username already taken");
                    Toast toast = Toast.makeText(RegisterActivity.this, "Unable to create account", Toast.LENGTH_SHORT);
                    toast.show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(RegisterActivity.this, "Unable to create account, check connection", Toast.LENGTH_SHORT);
                toast.show();
                Log.i(TAG,call.request().url().toString());

                progressDialog.dismiss();
            }
        });
    }

    private void gotoLoginActivity() {
        Intent goToLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(goToLoginActivity);
        finish();
    }


}