package com.arkandas.vulkanite.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arkandas.vulkanite.R;

public class TransactionDetails extends AppCompatActivity {

    TextView transactionAmount;
    TextView fromWallet;
    TextView toWallet;
    TextView transactionType;
    TextView transactionDate;
    TextView transactionBalance;
    TextView transactionNote;
    TextView transactionId;
    ConstraintLayout transactionAmountLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        Intent intent = getIntent();
        transactionAmountLayout = findViewById(R.id.transactionAmountLayout);
        transactionId = findViewById(R.id.transactionIdText);
        transactionId.setText(intent.getStringExtra("Id"));
        transactionAmount = findViewById(R.id.transactionAmount);
        if(Integer.valueOf(intent.getStringExtra("Amount")) > 0 ) {
            transactionAmount.setText("+" + intent.getStringExtra("Amount") + " VUL");
        }else{
            transactionAmount.setText(intent.getStringExtra("Amount") + " VUL");

        }
        fromWallet = findViewById(R.id.fromWalletText);
        fromWallet.setText(intent.getStringExtra("Origin"));
        toWallet = findViewById(R.id.toWalletText);
        toWallet.setText(intent.getStringExtra("Destination"));
        transactionType = findViewById(R.id.transactionTypeText);
        String transactionTypeValue = intent.getStringExtra("Type");
        if(transactionTypeValue.equals("SENT")){
            transactionAmountLayout.setBackgroundColor(Color.parseColor("#e36387"));
        }else if(transactionTypeValue.equals("VOUCHER")){
            transactionAmountLayout.setBackgroundColor(Color.parseColor("#a6dcef"));
        }else if(transactionTypeValue.equals("RECEIVED")){
            transactionAmountLayout.setBackgroundColor(Color.parseColor("#98D7C2"));
        }
        transactionType.setText(transactionTypeValue);
        transactionDate = findViewById(R.id.transactionDateText);
        transactionDate.setText(intent.getStringExtra("Date"));
        transactionBalance = findViewById(R.id.transactionBalanceText);
        transactionBalance.setText(intent.getStringExtra("Balance"));
        transactionNote = findViewById(R.id.transactionNoteText);
        transactionNote.setText(intent.getStringExtra("Note"));

        ImageButton back = (ImageButton)findViewById(R.id.backToMain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }





    
}