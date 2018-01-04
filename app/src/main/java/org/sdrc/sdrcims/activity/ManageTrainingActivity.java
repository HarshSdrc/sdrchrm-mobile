package org.sdrc.sdrcims.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sdrc.sdrcims.R;

public class ManageTrainingActivity extends AppCompatActivity {
    private Button courseAnnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_training);
        getSupportActionBar().setTitle("Traning Management");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseAnnBtn = (Button) findViewById(R.id.course_ann_btn);
        courseAnnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ManageTrainingActivity.this,CourseAnnouncementActivity.class);
                startActivity(in);
            }
        });

    }
}
