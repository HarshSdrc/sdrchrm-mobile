package org.sdrc.sdrcims.listener;

import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.ReturnModel;
import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.model.UserDataModel;

import java.util.List;

/**
 * Created by Harsh Pratyush (harsh@sdrc.co.in) on 06-01-2018.
 */

public interface TypeDetailsListner {

    void setTypeList(List<TypeDetailModel> typeDetailModels);

    void saveDevice(ReturnModel returnModel);

    void getAllDevice(ReturnModel returnModel);
}
