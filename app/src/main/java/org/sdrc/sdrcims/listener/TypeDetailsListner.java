package org.sdrc.sdrcims.listener;

import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.TypeDetailModel;

import java.util.List;

/**
 * Created by Harsh Pratyush (harsh@sdrc.co.in) on 06-01-2018.
 */

public interface TypeDetailsListner {

    void setTypeList(List<TypeDetailModel> typeDetailModels);
}
