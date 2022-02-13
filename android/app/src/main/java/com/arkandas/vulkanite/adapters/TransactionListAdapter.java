package com.arkandas.vulkanite.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.arkandas.vulkanite.R;
import com.arkandas.vulkanite.activity.TransactionDetails;
import com.arkandas.vulkanite.data.model.response.TransactionModel;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private static final String TAG = TransactionListAdapter.class.getSimpleName();
    private List<TransactionModel> transactionModelList;
    SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
    private Context context;

    public TransactionListAdapter(List<TransactionModel> transactionModelList, Context context){
        this.context = context;
        this.transactionModelList = transactionModelList;
    }

    @NonNull
    @Override
    public TransactionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_row, parent, false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListAdapter.ViewHolder holder, int position) {

        if(transactionModelList.get(position).getTransactionType().equals("SENT")) {
            holder.cardTransactionAmount.setBackground(ContextCompat.getDrawable(context, R.drawable.amount_bg_red));
            holder.cardTransactionType.setTextColor(Color.parseColor("#e36387"));

        }
        if(transactionModelList.get(position).getTransactionType().equals("VOUCHER")){
            holder.cardTransactionAmount.setBackground(ContextCompat.getDrawable(context, R.drawable.amount_bg_blue));
            holder.cardTransactionType.setTextColor(Color.parseColor("#a6dcef"));
        }
        if(transactionModelList.get(position).getTransactionType().equals("RECEIVED")){
            holder.cardTransactionAmount.setBackground(ContextCompat.getDrawable(context, R.drawable.amount_bg_green));
            holder.cardTransactionType.setTextColor(Color.parseColor("#98D7C2"));
        }
        holder.cardTransactionDate.setText(dateParser(transactionModelList.get(position).getTransactionDate()));
        holder.cardTransactionType.setText(transactionModelList.get(position).getTransactionType());
        holder.cardTransactionDescription.setText(transactionModelList.get(position).getDescription());
        if(transactionModelList.get(position).getAmount() > 0) {
            holder.cardTransactionAmount.setText("+" + transactionModelList.get(position).getAmount().toString() + " VUL");
        }else{
            holder.cardTransactionAmount.setText(transactionModelList.get(position).getAmount().toString() + " VUL");

        }
        holder.cardAccountBalance.setText(transactionModelList.get(position).getBalance().toString() + " VUL");

        holder.fullCardLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent goToTransactionDetails = new Intent(context.getApplicationContext(), TransactionDetails.class);

                goToTransactionDetails.putExtra("Id", String.valueOf(transactionModelList.get(position).getTransactionId()));
                goToTransactionDetails.putExtra("Amount", transactionModelList.get(position).getAmount().toString());
                goToTransactionDetails.putExtra("Balance", transactionModelList.get(position).getBalance().toString());
                goToTransactionDetails.putExtra("Description", transactionModelList.get(position).getDescription());
                goToTransactionDetails.putExtra("Date", dateParser(transactionModelList.get(position).getTransactionDate()));
                goToTransactionDetails.putExtra("Type", transactionModelList.get(position).getTransactionType());
                goToTransactionDetails.putExtra("Origin", transactionModelList.get(position).getWalletOrigin());
                goToTransactionDetails.putExtra("Destination", transactionModelList.get(position).getWalletDestination());
                goToTransactionDetails.putExtra("Note", transactionModelList.get(position).getNote());

                goToTransactionDetails.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.getApplicationContext().startActivity(goToTransactionDetails);
                // ((Activity)context).finish();
            }
        });


    }

    public String dateParser(String date){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        Instant instant = Instant.parse(date);
        return formatter.format( instant );
    }

    @Override
    public int getItemCount() {
        return transactionModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cardTransactionDate;
        private TextView cardTransactionType;
        private TextView cardTransactionAmount;
        private TextView cardAccountBalance;
        private TextView cardTransactionDescription;
        private ConstraintLayout fullCardLayout;

        public ViewHolder(View itemView){
            super(itemView);
            cardTransactionDate = (TextView)itemView.findViewById(R.id.card_transaction_date);
            cardTransactionType = (TextView)itemView.findViewById(R.id.card_transaction_type);
            cardTransactionAmount = (TextView)itemView.findViewById(R.id.card_transaction_amount);
            cardAccountBalance = (TextView)itemView.findViewById(R.id.card_account_balance);
            cardTransactionDescription = (TextView)itemView.findViewById(R.id.card_transaction_description);
            fullCardLayout = (ConstraintLayout)itemView.findViewById(R.id.fullCardLayout);

        }

    }


}
