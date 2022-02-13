package com.arkandas.vulkanite;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arkandas.vulkanite.activity.LoginActivity;
import com.arkandas.vulkanite.activity.MainActivity;
import com.arkandas.vulkanite.data.util.UserPreferencesService;
import com.arkandas.vulkanite.adapters.TransactionListAdapter;
import com.arkandas.vulkanite.data.model.response.TransactionModel;
import com.arkandas.vulkanite.data.remote.APIService;
import com.arkandas.vulkanite.data.remote.APIUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {

    private SwipeRefreshLayout swipeContainer;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService mAPIService;
    private RecyclerView recyclerViewTransactions;
    private TransactionListAdapter adapter;
    private ArrayList<TransactionModel> data = new ArrayList<TransactionModel>();
    private TextView emptyView;
    private static final String TAG = FragmentHome.class.getSimpleName();

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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

        getTransactionHistory();

    }

    public void getTransactionHistory(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Retrieving transactions");
        progressDialog.show();

        mAPIService.getUserTransactions(UserPreferencesService.GetToken(getActivity().getApplicationContext())).enqueue(new Callback<List<TransactionModel>>(){

            @Override
            public void onResponse(Call<List<TransactionModel>> call, Response<List<TransactionModel>> response) {

                Log.i(TAG, response.message());

                if (response.isSuccessful()){
                    recyclerViewTransactions.setHasFixedSize(true);
                    recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    data.clear();
                    if(response.body().size() > 0){
                        recyclerViewTransactions.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        for(int i = 0; i< response.body().size();i++) {
                            data.add(response.body().get(i));
                        }
                    }else{
                        recyclerViewTransactions.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                    adapter = new TransactionListAdapter(data, getContext());
                    recyclerViewTransactions.setAdapter(adapter);
                    swipeContainer.setRefreshing(false);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<TransactionModel>> call, Throwable t) {
                swipeContainer.setRefreshing(false);
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        swipeContainer = view.findViewById(R.id.swipeContainerTransactions);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).getUserAccountInfo();
                getTransactionHistory();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerViewTransactions = view.findViewById(R.id.card_recycler_view_transactions);
        emptyView = (TextView) view.findViewById(R.id.empty_view_transactions);
        return view;
    }
}