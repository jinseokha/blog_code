package com.devseok.sharedpreferences_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> array = new ArrayList<>();
    Map<String, String> hashmap = new HashMap<>();

    Button button_save;
    Button button_load;

    // int : MODE_APPEND // 기존 preferences에 신규 preferences를 추가
    // int : MODE_MULTI_PROCESS // 이미 호출되어 사용 중인지 체크
    // int : MODE_PRIVATE // 생성한 appliation에서만 사용 가능
    // int : MODE_WORLD_READABLE // 다른 application에서 읽을 수 있음
    // int : MODE_WORLD_WRITEABLE // 다른 applicaton에서 기록할 수 있음

    SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();

    String str;
    int INT;
    boolean is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_save = findViewById(R.id.button_save);
        button_load = findViewById(R.id.button_load);

        array.add("test1");
        array.add("test2");
        array.add("test3");


        button_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // string, int, boolean
                loadSharedPreference();

                // arraylist
                loadArrayList();

                // map
                loadHashMap();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // string, int, boolean
                saveSharedPreference();

                // arraylist
                saveArrayList(array);

                // map
                saveHashMap(hashmap);
            }
        });

    }

    // string, int, boolean :: save
    public void saveSharedPreference() {
        editor.putString("string", "string call");
        editor.putInt("int", 77);
        editor.putBoolean("boolean", true);
        editor.commit();

    }

    // string, int, boolean :: load
    public void loadSharedPreference() {
        str = pref.getString("string", "null");
        INT = pref.getInt("int", 0);
        is = pref.getBoolean("boolean", false);

    }

    // arraylist :: save
    public void saveArrayList(ArrayList<String> values) {

        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString("array", a.toString());
        } else {
            editor.putString("array", null);
        }
        editor.commit();
    }

    public ArrayList<String> loadArrayList() {

        String json = pref.getString("array", null);
        ArrayList<String> urls = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    // hashmap :: save
    public void saveHashMap(Map<String, String> inputMap){
        if (pref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("hashmap").commit();
            editor.putString("hashmap", jsonString);
            editor.commit();
        }
    }

    // hashmap :: load
    public Map<String, String> loadHashMap(){
        Map<String, String> outputMap = new HashMap<String,String>();

        try{
            if (pref != null){
                String jsonString = pref.getString("hashmap", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String k = keysItr.next();
                    String v = (String) jsonObject.get(k);
                    outputMap.put(k,v);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }
}