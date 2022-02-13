package com.arkandas.vulkanite;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arkandas.vulkanite.activity.MainActivity;
import com.arkandas.vulkanite.adapters.VoucherListAdapter;
import com.arkandas.vulkanite.data.model.response.VoucherModel;
import com.arkandas.vulkanite.data.remote.APIService;
import com.arkandas.vulkanite.data.remote.APIUtils;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoucherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoucherFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Dialog dialog;
    private APIService mAPIService;
    private RecyclerView recyclerViewVouchers;
    private VoucherListAdapter adapter;
    private ArrayList<VoucherModel> data = new ArrayList<VoucherModel>();
    private TextView emptyView;
    private static final String TAG = VoucherFragment.class.getSimpleName();
    private FloatingActionButton qrCodeScanVoucher;


    public VoucherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VoucherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VoucherFragment newInstance(String param1, String param2) {
        VoucherFragment fragment = new VoucherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAPIService = APIUtils.getAPIService();
        dialog = new Dialog(getActivity());

        getVouchers(true);
    }


    public void getVouchers(Boolean dialog){
        if(dialog) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Retrieving vouchers");
            progressDialog.show();
        }
        mAPIService.getVouchers(UserPreferencesService.GetToken(getActivity().getApplicationContext())).enqueue(new Callback<List<VoucherModel>>(){

            @Override
            public void onResponse(Call<List<VoucherModel>> call, Response<List<VoucherModel>> response) {

                Log.i(TAG, response.message());

                if (response.isSuccessful()){
                    recyclerViewVouchers.setHasFixedSize(true);
                    recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    data.clear();
                    if(response.body().size() > 0){
                        recyclerViewVouchers.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        for(int i = 0; i< response.body().size();i++) {
                            data.add(response.body().get(i));
                        }
                    }else{
                        recyclerViewVouchers.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    adapter = new VoucherListAdapter(data, getContext());
                    recyclerViewVouchers.setAdapter(adapter);
                    swipeContainer.setRefreshing(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<VoucherModel>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                progressDialog.dismiss();
            }
        });
    }

    private void startQrScanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(VoucherFragment.this);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan QR code");
        integrator.setBeepEnabled(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {

            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Error reading Qr code. Try again.", Toast.LENGTH_LONG).show();
            } else {
                //QR Code contains some data
                try {
                    //Convert the QR Code Data to JSON
                    JSONObject obj = new JSONObject(result.getContents());
                    //Set up the TextView values using the data from JSON
                    if(obj.getString("type").equals("voucher")) {
                        useCoupon(obj.getString("code"));
                    }else{
                        Toast.makeText(getContext(), "Incorrect voucher format", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //In case of exception, display whatever data is available on the QR Code
                    //This can be caused due to the format mismatch of the JSON
                    Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void useCoupon(String voucherCode){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Voucher Exchange");
        progressDialog.show();

        mAPIService.useVoucher(UserPreferencesService.GetToken(getActivity().getApplicationContext()), voucherCode).enqueue(new Callback<Void>(){

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.i(TAG, response.message());

                if(response.isSuccessful()){
                    openSuccessDialog(voucherCode);
                    getVouchers(false);
                }else{
                    Toast.makeText(getContext(), "Invalid voucher", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error retrieving voucher", Toast.LENGTH_LONG).show();
                swipeContainer.setRefreshing(false);
                progressDialog.dismiss();
            }
        });
    }

    private void openSuccessDialog(String message) {
        dialog.setContentView(R.layout.display_voucher_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView voucherMessage = dialog.findViewById(R.id.voucher_message);
        voucherMessage.setText("Voucher "+ message +" has been successfully redeemed" );
        Button btnClose = dialog.findViewById(R.id.dialog_send_error_close_button);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voucher, container, false);
        swipeContainer = view.findViewById(R.id.swipeContainerVouchers);
        qrCodeScanVoucher = view.findViewById(R.id.qrCodeScanVoucher);
        qrCodeScanVoucher.setOnClickListener(v -> startQrScanner());

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).getUserAccountInfo();
                getVouchers(true);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerViewVouchers = view.findViewById(R.id.card_recycler_view_vouchers);
        emptyView = (TextView) view.findViewById(R.id.empty_view_vouchers);
        return view;
    }
}