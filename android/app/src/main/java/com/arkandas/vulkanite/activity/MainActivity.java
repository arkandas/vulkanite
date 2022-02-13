package com.arkandas.vulkanite.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkandas.vulkanite.FragmentHome;
import com.arkandas.vulkanite.R;
import com.arkandas.vulkanite.SendFragment;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.arkandas.vulkanite.data.model.response.AccountInfo;
import com.arkandas.vulkanite.data.remote.APIService;
import com.arkandas.vulkanite.data.remote.APIUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private TextView coinQuantity;
    private ImageView displayQrCode;
    private ImageButton qrCodeImage;
    private Dialog dialog;
    private String userWallet = "";
    private ImageView imageButtonLogout;


    private APIService mAPIService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        qrCodeImage = findViewById(R.id.ir_qr_code);
        imageButtonLogout = findViewById(R.id.imageButtonLogout);
        mAPIService = APIUtils.getAPIService();
        displayQrCode = findViewById(R.id.dialog_qr_code_big);
        dialog = new Dialog(this);
        // Initialize the Scan Object
        getUserAccountInfo();

        qrCodeImage.setOnClickListener(v -> openInDialog());
        imageButtonLogout.setOnClickListener(v -> logout());

    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent goToLoginActivity = new Intent(this, LoginActivity.class);
        startActivity(goToLoginActivity);
        finish();
    }

    public void getUserAccountInfo(){
        mAPIService.getAccountInfo(UserPreferencesService.GetToken(getApplicationContext())).enqueue(new Callback<AccountInfo>(){

            @Override
            public void onResponse(Call<AccountInfo> call, Response<AccountInfo> response) {
                coinQuantity = findViewById(R.id.coinQuantity);
                coinQuantity.setText(response.body().getWalletBalance().toString() + " " + response.body().getWalletCurrencyType());
                userWallet = response.body().getWalletAddress();
                SharedPreferences sharedPref = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("wallet", userWallet);
                editor.putString("balance", response.body().getWalletBalance().toString());
                editor.putString("id", response.body().getUserId().toString());
                editor.commit();
            }

            @Override
            public void onFailure(Call<AccountInfo> call, Throwable t) {

            }
        });
    }

    private void openInDialog() {
        dialog.setContentView(R.layout.display_qr_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView dialogQrImageView = dialog.getWindow().findViewById(R.id.dialog_qr_code_big);
        TextView dialogWalletAddress = dialog.getWindow().findViewById(R.id.dialog_wallet_info);
        dialogWalletAddress.setText(userWallet);
        createAndDisplayQrCode(userWallet, dialogQrImageView);
        Button btnClose = dialog.findViewById(R.id.dialog_close_button);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public void createAndDisplayQrCode(String wallet, ImageView newImage){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "wallet");
        jsonObject.addProperty("wallet", wallet);

        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(jsonObject.toString(), BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            newImage.setImageBitmap(bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}

