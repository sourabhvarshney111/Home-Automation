package com.example.android.home_automation;

import android.app.Activity;

        import android.app.Activity;
        import android.widget.TextView;

        import java.util.ArrayList;

        import static java.lang.Math.abs;


/**
 * Created by user on 1/19/18.
 */

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
        compound.add("or");
        fan=0;
        light=0;
    }
    public int txt_pro(String str1,TextView textView) {
        String str = str1.toLowerCase();
        ArrayList<String> tokens = new ArrayList<String>();
        for (String word : str.split(" ")) {
            tokens.add(word);
        }
        ArrayList<String> noune = new ArrayList<String>();
        ArrayList<String> verbe = new ArrayList<String>();
        ArrayList<String> compounde = new ArrayList<String>();
        noune = intersection(tokens, noun);
        verbe = intersection(tokens, verb);
        compounde = intersection(tokens, compound);
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
                int b = str.indexOf("off");
                int c = str.indexOf("on");
                int d = -1, e = -1;
                if (str.contains("light"))
                    d = str.indexOf("light");
                else if (str.contains("lights"))
                    d = str.indexOf("lights");
                if (str.contains("fan"))
                    d = str.indexOf("fan");
                else if (str.contains("fans"))
                    d = str.indexOf("fans");
                int i = abs(d - c);
                int j = abs(d - b);
                if (i > j) {
                    light = 1;
                    fan = 0;
                } else {
                    light = 0;
                    fan = 0;
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
            }
            if ((noune.contains("light") || noune.contains("lights")) && u == 315)
                light = 0;
            else if ((noune.contains("light") || noune.contains("lights")) && u != 315)
                light=1;
            if ((noune.contains("fan") || noune.contains("fans")) && u == 315)
                fan = 0;
            else if ((noune.contains("fan") || noune.contains("fans")) && u != 315)
                fan=1;
            return 0;
        }
        else{

            textView.setText("You have given wrong command "+x +" "+y+" "+tokens.toString());
            return -1;
        }
    }
    public <T> ArrayList<T> intersection(ArrayList<T> list1, ArrayList<T> list2) {
        ArrayList<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
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