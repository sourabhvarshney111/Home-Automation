package com.example.android.home_automation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    private TextView txtSpeechInput,output;
    private ImageButton btnSpeak;
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
                    try{
                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        String result1=new String();
                        if(result.size()>1)
                            result1=result.get(0);
                        else
                            result1=result.toString();
                        result1=result1.replace("fence","fans");
                        if(!result1.contains("off"))
                            result1=result1.replace("of","off");
                        result1=result1.replace("button","but turn");
                        txtSpeechInput.setText("Command: "+result1);
                        result1=result1.replace('[',' ');
                        result1=result1.replace(']',' ');
                        result1=result1.replace("down","off");
                        result1=result1.replace("up","on");
                        result1=result1.replace("in","on");
                        result1=result1.replace("out","off");
                        int flag=textProcessing.txt_pro(result1,txtSpeechInput);
                        if(flag==0)
                        {
                            if(textProcessing.getFan()==0&&textProcessing.getLight()==0)
                            {
                                output.setText("Current Status:\n"+"Lights: OFF\n"+"Fans: OFF");
                            }
                            else if(textProcessing.getFan()==1&&textProcessing.getLight()==0)
                            {
                                output.setText("Current Status:\n"+"Lights: OFF\n"+"Fans: ON");
                            }
                            else if(textProcessing.getFan()==0&&textProcessing.getLight()==1)
                            {
                                output.setText("Current Status:\n"+"Lights: ON\n"+"Fans: OFF");
                            }
                            else
                                output.setText("Current Status:\n"+"Lights: ON\n"+"Fans: ON");
                        }
                    }
                    catch(Exception e){
                        txtSpeechInput.setText("You have given Wrong Command");
                    }
                }

                break;
            }

        }
    }
}

