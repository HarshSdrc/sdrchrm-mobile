package org.sdrc.sdrcims.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.sdrc.sdrcims.R;

import org.sdrc.sdrcims.listener.TypeDetailsListner;

import org.sdrc.sdrcims.model.DeviceModel;

import org.sdrc.sdrcims.model.ReturnModel;
import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.model.UserDataModel;
import org.sdrc.sdrcims.network.NetworkHelper;
import org.sdrc.sdrcims.util.IntentIntegrator;
import org.sdrc.sdrcims.util.IntentResult;
import org.sdrc.sdrcims.util.StateManager;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by SDRC_DEV on 06-01-2018.
 */

public class AddDeviceActivity extends DeviceManagementActivity implements View.OnClickListener,TypeDetailsListner {

    private Button submitButton,resetButton;

    private TextView select_date,select_barcode;

    private EditText deviceName, modelNo, serialNo, firmware , firmwareVersion , macAdress,deviceDescription;

    private Spinner deviceTypes ;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_new_device);
        deviceName = (EditText) findViewById(R.id.device_name);
        modelNo = (EditText) findViewById(R.id.model_number);
        serialNo = (EditText) findViewById(R.id.serial_number);
        firmware=(EditText) findViewById(R.id.firmware);
        firmwareVersion=(EditText) findViewById(R.id.firmware_version);
        macAdress=(EditText) findViewById(R.id.mac_adress);
        deviceDescription=(EditText)findViewById(R.id.description) ;
        select_date=(TextView)findViewById(R.id.select_date);

        select_date.setOnClickListener(this);

        select_barcode=(TextView)findViewById(R.id.select_barcode);

        select_barcode.setOnClickListener(this);

        submitButton = (Button)findViewById(R.id.add_devie);
        resetButton = (Button)findViewById(R.id.reset_device);

        submitButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        deviceTypes = (Spinner)findViewById(R.id.device_type);
        NetworkHelper helper = new NetworkHelper(AddDeviceActivity.this,AddDeviceActivity.this);
        StateManager stateManager = new StateManager(getApplicationContext());
        UserDataModel userDataModel=stateManager.getLoginDetail();
        helper.getDeviceType(userDataModel,stateManager.getCookie());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.select_date:
                showDatePicker(select_date);
                break;
            case R.id.reset_device :
                resetAllField();
                break;
            case R.id.add_devie :
                submitEvent();
                break;

            case R.id.select_barcode:
                intiateScan();
                break;

        }

    }


    private void submitEvent() {

        DeviceModel deviceModel;
        if(deviceName.getText().toString().length()==0){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.device_name),Toast.LENGTH_LONG).show();


        }
        else if(modelNo.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.model_number),Toast.LENGTH_LONG).show();
        }

        else if(macAdress.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.mac_adress),Toast.LENGTH_LONG).show();
        }

        else if(firmwareVersion.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.firmware_version),Toast.LENGTH_LONG).show();
        }

        else if(firmware.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.firmware),Toast.LENGTH_LONG).show();
        }

        else if(serialNo.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.serial_number),Toast.LENGTH_LONG).show();
        }

        else if(deviceDescription.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_device_description),Toast.LENGTH_LONG).show();
        }

        else if(select_date.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.select_date_puchase),Toast.LENGTH_LONG).show();
        }

        else if(select_barcode.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.select_barcode_new),Toast.LENGTH_LONG).show();
        }

        else{

           deviceModel = new DeviceModel();
           Random random = new Random();
           deviceModel.setBarCode(select_barcode.getText().toString());
           deviceModel.setDescription(deviceDescription.getText().toString());
           deviceModel.setDeviceName(deviceName.getText().toString());
           deviceModel.setModel(modelNo.getText().toString());
            deviceModel.setMacAdress(macAdress.getText().toString());
            deviceModel.setFirmwareVersion(firmwareVersion.getText().toString());
           deviceModel.setDeviceTypeId(((TypeDetailModel)deviceTypes.getSelectedItem()).getId());
           deviceModel.setFirmWare(firmware.getText().toString());
           deviceModel.setSerialNo(serialNo.getText().toString());
           try {
               deviceModel.setPurchaseDate(select_date.getText().toString());
           }
           catch(Exception e)
            {
                Log.v("Error",e.getMessage());
            }
           Log.v("Checking::",String.valueOf(deviceModel.getDeviceTypeId()));

           NetworkHelper networkHelper = new NetworkHelper(AddDeviceActivity.this,AddDeviceActivity.this);

            StateManager stateManager = new StateManager(getApplicationContext());
            UserDataModel userDataModel=stateManager.getLoginDetail();
            userDataModel.setSubmissionObject(deviceModel);
           networkHelper.sendNewDevice(userDataModel,stateManager.getCookie());

        }

    }

    private void resetAllField() {
        startActivity(new Intent(AddDeviceActivity.this, AddDeviceActivity.class));

    }


    private void intiateScan() {

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            select_barcode.setText(scanResult.getContents());
//            Log.v("scan Result",scanResult.getContents());
        }

    }



    private void showDatePicker(final TextView edt) {
        Calendar cal = Calendar.getInstance();


        if(edt.getText().toString().trim().length()>0)
        {
            try {
                cal.setTime(new java.util.Date(simpleDateFormat.parse(edt.getText().toString()).getTime()));
            }
            catch(Exception e)
            {
                Log.v("Error",e.getMessage());
            }
            }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;

                edt.setText(String.format("%02d",day) + "/" + String.format("%02d",month) + "/" + year);
            }
        }, year, month, day);
        dialog.setTitle("Purchase Date");
        dialog.getDatePicker().setMaxDate(new java.util.Date().getTime());

        dialog.show();
    }



    @Override
    public void setTypeList(List<TypeDetailModel> typeDetailModels) {
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeDetailModels);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deviceTypes.setAdapter(adapter);

    }

    @Override
    public void saveDevice(final ReturnModel returnModel) {

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDeviceActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(returnModel.getDescription())
                .setTitle(returnModel.getMessage());

        // 3. Get the AlertDialog from create()
        String buttonName="";

        switch (returnModel.getStatusCode())
        {
            case 200:
                buttonName="Ok";
                break;
            case 415:
                buttonName = "Login";
                break;
            case 400:
                buttonName="Yes";
        }


        builder.setCancelable(false);
        builder.setPositiveButton(buttonName, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if(returnModel.getStatusCode()==200)
                {
                    dialog.dismiss();
                    finish();
                    Intent deviceManagementIntenet = new Intent(AddDeviceActivity.this,DeviceManagementActivity.class);

                    startActivity(deviceManagementIntenet);
                }
                else if(returnModel.getStatusCode()==415)
                {
                    dialog.dismiss();
                    finish();
                    Intent deviceManagementIntenet = new Intent(AddDeviceActivity.this,LoginActivity.class);
                    startActivity(deviceManagementIntenet);
                }
                else {
                    resetAllField();
                    dialog.dismiss();
                }
            }
        });
        if(returnModel.getStatusCode()==400) {
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    finish();
                    Intent deviceManagementIntenet = new Intent(AddDeviceActivity.this, DeviceManagementActivity.class);
                    startActivity(deviceManagementIntenet);

                }
            });
        }
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public void getAllDevice(ReturnModel returnModel) {

    }

    @Override
    public void login(ReturnModel returnModel, String string) {

    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent homeIntenet = new Intent(AddDeviceActivity.this,DeviceManagementActivity.class);
        startActivity(homeIntenet);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        Intent homeIntenet = new Intent(AddDeviceActivity.this,DeviceManagementActivity.class);
        startActivity(homeIntenet);
        return super.onSupportNavigateUp();
    }
}
