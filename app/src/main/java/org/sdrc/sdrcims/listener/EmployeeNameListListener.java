package org.sdrc.sdrcims.listener;

import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.TypeDetailModel;

import java.util.List;

/**
 * Created by Subham Ashish(subham@sdrc.co.in) on 05-01-2018.
 */

public interface EmployeeNameListListener {

    void setEmployeeList(List<EmployeeModel> list);
    void setTypeDetailList(List<TypeDetailModel> typeDetailsModelList);
}
