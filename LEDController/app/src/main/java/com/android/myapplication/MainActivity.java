package com.android.myapplication;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String LED_status="";
    final Handler handler=new Handler();
    final int delay=10000; //5 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);
        ImageView imageView=findViewById(R.id.imageView3);

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        JsonObjectRequest OnRequest=new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.1.151:8000/LED_ON",
                null,null,null
        );
        JsonObjectRequest OffRequest=new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.1.151:8000/LED_OFF",
                null,null,null
        );
        JsonObjectRequest statusRequest=new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.1.151:8000/status",
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            LED_status=response.getString("status");
                            switchText(button,imageView);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },null
        );

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestQueue.add(statusRequest);
                switchText(button,imageView);
                handler.postDelayed(this,delay);
            }
        },delay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText()=="ON") {
                    requestQueue.add(OnRequest);
                    button.setText("OFF");
                    imageView.setImageResource(R.drawable.on);
                }
                else if(button.getText()=="OFF"){
                    requestQueue.add(OffRequest);
                    button.setText("ON");
                    imageView.setImageResource(R.drawable.off);

                }
            }
        });
        requestQueue.add(statusRequest);
    }
    public void switchText(Button button,ImageView imageView){
        if(LED_status=="false"){
            button.setText("ON");
            imageView.setImageResource(R.drawable.off);
        }
        else{
            button.setText("OFF");
            imageView.setImageResource(R.drawable.on);
        }
    };
}