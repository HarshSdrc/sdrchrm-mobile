package org.sdrc.sdrcims.service;

import org.sdrc.sdrcims.model.ReturnModel;
import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.model.UserDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by SDRC_DEV on 06-01-2018.
 */

public interface DeviceManagementService {

    @POST("getDeviceType")
    Call<ReturnModel> getDeviceType(@Header("Cookie")String cookie, @Body UserDataModel userDataModel);

    @POST("addDevice")
    Call<ReturnModel> saveDevice(@Header("Cookie") String cookie,@Body UserDataModel userDataModel);

    @POST("login")
    Call<ReturnModel> login(@Body UserDataModel userDataModel);
}
