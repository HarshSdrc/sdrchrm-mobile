package org.sdrc.sdrcims.service;

import org.sdrc.sdrcims.model.CourseAnnouncementModel;
import org.sdrc.sdrcims.model.DropDown;
import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.ReturnModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Amit Kumar Sahoo(amit@sdrc.co.in) on 04-01-2018.
 */

public interface TrainerList {
    @GET("dropDownEmp")
    Call<DropDown> getTrainingList();


}

