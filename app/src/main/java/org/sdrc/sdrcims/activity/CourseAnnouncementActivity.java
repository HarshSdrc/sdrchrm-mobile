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
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.listener.EmployeeNameListListener;
import org.sdrc.sdrcims.model.CourseAnnouncementModel;
import org.sdrc.sdrcims.model.EmployeeModel;
import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.network.NetworkHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

public class CourseAnnouncementActivity extends AppCompatActivity implements View.OnClickListener,EmployeeNameListListener{

    private EditText startDate, endDate, startTime, endTime,courseCode,remark,email;

    private Spinner trainerName,courseName;
    private  ImageView cousrseStructureImg;
    private int ATTACHMENT_CODE = 0;
    private File filetoSend = null;
    private TextView attachmentTv;
    private Button submitButton,resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_announcement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.course_announcement);

        courseCode = (EditText) findViewById(R.id.course_code_edt);
        courseName = (Spinner) findViewById(R.id.course_name_spinner);
        trainerName = (Spinner) findViewById(R.id.trainer_spinner);
        startDate = (EditText) findViewById(R.id.start_date_edt);
        startDate.setFocusable(false);
        endDate = (EditText) findViewById(R.id.end_date_edt);
        endDate.setFocusable(false);
        startTime = (EditText) findViewById(R.id.start_time_edt);
        startTime.setFocusable(false);
        endTime = (EditText) findViewById(R.id.end_time_edt);
        endTime.setFocusable(false);
        email = (EditText) findViewById(R.id.email_edt);
        attachmentTv = (TextView) findViewById(R.id.attachment_tv);
        cousrseStructureImg = (ImageView) findViewById(R.id.attachment_img);
        remark = (EditText) findViewById(R.id.remarks_edt);

        resetButton = (Button) findViewById(R.id.reset_btn);
        submitButton =  (Button) findViewById(R.id.submit_btn);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        endTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        cousrseStructureImg.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        NetworkHelper helper = new NetworkHelper(CourseAnnouncementActivity.this,CourseAnnouncementActivity.this);
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
                break;
            case R.id.reset_btn :
                resetAllField();
                break;
            case R.id.submit_btn :
                submitEvent();

        }
    }

    private void submitEvent() {

        CourseAnnouncementModel courseAnnouncementModel = new CourseAnnouncementModel();

        if(courseCode.getText().toString().length()==0){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.course_code_errmsg),Toast.LENGTH_LONG).show();

        }else if(courseName.getSelectedItem().toString().equalsIgnoreCase("Select Trainer Name")){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.course_name_errmsg),Toast.LENGTH_LONG).show();

        }else if(startDate.getText().toString().length()==0){

          Toast.makeText(getApplicationContext(),getResources().getString(R.string.start_date_errmsg),Toast.LENGTH_LONG).show();

        }else if(endDate.getText().toString().length()==0){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.end_date_errmsg),Toast.LENGTH_LONG).show();

        }else if(startTime.getText().toString().length()==0){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.start_time_errmsg),Toast.LENGTH_LONG).show();

        }else if(endTime.getText().toString().length()==0){

             Toast.makeText(getApplicationContext(),getResources().getString(R.string.end_time_errmsg),Toast.LENGTH_LONG).show();

        }else if(trainerName.getSelectedItem().toString().equalsIgnoreCase("Select Trainer Name")){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.trainer_name_errmsg),Toast.LENGTH_LONG).show();

        }else if(!filetoSend.exists()){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.attach_file_errmsg),Toast.LENGTH_LONG).show();

        }else if(email.getText().toString().length()==0){

            Toast.makeText(getApplicationContext(),getResources().getString(R.string.email_empty_msg),Toast.LENGTH_LONG).show();

            //Patterns.EMAIL_ADDRESS.matcher(email.getText().toString());

        } else{

            courseAnnouncementModel = new CourseAnnouncementModel();
            courseAnnouncementModel.setCourseCode(courseCode.getText().toString());
            courseAnnouncementModel.setCourseName(courseName.getSelectedItem().toString());
            courseAnnouncementModel.setStartDate(startDate.getText().toString());
            courseAnnouncementModel.setEndDate(endDate.getText().toString());
            courseAnnouncementModel.setFrom(startTime.getText().toString());
            courseAnnouncementModel.setTo(endTime.getText().toString());
            courseAnnouncementModel.setTrainerName(trainerName.getSelectedItem().toString());
            courseAnnouncementModel.setEmail(email.getText().toString());
            courseAnnouncementModel.setRemarks(remark.getText().toString());
        }

       // NetworkHelper helper = new NetworkHelper(CourseAnnouncementActivity.this,CourseAnnouncementActivity.this);


    }


    private void resetAllField() {

        courseCode.setText("");
        courseName.setSelection(0);
        startDate.setText("");
        endDate.setText("");
        startTime.setText("");
        endTime.setText("");
        attachmentTv.setText("");
        trainerName.setSelection(0);

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

    @Override
    public void setEmployeeList(List<EmployeeModel> list) {

       // List<String>  empNameList = new ArrayList<String>();



        /*ListIterator<EmployeeModel> iterator = list.listIterator();

        while(iterator.hasNext()){

            empNameList.add(iterator.next().getEmployeeName());

        }*/
        if(list!=null){

            //trainerName
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            trainerName.setAdapter(adapter);

        }

    }

    @Override
    public void setTypeDetailList(List<TypeDetailModel> typeDetailsModelList) {

        if(typeDetailsModelList!=null){

            //course name
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeDetailsModelList);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            courseName.setAdapter(adapter);

        }

    }
}
