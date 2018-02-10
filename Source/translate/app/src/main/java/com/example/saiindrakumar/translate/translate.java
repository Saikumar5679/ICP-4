package com.example.saiindrakumar.translate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class translate extends AppCompatActivity {


    String sourceText;
    String ln_actual;
    String ln_convert;
    TextView outputTextView;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_translate);

        outputTextView = (EditText) findViewById(R.id.editText7);
    }
    public void logout(View v) {
        Intent redirect = new Intent(translate.this, MainActivity.class);
        startActivity(redirect);
    }
    public void translateText(View v) {
        TextView sourceTextView = (EditText) findViewById(R.id.editText6);
        EditText srclng = (EditText) findViewById(R.id.editText4);
        EditText tgtlng = (EditText) findViewById(R.id.editText5);
        sourceText = sourceTextView.getText().toString();
        ln_actual= srclng.getText().toString();
        ln_convert= tgtlng.getText().toString();
        String getURL = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=trnsl.1.1.20180208T172132Z.4d5952035a250610." +
                "9f28cc124ebf7f110a734995c2ea242d47fceed2&text=" + sourceText +"&" +
                "lang="+ln_actual+"-"+ln_convert+"&[format=plain]&[options=1]&[callback=set]";//The API service URL
        final String response1 = "";
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final JSONObject jsonResult;
                    final String result = response.body().string();
                    try {
                        jsonResult = new JSONObject(result);
                        JSONArray convertedTextArray = jsonResult.getJSONArray("text");
                        final String convertedText = convertedTextArray.get(0).toString();
                        Log.d("okHttp", jsonResult.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                outputTextView.setText(convertedText);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception ex) {
            outputTextView.setText(ex.getMessage());

        }

    }
}