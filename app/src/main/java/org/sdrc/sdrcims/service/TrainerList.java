package org.sdrc.sdrcims.service;

import org.sdrc.sdrcims.model.TrainerData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Amit Kumar Sahoo(amit@sdrc.co.in) on 04-01-2018.
 */

public interface TrainerList {
    @GET("GetCountries")
    Call<List<TrainerData>> getTrainingList();
}
