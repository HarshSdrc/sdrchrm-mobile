package org.sdrc.sdrcims.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import org.sdrc.sdrcims.R;

/**
 * Created by Harsh Pratyush (harsh@sdrc.co.in) on 06-01-2018.
 */

public class DeviceManagementActivity extends HomeActivity implements View.OnClickListener{

    private Button addDevice,manageDevice,viewHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
      setContentView(R.layout.device_managment_home);

        getSupportActionBar().setTitle(R.string.device_management);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        addDevice = (Button) findViewById(R.id.new_device_management);
        addDevice.setOnClickListener(this);

        manageDevice =(Button) findViewById(R.id.manage_device_old);
        manageDevice.setOnClickListener(this);

        viewHistory =(Button) findViewById(R.id.view_device_history);
        manageDevice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.new_device_management :
                Intent addDeviceIntenet = new Intent(DeviceManagementActivity.this,AddDeviceActivity.class);
                startActivity(addDeviceIntenet);
                finish();
                break;
            case R.id.manage_device_old:
                break;

            case R.id.view_device_history:
                Intent viewDeviceIntenet = new Intent(DeviceManagementActivity.this,ViewDeviceActivity.class);
                startActivity(viewDeviceIntenet);
                finish();
                break;

        }

    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (keyCode == KeyEvent.KEYCODE_BACK ) {
//            Intent homeIntenet = new Intent(DeviceManagementActivity.this,HomeActivity.class);
//            startActivity(homeIntenet);
//            finish();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
@Override
public void onBackPressed() {
    super.onBackPressed();
    finish();
    Intent homeIntenet = new Intent(DeviceManagementActivity.this,HomeActivity.class);
           startActivity(homeIntenet);

}

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        Intent homeIntenet = new Intent(DeviceManagementActivity.this,HomeActivity.class);
        startActivity(homeIntenet);
        return super.onSupportNavigateUp();
    }
}
