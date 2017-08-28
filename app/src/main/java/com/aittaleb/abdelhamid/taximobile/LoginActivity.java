package com.aittaleb.abdelhamid.taximobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {




    private ImageView taxitapp = null;
    private Socket msocket;

    private Button signIn = null;
    private Button signUp = null;



    private Emitter.Listener onRespLogin = new Emitter.Listener() {
        String ss="";
        @Override
        public void call(Object... args) {

            ss=(String) args[0].toString();
            if(ss.equals("no login")){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getApplicationContext(),"your login or pessword is incorrect "+msocket.id().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Intent goToMap = new Intent(LoginActivity.this,MapsActivity.class);
                startActivity(goToMap);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation);

        taxitapp = (ImageView) findViewById(R.id.taxitapp) ;
        taxitapp.setAnimation(animation);
        signIn = (Button) findViewById(R.id.start);
        signUp = (Button) findViewById(R.id.signUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final EditText log = (EditText) findViewById(R.id.login);
                final EditText pass = (EditText) findViewById(R.id.password);
                JSONObject login = new JSONObject();
                try {
                    login.put("login",log.getText().toString());
                    login.put("password",pass.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                msocket.connect();
                msocket.emit("login",login);


            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent goToRegister =new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(goToRegister);
            }
        });



        try {
            msocket = IO.socket("http://10.0.2.2:9080/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        msocket.on("respLogin",onRespLogin);


    }

    public void onDestroy(){
        super.onDestroy();
        msocket.disconnect();
    }

    public void PasswordForgotten(View view) {
        Toast.makeText(this,"yedek fih",Toast.LENGTH_SHORT).show();
    }


}
