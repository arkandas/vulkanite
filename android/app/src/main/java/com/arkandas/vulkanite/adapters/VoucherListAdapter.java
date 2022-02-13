package com.arkandas.vulkanite.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.arkandas.vulkanite.R;
import com.arkandas.vulkanite.data.model.response.VoucherModel;

import java.io.InputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

public class VoucherListAdapter extends RecyclerView.Adapter<VoucherListAdapter.ViewHolder> {

    private static final String TAG = VoucherListAdapter.class.getSimpleName();
    private List<VoucherModel> voucherModelList;
    private Context context;

    public VoucherListAdapter(List<VoucherModel> voucherModelList, Context context) {
        this.context = context;
        this.voucherModelList = voucherModelList;
    }

    @NonNull
    @Override
    public VoucherListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_row, parent, false);
        return new VoucherListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherListAdapter.ViewHolder holder, int position) {


        if(voucherModelList.get(position).getRarity().equals("COMMON")){
            holder.voucherBackground.setBackgroundResource(R.color.common);
            holder.voucherTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
        }else if(voucherModelList.get(position).getRarity().equals("UNCOMMON")){
            holder.voucherBackground.setBackgroundResource(R.color.uncommon);
            holder.voucherTitle.setTextColor(ContextCompat.getColor(context, R.color.uncommon));
        }else if(voucherModelList.get(position).getRarity().equals("RARE")){
            holder.voucherBackground.setBackgroundResource(R.color.rare);
            holder.voucherTitle.setTextColor(ContextCompat.getColor(context, R.color.rare));
        }else if(voucherModelList.get(position).getRarity().equals("EPIC")){
            holder.voucherBackground.setBackgroundResource(R.color.epic);
            holder.voucherTitle.setTextColor(ContextCompat.getColor(context, R.color.epic));
        }else if(voucherModelList.get(position).getRarity().equals("LEGENDARY")){
            holder.voucherBackground.setBackgroundResource(R.color.legendary);
            holder.voucherTitle.setTextColor(ContextCompat.getColor(context, R.color.legendary));
        }

        holder.voucherAmount.setText("+" +voucherModelList.get(position).getAmount().toString() + " VUL");
        holder.voucherCode.setText(voucherModelList.get(position).getVoucherCode());
        holder.voucherDate.setText(dateParser(voucherModelList.get(position).getRedemptionDate()));
        holder.voucherTitle.setText(voucherModelList.get(position).getVoucherTitle());
        holder.voucherText.setText(voucherModelList.get(position).getVoucherDescription());
        new DownloadImageTask(holder.voucherImage).execute(voucherModelList.get(position).getVoucherImage());

    }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }


    public String dateParser(String date){
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        Instant instant = Instant.parse(date);
        return formatter.format( instant );
    }

    @Override
    public int getItemCount() {
        return voucherModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView voucherImage;
        private TextView voucherAmount;
        private TextView voucherTitle;
        private TextView voucherText;
        private TextView voucherDate;
        private TextView voucherCode;
        private ImageView voucherBackground;

        public ViewHolder(View itemView){
            super(itemView);
            voucherImage = (ImageView)itemView.findViewById(R.id.voucher_image);
            voucherAmount = (TextView)itemView.findViewById(R.id.voucher_amount);
            voucherText = (TextView)itemView.findViewById(R.id.voucher_text);
            voucherTitle = (TextView)itemView.findViewById(R.id.voucher_title);
            voucherDate = (TextView)itemView.findViewById(R.id.voucher_date);
            voucherCode = (TextView)itemView.findViewById(R.id.voucher_code);
            voucherBackground = (ImageView)itemView.findViewById(R.id.voucher_background);

        }

    }


}
