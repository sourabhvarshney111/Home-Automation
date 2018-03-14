package com.example.android.homeauto;

import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Text_processing extends Activity {
    ArrayList<String> noun=new ArrayList<String>();
    ArrayList<String> verb= new ArrayList<String>();
    ArrayList<String> compound = new ArrayList<String>();
    int[] light= new int[10];

    public Text_processing(){
        noun.add("living");
        noun.add("guest");
        noun.add("drawing");
        noun.add("bedroom");
        noun.add("bathroom");
        noun.add("kitchen");
        noun.add("dining");
        noun.add("toilet");
        noun.add("entrance");
        noun.add("attached");
        verb.add("on");
        verb.add("off");
        for(int i=0;i<10;i++)
            light[i]=0;
    }
    public int txt_pro(String str1, TextView textView1) {
        String str = str1.toLowerCase();
        ArrayList<String> tokens = new ArrayList<String>();
        StringTokenizer tokenizer= new StringTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        ArrayList<String> noune = new ArrayList<String>();
        ArrayList<String> verbe = new ArrayList<String>();
        ArrayList<String> compounde = new ArrayList<String>();
        noune = intersection(tokens, noun,textView1);
        verbe = intersection(tokens, verb,textView1);
        compounde = intersection(tokens, compound,textView1);
        if(textView1.getText().toString().equals("You have given wrong command"))
            return -1;
        int x = noune.size();
        int y = verbe.size();
        if(x>2){
            textView1.setText("System can only handle two room commands at maximum");
            return -1;
        }
        if(y==2)
            Bifunction(str,noune);
        else if((y==1) && (verbe.contains("on")))
            OnFunction(noune);
        else if((y==1) && (verbe.contains("off")))
            Offfunction(noune);
        return 0;
    }
    public <T> ArrayList<T> intersection(ArrayList<T> list1, ArrayList<T> list2,TextView textView1) {
        ArrayList<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                if(!list.contains(t))
                    list.add(t);
                else if(t.equals("light")||t.equals("lights")||t.equals("fan")||t.equals("fans"))
                    textView1.setText("Unrecognized Command");
            }
        }
        return list;
    }

    public int[] getLight() {
        return light;
    }
    private int DataDecision(int p,int q,int r){
        int x;
        if(p<q){
            if(r>p && r<q)
                x=1;
            else
                x=0;
        }
        else{
            if(r>p && r>q)
                x=1;
            else
                x=0;
        }
        return x;
    }
    private void OnFunction(ArrayList<String> noune){
        if(noune.contains("living"))
            light[0]=1;
        if(noune.contains("guest"))
            light[1]=1;
        if(noune.contains("drawing"))
            light[2]=1;
        if(noune.contains("bedroom"))
            light[3]=1;
        if(noune.contains("kitchen"))
            light[4]=1;
        if(noune.contains("drawing"))
            light[5]=1;
        if(noune.contains("bathroom"))
            light[6]=1;
        if(noune.contains("toilet"))
            light[7]=1;
        if(noune.contains("entrance"))
            light[8]=1;
        if(noune.contains("attached"))
            light[9]=1;
    }
    private void Offfunction(ArrayList<String> noune){
        if(noune.contains("living"))
            light[0]=0;
        if(noune.contains("guest"))
            light[1]=0;
        if(noune.contains("drawing"))
            light[2]=0;
        if(noune.contains("bedroom"))
            light[3]=0;
        if(noune.contains("kitchen"))
            light[4]=0;
        if(noune.contains("drawing"))
            light[5]=0;
        if(noune.contains("bathroom"))
            light[6]=0;
        if(noune.contains("toilet"))
            light[7]=0;
        if(noune.contains("entrance"))
            light[8]=0;
        if(noune.contains("attached"))
            light[9]=0;
    }
    private void Bifunction(String str,ArrayList<String> noune){
        int p=str.indexOf("on");
        int q=str.indexOf("off"),r;
        if(noune.contains("living")){
            r=str.indexOf("living");
            light[0]=DataDecision(p,q,r);
        }
        if(noune.contains("guest")){
            r=str.indexOf("guest");
            light[1]=DataDecision(p,q,r);
        }
        if(noune.contains("drawing")){
            r=str.indexOf("drawing");
            light[2]=DataDecision(p,q,r);
        }
        if(noune.contains("bedroom")){
            r=str.indexOf("bedroom");
            light[3]=DataDecision(p,q,r);
        }
        if(noune.contains("kitchen")){
            r=str.indexOf("kitchen");
            light[4]=DataDecision(p,q,r);
        }
        if(noune.contains("dining")){
            r=str.indexOf("dining");
            light[5]=DataDecision(p,q,r);
        }
        if(noune.contains("bathroom")){
            r=str.indexOf("bathroom");
            light[6]=DataDecision(p,q,r);
        }
        if(noune.contains("toilet")){
            r=str.indexOf("toilet");
            light[7]=DataDecision(p,q,r);
        }
        if(noune.contains("entrance")){
            r=str.indexOf("entrance");
            light[8]=DataDecision(p,q,r);
        }
        if(noune.contains("attached")){
            r=str.indexOf("attached");
            light[9]=DataDecision(p,q,r);
        }
    }
}
