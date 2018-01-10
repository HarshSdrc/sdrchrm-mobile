package org.sdrc.sdrcims.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.util.StateManager;

public class HomeActivity extends LoginActivity implements View.OnClickListener {

    private Button mDmButton,mLeaveButton,mTrainingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.home);
        mTrainingButton = (Button) findViewById(R.id.im_btn);
        mTrainingButton.setOnClickListener(this);
        mDmButton=(Button) findViewById(R.id.dm_btn);
        mDmButton.setOnClickListener(this);

        StateManager stateManager = new StateManager(getApplicationContext());
        Log.v("MyCookie",stateManager.getCookie());
    }

    @Override
    public void onClick(View view) {
    if(view.getId() == R.id.im_btn){

        Intent intent = new Intent(HomeActivity.this,ManageTrainingActivity.class);
        startActivity(intent);
    }
    else if(view.getId()==R.id.dm_btn)
    {
        Intent deviceManagementIntenet = new Intent(HomeActivity.this,DeviceManagementActivity.class);
        startActivity(deviceManagementIntenet);
//        finish();
    }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent loginIntenet = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(loginIntenet);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        Intent loginIntenet = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(loginIntenet);
        return super.onSupportNavigateUp();
    }
}
