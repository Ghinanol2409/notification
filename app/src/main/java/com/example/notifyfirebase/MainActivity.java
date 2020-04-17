package com.example.notifyfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btn_send;
    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View();

        if (getIntent().hasExtra("Category")){
            Intent intent = new Intent(MainActivity.this,ReceiverNotificationActivity.class);
            intent.putExtra("Category",getIntent().getStringExtra("Category"));
            intent.putExtra("branchID",getIntent().getStringExtra("branchID"));
            startActivity(intent);
        }

        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
    }

    //region Send Notification
    private  void sendNotification(){
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to","/topic/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("Tittle","Some tittles");
            notificationObj.put("Body","Some bodies");

            JSONObject extraData = new JSONObject();
            extraData.put("brandID","puma");
            extraData.put("Category","SHoes");

            mainObj.put("Notification", notificationObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("MUR", "onResponse: ");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=844131914265");
                    return  header;
                }
            };

            mRequestQue.add(request);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //endregion

    //region View
    private void View() {
        btn_send = findViewById(R.id.btn);
    }
    //endregion
}
