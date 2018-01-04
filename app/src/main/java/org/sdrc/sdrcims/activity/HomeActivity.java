package org.sdrc.sdrcims.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sdrc.sdrcims.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mDmButton,mLeaveButton,mTrainingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mTrainingButton = (Button) findViewById(R.id.im_btn);
        mTrainingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    if(view.getId() == R.id.im_btn){

        Intent intent = new Intent(HomeActivity.this,ManageTrainingActivity.class);
        startActivity(intent);
    }
    }
}
