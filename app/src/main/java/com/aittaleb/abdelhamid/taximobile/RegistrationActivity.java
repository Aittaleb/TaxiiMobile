package com.aittaleb.abdelhamid.taximobile;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {
    ChatApplication cApp;
    Socket msocket;
    MyService mService;
    public boolean mBound=false;


    private  static TextView Large;

    private Emitter.Listener onResponse = new Emitter.Listener() {
        String ss="";
        @Override
        public void call(Object... args) {
            //JSONObject data = (JSONObject) args[0];
            ss=(String) args[0].toString();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //edit.setText("Success!!!!!");
                    Toast.makeText(getApplicationContext(),ss,Toast.LENGTH_LONG).show();
                }
            });


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         //Button signUp = (Button) findViewById(R.id.signUpButton);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Large = (TextView) findViewById(R.id.editText5);
        try {
            msocket = IO.socket("http://10.0.2.2:9080/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        msocket.connect();
        msocket.on("response",onResponse);


    }

    public void PostClick(View view) {


        final EditText nom=(EditText) findViewById(R.id.PersonName);
        final EditText email=(EditText) findViewById(R.id.PersonEmail);
        final EditText phone=(EditText) findViewById(R.id.Phone);
        final EditText password=(EditText) findViewById(R.id.password);
        final EditText rePassword=(EditText) findViewById(R.id.passwordConfirmation);

        final TextInputLayout tilnom = (TextInputLayout) findViewById(R.id.namelayout);
        final TextInputLayout tilemail = (TextInputLayout) findViewById(R.id.emaillayout);
        final TextInputLayout tilphone = (TextInputLayout) findViewById(R.id.phonelayout);
        final TextInputLayout tilpassword = (TextInputLayout) findViewById(R.id.passwordlayout);
        final TextInputLayout tilrepassword = (TextInputLayout) findViewById(R.id.passwordconfirmationlayout);


        final String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        tilnom.setErrorEnabled(false);
        tilemail.setErrorEnabled(false);
        tilpassword.setErrorEnabled(false);
        tilrepassword.setErrorEnabled(false);
        tilphone.setErrorEnabled(false);

        if(nom.getText().toString().trim().length()>2 && email.getText().toString().trim().matches(emailPattern) && password.getText().toString().length()>=8
                && password.getText().toString().equals(rePassword.getText().toString()) && phone.getText().toString().trim().length()>6){


            JSONObject msg=new JSONObject();
            try {
                msg.put("password",password.getText().toString());
                msg.put("nom",nom.getText().toString());
                msg.put("email",email.getText().toString());
                msg.put("phone",phone.getText().toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }


            msocket.emit("registre",msg);
        }

        else{
            if(! email.getText().toString().trim().matches(emailPattern))
                tilemail.setError("veuillez saisir un email correct");
            if(password.getText().toString().length()<8)
                tilpassword.setError("Le mot de passe doit contenir au moins 8 caractères");
            if(nom.getText().toString().trim().length()<=2)
                tilnom.setError("Veuillez saisir votre nom");
            if(phone.getText().toString().trim().length()<=6)
                tilphone.setError("veuillez saisir un telephone valide");
            if(! password.getText().toString().equals(rePassword.getText().toString()))
                tilrepassword.setError("Veuillez vérifier le mot de passe");
        }



        // String myUrl= "http://192.168.43.90:3000/add?personName="+NameEdit.getText().toString()+"&password="+CommentEdit.getText().toString();
        //MyAsincTask task = new MyAsincTask();
        //task.execute(myUrl);
        //Toast.makeText(getApplicationContext(),"dayza hna b3da",Toast.LENGTH_SHORT).show();

    }


    public void startService(View view) {
        //in order to start the service we call the method bindService
        bindService(new Intent(getBaseContext(),MyService.class),mConnection, Context.BIND_AUTO_CREATE);
        //mConnection is an instance of ServiceConnection which provides data from the service

    }

    public void stopService(View view) {
        if(mBound)
            unbindService(mConnection);
        mBound=false;
    }

    public void getNumber(View view) {
        if(mBound)
        {
           int num = mService.getRandomNumber();
            Toast.makeText(this,"number "+num,Toast.LENGTH_SHORT).show();
        }

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.LocalBinder binder =(MyService.LocalBinder) service;
            mService = binder.getService();
            mBound=true;
            //the mBound boolean is to indicate whether the service is bound or not
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound=false;

            //we put the status of the service to unBound

        }
    };
}






