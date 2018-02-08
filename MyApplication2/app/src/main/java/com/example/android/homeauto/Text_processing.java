package com.example.android.homeauto;

import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Math.abs;

public class Text_processing extends Activity {
    ArrayList<String> noun=new ArrayList<String>();
    ArrayList<String> verb= new ArrayList<String>();
    ArrayList<String> compound = new ArrayList<String>();
    int fan,light;

    public Text_processing(){
        noun.add("fan");
        noun.add("fans");
        noun.add("light");
        noun.add("lights");
        verb.add("on");
        verb.add("off");
        compound.add("and");
        compound.add("but");
        fan=0;
        light=0;
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
        int a = compounde.size();
        String g = verbe.get(0);
        int u = 0;
        for (int i = 0; i < g.length(); i++) {
            u += (int) (g.charAt(i));
        }
        if (x >= 1 && y >= 1) {
            if (a > 0 && y > 1) {
                int b = str.indexOf("on");
                int c = str.indexOf("off");
                int d, e;
                d = str.indexOf("light");
                e = str.indexOf("fan");
                int i = abs(d - c);
                int j = abs(d - b);
                if (i > j) {
                    light = 1;
                    fan = 0;
                    return 0;
                } else {
                    light = 0;
                    fan = 1;
                    return 0;
                }
            }
            if (x == 2 && y == 1) {
                if (u == 315) {
                    light = 0;
                    fan = 0;
                } else {
                    light = 1;
                    fan = 1;
                }
            }
            if ((noune.contains("light") || noune.contains("lights")) && u == 315)
                light = 0;
            else if ((noune.contains("light") || noune.contains("lights")) && u != 315)
                light = 1;
            if ((noune.contains("fan") || noune.contains("fans")) && u == 315)
                fan = 0;
            else if ((noune.contains("fan") || noune.contains("fans")) && u != 315)
                fan = 1;
            return 0;
        }
        else{

            textView1.setText("You have given wrong command ");
            return -1;
        }
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

    public int getFan() {
        return fan;
    }

    public int getLight() {
        return light;
    }
}
