package org.sdrc.sdrcims.network;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.sdrc.sdrcims.model.TrainerData;
import org.sdrc.sdrcims.service.TrainerList;

import java.net.SocketTimeoutException;
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
    private String url="http://www.webservicex.net/country.asmx/";
    Retrofit retrofit;
    public NetworkHelper(Context ctx){
        this.context = ctx;

        initRetrofitClient();
    }

    private void initRetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
         retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public void getTrainerList(){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("loading");
        dialog.show();
        TrainerList trainerList =retrofit.create(TrainerList.class);
        Call<List<TrainerData>> call = trainerList.getTrainingList();
        call.enqueue(new Callback<List<TrainerData>>() {
            @Override
            public void onResponse(Call<List<TrainerData>> call, Response<List<TrainerData>> response) {
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<List<TrainerData>> call, Throwable t) {
                dialog.cancel();
            }
        });
    }


}
