package org.sdrc.sdrcims.listener;

import org.sdrc.sdrcims.model.EmployeeModel;

import java.util.List;

/**
 * Created by Amit Kumar Sahoo(amit@sdrc.co.in) on 05-01-2018.
 */

public interface EmployeeNameListListener {

    void setEmployeeList(List<EmployeeModel> list);
}
