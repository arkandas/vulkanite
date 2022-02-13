package com.arkandas.vulkanite;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.arkandas.vulkanite.activity.LoginActivity;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestFragment extends Fragment {

    Button requestCurrencyButton;
    EditText requestAmountText;
    ImageView requestQrCode;
    TextInputLayout requestAmountInputLayout;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance(String param1, String param2) {
        RequestFragment fragment = new RequestFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        requestAmountInputLayout = (TextInputLayout) view.findViewById(R.id.requestAmountInputLayout);

        requestCurrencyButton = view.findViewById(R.id.requestCurrencyButton);
        requestAmountText = view.findViewById(R.id.requestAmountText);
        requestQrCode = view.findViewById(R.id.requestQrImage);
        requestCurrencyButton.setOnClickListener(v -> createAndDisplayQrCode(UserPreferencesService.GetWallet(getActivity()), requestQrCode));
        // Inflate the layout for this fragment
        return view;
    }

    public void createAndDisplayQrCode(String wallet, ImageView newImage) {
        requestAmountInputLayout.setError(null);
        if (requestAmountText.getText().toString().equals("") && requestAmountText.getText().length() <= 0) {
            requestAmountInputLayout.setError("Amount cannot be empty or 0");
        }else{
            UserPreferencesService.hideKeyboard(getActivity());
            Integer amount = Integer.valueOf(requestAmountText.getText().toString().trim());
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("type", "request");
            jsonObject.addProperty("wallet", wallet);
            jsonObject.addProperty("amount", amount);

            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(jsonObject.toString(), BarcodeFormat.QR_CODE, 1000, 1000);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                newImage.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}