package org.sdrc.sdrcims.model;

import java.util.List;

/**
 * Created by Amit Kumar Sahoo(amit@sdrc.co.in) on 05-01-2018.
 */

public class DropDown {


    List<EmployeeModel> employeeModel;
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }







    public List<EmployeeModel> getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(List<EmployeeModel> employeeModel) {
        this.employeeModel = employeeModel;
    }



}
