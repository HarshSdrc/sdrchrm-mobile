package org.sdrc.sdrcims.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sdrc.sdrcims.R;
import org.sdrc.sdrcims.listener.TypeDetailsListner;
import org.sdrc.sdrcims.model.ReturnModel;
import org.sdrc.sdrcims.model.TypeDetailModel;
import org.sdrc.sdrcims.model.UserDataModel;
import org.sdrc.sdrcims.network.NetworkHelper;
import org.sdrc.sdrcims.util.StateManager;

import java.util.List;

/**
 * Created by SDRC_DEV on 10-01-2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,TypeDetailsListner {

    private EditText userName, password;

    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setTitle(R.string.login);
        userName = (EditText) findViewById(R.id.emaiid);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login_btn);
        loginButton.setOnClickListener(this);

//        userName =
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_btn){
            if(userName.getText().toString().length()==0){

                Toast.makeText(getApplicationContext(),getResources().getString(R.string.email_empty_msg),Toast.LENGTH_LONG).show();


            }

         else if(password.getText().toString().length()==0){

                Toast.makeText(getApplicationContext(),getResources().getString(R.string.password),Toast.LENGTH_LONG).show();


            }
            else {
                UserDataModel userDataModel = new UserDataModel();

                userDataModel.setUserName(userName.getText().toString());
                userDataModel.setPassword(password.getText().toString());

                NetworkHelper networkHelper = new NetworkHelper(LoginActivity.this, LoginActivity.this);
                networkHelper.login(userDataModel);
            }
            }
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

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

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

        if(returnModel.getStatusCode()!=200) {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    dialog.dismiss();

                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();
        }

        else
        {

            StateManager stateManager = new StateManager(getApplicationContext());
            stateManager.createLoginSession(userName.getText().toString(),password.getText().toString(),
                    string,"");
            finish();
            Intent homeIntenet = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(homeIntenet);
        }
    }


//    public String getCookie(String s)
//    {
//        if(s.trim().length()>2) {
//            SharedPreferences sharedpreferences = getSharedPreferences(String.valueOf(R.string.cookie), Context.MODE_PRIVATE);
//            ;
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString(String.valueOf(R.string.cookie), s);
//            editor.commit();
//        }
//        else {
//            SharedPreferences sharedpreferences = getSharedPreferences(String.valueOf(R.string.cookie), Context.MODE_PRIVATE);
//            return sharedpreferences.toString();
//        }
//        return  s;
//    }
}
