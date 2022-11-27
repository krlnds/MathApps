package com.karlina.mathapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnHitung;
    int operator;
    EditText editText1;
    EditText editText2;
    TextView txtHasil;
    RecyclerView recyclerView;

    private Riwayat_Adapter riwayatAdapter;
    private ArrayList<Riwayat> riwayatArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHitung = findViewById(R.id.btn_hitung);
        editText1 = findViewById(R.id.edt1);
        editText2 = findViewById(R.id.edt2);
        txtHasil = findViewById(R.id.txt_hasil);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        riwayatArrayList = new ArrayList<>();

        LoadData();

        riwayatAdapter = new Riwayat_Adapter(riwayatArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(riwayatAdapter);



        btnHitung.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int a, b;

                if (editText1.getText().toString().isEmpty() || editText2.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Semua kolom wajib diisi dengan angka", Toast.LENGTH_SHORT).show();
                }else{
                    a = Integer.parseInt(editText1.getText().toString());
                    b = Integer.parseInt(editText2.getText().toString());

                    if(operator == 0){

                        txtHasil.setText(tambah(a,b).toString());
                        addData(a,b, tambah(a,b), "+");

                    }else if (operator == 1){
                        txtHasil.setText(Kurang(a,b).toString());
                        addData(a,b, Kurang(a,b), " -");

                    }else if (operator == 2){
                        txtHasil.setText(Perkalian(a,b).toString());
                        addData(a,b, Perkalian(a,b), " x ");

                    }else if (operator == 3){
                        txtHasil.setText(Pembagian(a,b).toString());
                        addData(a,b, Pembagian(a,b), " : ");

                    }else{
                        Toast.makeText(MainActivity.this, a + "operator tidak valid" + b, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        final Spinner List = findViewById(R.id.spinner_operator);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.aritmatika_operator));

        List.setAdapter(adapter);

        List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
               operator = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    void addData(int a, int b, double hasil, String operator) {
        riwayatAdapter.notifyDataSetChanged();
        riwayatArrayList.add(new Riwayat(a, b, hasil,operator));
        SaveHistoryData();

    }


    public Float tambah(float a, float b){
        return a+b;

    }
    public Float Kurang(float a, float b){
        return a-b;
    }

    public Float Perkalian(float a, float b){
        return a*b;
    }
    public Float Pembagian(float a, float b){
        return a/b;
    }

    private void LoadData(){

        SharedPreferences sharedPreferences = getSharedPreferences(Config.shared_key, MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString(Config.history_key, null);

        Type type = new TypeToken<ArrayList<Riwayat>>() {}.getType();

        riwayatArrayList = gson.fromJson(json, type);

    }

    private void SaveHistoryData(){

        SharedPreferences sharedPreferences = getSharedPreferences(Config.shared_key, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        String json = gson.toJson(riwayatArrayList);
        editor.putString(Config.history_key, json);
        editor.apply();
    }



}