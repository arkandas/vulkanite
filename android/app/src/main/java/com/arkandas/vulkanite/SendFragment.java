package com.arkandas.vulkanite;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.arkandas.vulkanite.activity.MainActivity;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.arkandas.vulkanite.data.model.request.CurrencyTransaction;
import com.arkandas.vulkanite.data.remote.APIService;
import com.arkandas.vulkanite.data.remote.APIUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendFragment extends Fragment {


    Button sendCurrencyButton;
    EditText sendAmountText;
    EditText sendWalletText;
    EditText sendNoteText;
    private APIService mAPIService;
    private static final String TAG = SendFragment.class.getSimpleName();
    Dialog dialog;
    TextInputLayout sendAmountInputLayout;
    TextInputLayout sendNoteInputLayout;
    TextInputLayout sendWalletInputLayout;
    private FloatingActionButton qrCodeScan;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendFragment newInstance(String param1, String param2) {
        SendFragment fragment = new SendFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);

        sendAmountInputLayout = view.findViewById(R.id.sendAmountInputLayout);
        sendNoteInputLayout = view.findViewById(R.id.sendNoteInputLayout);
        sendWalletInputLayout = view.findViewById(R.id.sendWalletInputLayout);

        sendCurrencyButton = view.findViewById(R.id.sendCurrencyButton);
        sendCurrencyButton.setOnClickListener(v -> validateTextInputs());

        sendAmountText = view.findViewById(R.id.requestAmountText);
        sendWalletText = view.findViewById(R.id.sendWalletText);
        sendNoteText = view.findViewById(R.id.sendNoteText);

        qrCodeScan = view.findViewById(R.id.qrCodeScan);
        qrCodeScan.setOnClickListener(v -> startQrScanner());

        // Inflate the layout for this fragment
        return view;
    }

    public void validateTextInputs(){

        Boolean sendTransaction = true;
//        Reset errors
        sendAmountInputLayout.setError(null);
        sendWalletInputLayout.setError(null);
        sendNoteInputLayout.setError(null);

        if(sendAmountText.getText().toString().equals("") && sendAmountText.getText().length() <= 0){
            sendAmountInputLayout.setError("Amount cannot be empty or 0");
            sendTransaction = false;
        }
        if(sendWalletText.getText().toString().equals("")){
            sendWalletInputLayout.setError("Wallet cannot be empty");
            sendTransaction = false;
        }
        if(sendNoteText.getText().toString().equals("")){
            sendNoteInputLayout.setError("Note cannot be empty");
            sendTransaction = false;
        }
        if(sendTransaction == true) {
            sendCurrency(new CurrencyTransaction(Integer.valueOf(sendAmountText.getText().toString().trim()), sendNoteText.getText().toString().trim(), sendWalletText.getText().toString().trim()));
        }
    }

    public void sendCurrency(CurrencyTransaction currencyTransaction){
        mAPIService.sendCurrency(UserPreferencesService.GetToken(getActivity().getApplicationContext()), currencyTransaction).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ((MainActivity)getActivity()).getUserAccountInfo();
                if(response.code() == 200) {
//                Display success dialog
                    sendNoteText.setText("");
                    sendAmountText.setText("");
                    sendWalletText.setText("");
                    openSuccessDialog();
                }else {
                    openErrorDialog();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to process, check connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startQrScanner() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(SendFragment.this);
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
                    //Set up the TextView Values using the data from JSON
                    if(obj.getString("type").equals("wallet")) {
                        sendWalletText.setText(obj.getString("wallet"));

                    }else if(obj.getString("type").equals("request")){
                        sendAmountText.setText(obj.getString("amount"));
                        sendWalletText.setText(obj.getString("wallet"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    //In case of exception, display whatever data is available on the QR Code
                    //This can be caused due to the format MisMatch of the JSON
                    Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void openSuccessDialog() {
        dialog.setContentView(R.layout.display_send_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnClose = dialog.findViewById(R.id.dialog_send_error_close_button);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void openErrorDialog() {
        dialog.setContentView(R.layout.display_send_error);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnClose = dialog.findViewById(R.id.dialog_send_error_close_button);
        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

}