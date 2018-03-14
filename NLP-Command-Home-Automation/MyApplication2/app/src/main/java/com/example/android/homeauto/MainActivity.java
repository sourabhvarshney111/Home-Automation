package com.example.android.homeauto;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private TextView txtSpeechInput,output;
    private ImageButton btnSpeak;
    String order=null;
    static EditText INSTANCE;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Text_processing textProcessing= new Text_processing();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        output= (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);


        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
       // INSTANCE=this;
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        String result1=new String();
                        if(result.size()>1)
                            result1=result.get(0);
                        else
                            result1=result.toString();
                        try{
                        result1=result1.replace("fence","fans");
                        if(!result1.contains("off"))
                            result1=result1.replace("of","off");
                        result1=result1.replace("button","but turn");
                        txtSpeechInput.setText("Command: "+result1);
                        result1=result1.replace('[',' ');
                        result1=result1.replace(']',' ');
                        result1=result1.replace("down","off");
                        result1=result1.replace("up","on");
                        result1=result1.replace("out","off");
                        result1=result1.replace("live in","living");
                        result1=result1.replace("draw in","drawing");
                        result1=result1.replace("bed room","bedroom");
                        result1=result1.replace("bath room","bathroom");
                        result1=result1.replace("dine in","dining");
                        if(result1.contains("attached"))
                            result1=result1.replace("bathroom","");
                        int flag=textProcessing.txt_pro(result1,txtSpeechInput);
                        if(flag==0) {
                            //write your code logic to send output to arduino
                        }
                    }
                    catch(Exception e){
                        txtSpeechInput.setText("You have given wrong command " + "\""+result1+"\"");
                    }
                }

                break;
            }

        }
    }
    public void send(View v)
    {
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
        //setContentView(R.layout.activity_main2);
        //  finish();
    }

   // public static EditText getActivityInstance() {  return INSTANCE; }
   // public String getData() {   return this.order; }
}


