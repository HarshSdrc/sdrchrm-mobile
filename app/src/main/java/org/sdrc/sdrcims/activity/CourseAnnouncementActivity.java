package org.sdrc.sdrcims.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.network.NetworkHelper;

import java.io.File;
import java.util.Calendar;

public class CourseAnnouncementActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText startDate, endDate, startTime, endTime;
    ImageView cousrseStructureImg;
    int ATTACHMENT_CODE = 0;
    File filetoSend = null;
    private TextView attachmentTv;
    private Button submitButton,resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_announcement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.course_announcement);
        startDate = (EditText) findViewById(R.id.start_date_edt);
        endDate = (EditText) findViewById(R.id.end_date_edt);
        startTime = (EditText) findViewById(R.id.start_time_edt);
        endTime = (EditText) findViewById(R.id.end_time_edt);
        attachmentTv = (TextView) findViewById(R.id.attachment_tv);
        cousrseStructureImg = (ImageView) findViewById(R.id.attachment_img);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        endTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        cousrseStructureImg.setOnClickListener(this);
        NetworkHelper helper = new NetworkHelper(CourseAnnouncementActivity.this);
        helper.getTrainerList();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date_edt:
                showDatePicker(startDate);
                break;
            case R.id.end_date_edt:
                showDatePicker(endDate);
                break;
            case R.id.start_time_edt:
                showTimePickert(startTime);
                break;
            case R.id.end_time_edt:
                showTimePickert(endTime);
                break;
            case R.id.attachment_img:
                selectFile();
        }
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, ATTACHMENT_CODE);


    }

    private void showTimePickert(final EditText edt) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String AM_PM;
                if (selectedHour < 12)
                    AM_PM = "AM";
                else
                    AM_PM = "PM";


                edt.setText(selectedHour + ":" + selectedMinute + "" + AM_PM);
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void showDatePicker(final EditText edt) {
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
        dialog.setTitle("Select Date");

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ATTACHMENT_CODE) {
                Uri returnUri = data.getData();
                //check for document type
                String mimeType = getContentResolver().getType(returnUri);
                    Cursor returnCursor =
                            getContentResolver().query(returnUri, null, null, null, null);
                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    String FilePath = data.getData().getPath();
                    attachmentTv.setVisibility(View.VISIBLE);
                    attachmentTv.setText("");
                    attachmentTv.setText(returnCursor.getString(nameIndex));
                    filetoSend = new File(FilePath);

            }


        }
    }

}
