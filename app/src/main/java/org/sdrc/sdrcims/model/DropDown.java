package org.sdrc.sdrcims.model;

import java.util.List;

/**
 * Created by Amit Kumar Sahoo(amit@sdrc.co.in) on 05-01-2018.
 */

public class DropDown {


    List<EmployeeModel> employeeModel;
    int status;
    List<TypeDetailModel> typeDetailsModel;

    public List<TypeDetailModel> getTypeDetailsModel() {
        return typeDetailsModel;
    }

    public void setTypeDetailsModel(List<TypeDetailModel> typeDetailsModel) {
        this.typeDetailsModel = typeDetailsModel;
    }

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
