package org.sdrc.sdrcims.activity;

import android.os.Bundle;
import android.view.View;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.listener.TypeDetailsListner;
import org.sdrc.sdrcims.model.ReturnModel;
import org.sdrc.sdrcims.model.TypeDetailModel;

import java.util.List;

/**
 * Created by SDRC_DEV on 09-01-2018.
 */

public class ViewDeviceActivity extends DeviceManagementActivity implements TypeDetailsListner,View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.devicehistory);
    getSupportActionBar().setTitle(R.string.view_device_history_name);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void setTypeList(List<TypeDetailModel> typeDetailModels) {

    }

    @Override
    public void saveDevice(ReturnModel returnModel) {

    }

    @Override
    public void getAllDevice(ReturnModel returnModel) {

    }

    @Override
    public void login(ReturnModel returnModel, String string) {

    }
}
