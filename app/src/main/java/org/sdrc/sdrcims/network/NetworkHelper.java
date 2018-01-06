package org.sdrc.sdrcims.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.listener.EmployeeNameListListener;
import org.sdrc.sdrcims.model.DropDown;
import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.ReturnModel;

import org.sdrc.sdrcims.service.TrainerList;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit Kumar Sahoo(amit@sdrc.co.in) on 04-01-2018.
 */

public class NetworkHelper {
    private Context context;
    EmployeeNameListListener listener;
    Retrofit retrofit;
    public NetworkHelper(Context ctx, EmployeeNameListListener listener){
        this.context = ctx;
        this.listener = listener;
        initRetrofitClient();
    }


    private void initRetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
         retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.baseurl))//reading this from string folder
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void getTrainerList(){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("loading");
        dialog.show();
        TrainerList trainerList =retrofit.create(TrainerList.class);

        Call<DropDown> call = trainerList.getTrainingList();

        call.enqueue(new Callback<DropDown>() {
            @Override
            public void onResponse(Call<DropDown> call, Response<DropDown> response) {

                //Log.v("response::",""+response.body());

                if(response.body().getStatus()==200){

                    listener.setEmployeeList(response.body().getEmployeeModel());

                }
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<DropDown> call, Throwable t) {
                Log.v("error::","failure");
                dialog.cancel();
            }
        });
    }


}
