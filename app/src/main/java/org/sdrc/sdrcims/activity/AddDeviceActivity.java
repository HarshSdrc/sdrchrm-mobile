package org.sdrc.sdrcims.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.listener.EmployeeNameListListener;
import org.sdrc.sdrcims.listener.TypeDetailsListner;
import org.sdrc.sdrcims.model.CourseAnnouncementModel;
import org.sdrc.sdrcims.model.DeviceModel;
import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.ReturnModel;
import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.network.NetworkHelper;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

/**
 * Created by SDRC_DEV on 06-01-2018.
 */

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener,EmployeeNameListListener {

    private Button submitButton,resetButton;

    private TextView select_date;

    private EditText deviceName, modelNo, serialNo, firmware , firmwareVersion , macAdress;

    private Spinner deviceTypes ;




    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.add_device);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.add_new_device);
        deviceName = (EditText) findViewById(R.id.device_name);
        modelNo = (EditText) findViewById(R.id.model_number);
        serialNo = (EditText) findViewById(R.id.serial_number);
        firmware=(EditText) findViewById(R.id.firmware);
        firmwareVersion=(EditText) findViewById(R.id.firmware_version);
        macAdress=(EditText) findViewById(R.id.mac_adress);
        select_date=(TextView)findViewById(R.id.select_date);
        select_date.setOnClickListener(this);

        submitButton = (Button)findViewById(R.id.add_devie);
        resetButton = (Button)findViewById(R.id.reset_device);
        submitButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        deviceTypes = (Spinner)findViewById(R.id.device_type);
        NetworkHelper helper = new NetworkHelper(AddDeviceActivity.this,AddDeviceActivity.this);
         helper.getDeviceType();
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

        }

    }


    private void submitEvent() {

        DeviceModel deviceModel = new DeviceModel();

        if(deviceName.getText().toString().length()==0){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.device_name),Toast.LENGTH_LONG).show();


        }
        else if(modelNo.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.model_number),Toast.LENGTH_LONG).show();
        }
        else{

           deviceModel = new DeviceModel();
           deviceModel.setBarCode("");
           deviceModel.setDescription("");
           deviceModel.setDeviceName(deviceName.getText().toString());
           deviceModel.setDeviceTypeId(((TypeDetailModel)deviceTypes.getSelectedItem()).getId());
           deviceModel.setFirmWare(firmware.getText().toString());
        }

    }

    private void resetAllField() {

    }



    private void showDatePicker(final TextView edt) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                edt.setText(day + "/" + month + "/" + year);
            }
        }, year, month, day);
        dialog.setTitle("Purchase Date");

        dialog.show();
    }

    @Override
    public void setEmployeeList(List<EmployeeModel> list) {

    }

    @Override
    public void setTypeDetailList(List<TypeDetailModel> typeDetailModels) {

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeDetailModels);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deviceTypes.setAdapter(adapter);

    }
}
