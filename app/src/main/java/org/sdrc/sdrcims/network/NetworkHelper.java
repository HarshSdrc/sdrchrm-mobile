package org.sdrc.sdrcims.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.listener.EmployeeNameListListener;
import org.sdrc.sdrcims.listener.TypeDetailsListner;
import org.sdrc.sdrcims.model.DeviceModel;
import org.sdrc.sdrcims.model.DropDown;
import org.sdrc.sdrcims.model.ReturnModel;

import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.model.UserDataModel;
import org.sdrc.sdrcims.service.DeviceManagementService;
import org.sdrc.sdrcims.service.TrainerList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
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
    private EmployeeNameListListener listener;
    private TypeDetailsListner typeDetailListner;
    private Retrofit retrofit;
    private OkHttpClient httpClient=new OkHttpClient();





    public NetworkHelper(Context ctx, EmployeeNameListListener listener){
        this.context = ctx;
        this.listener = listener;
        initRetrofitClient();
    }

    public NetworkHelper(Context ctx, TypeDetailsListner typeDetailListner){
        this.context = ctx;
        this.typeDetailListner = typeDetailListner;
        initRetrofitClient();
    }



    private void initRetrofitClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
         retrofit = new Retrofit
                 .Builder()
                .baseUrl(context.getResources().getString(R.string.baseurl))//reading this from string folder
                .addConverterFactory(GsonConverterFactory.create(gson))

                 .build()
                    ;

//        httpClient.
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

                    listener.setTypeDetailList(response.body().getTypeDetailsModel());

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

    public void getDeviceType(UserDataModel userDataModel,String cookie){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait");
        dialog.show();

//        if (!TextUtils.isEmpty(authToken)) {
//            AuthenticationInterceptor interceptor =
//                    new AuthenticationInterceptor(authToken);
//
//            if (!httpClient.interceptors().contains(interceptor)) {
//                httpClient.addInterceptor(interceptor);




        DeviceManagementService deviceManagementService =retrofit.create(DeviceManagementService.class);
        Call<ReturnModel> call = deviceManagementService.getDeviceType(cookie,userDataModel);

        call.enqueue(new Callback<ReturnModel>() {
            @Override
            public void onResponse( Call<ReturnModel> call, Response<ReturnModel> response) {



               if(response.body()!=null&&response.body().getStatusCode()==200)
               {
                   Gson gson = new Gson();
                   String gsonString = gson.toJson(response.body().getObject());
                   TypeDetailModel typeDetailModel = null;
                   List<TypeDetailModel> typeDetailModelList = new ArrayList<TypeDetailModel>();

                   if (response.body().getObject() instanceof ArrayList) {
                   List<Object> objects = new ArrayList<>();
                   for (Object objectA : (ArrayList) response.body().getObject()) {
                       gsonString = gson.toJson(objectA);
                       typeDetailModel = gson.fromJson(gsonString, TypeDetailModel.class);
                       typeDetailModelList.add(typeDetailModel);
                   }


               }
                   typeDetailListner.setTypeList(typeDetailModelList);
               }
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<ReturnModel> call, Throwable t) {
                t.fillInStackTrace();
                dialog.cancel();
            }
        });
    }

    public void sendCourseAnnouncement(){




    }


    public void sendNewDevice(UserDataModel userDataModel,String cookie) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait");
        dialog.show();
        DeviceManagementService deviceManagementService =retrofit.create(DeviceManagementService.class);
        Call<ReturnModel> call = deviceManagementService.saveDevice(cookie,userDataModel);
        call.enqueue(new Callback<ReturnModel>() {
            @Override
            public void onResponse( Call<ReturnModel> call, Response<ReturnModel> response) {

                dialog.cancel();
                typeDetailListner.saveDevice(response.body());

            }

            @Override
            public void onFailure(Call<ReturnModel> call, Throwable t) {
                dialog.cancel();
            }
        });
    }

    public void login(UserDataModel userDataModel){

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Please Wait");
        dialog.show();
        DeviceManagementService deviceManagementService =retrofit.create(DeviceManagementService.class);
        Call<ReturnModel> call = deviceManagementService.login(userDataModel);
        call.enqueue(new Callback<ReturnModel>() {
            @Override
            public void onResponse( Call<ReturnModel> call, Response<ReturnModel> response) {

                {
                    dialog.cancel();
                    String cookies="";
                    if(response.headers().values("Set-Cookie")!=null) {
                      for(String cookie: response.headers().values("Set-Cookie"))
                      {
                          if(cookies.trim().equalsIgnoreCase(""))
                          cookies+=(cookie.split(";")[0]);
                          else
                              cookies+= "; "+cookie.split(";")[0];
                      }


                    }



                    typeDetailListner.login(response.body(),cookies);

                }


            }

            @Override
            public void onFailure(Call<ReturnModel> call, Throwable t) {
                dialog.cancel();
            }
        });

    }


}
