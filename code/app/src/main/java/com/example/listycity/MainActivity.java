package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText editCityText;
    Button buttonAddCity, buttonDeleteCity;
    String selectedCity;
    boolean isConfirmState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_view);
        editCityText = findViewById(R.id.edit_city_text);
        buttonAddCity = findViewById(R.id.button_add_city);
        buttonDeleteCity = findViewById(R.id.button_delete_city);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "Dammam"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> selectedCity = dataList.get(position));

        buttonAddCity.setOnClickListener(v -> {
            if (isConfirmState) {
                String newCity = editCityText.getText().toString().trim();
                if (!newCity.isEmpty() && !dataList.contains(newCity)) {
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged();
                    editCityText.setText("");
                    buttonAddCity.setText("Add City");
                    isConfirmState = false;
                } else {
                    Toast.makeText(MainActivity.this, "City already exists or input is empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                buttonAddCity.setText("Confirm");
                isConfirmState = true;
            }
        });

        buttonDeleteCity.setOnClickListener(v -> {
            if (selectedCity != null && dataList.contains(selectedCity)) {
                dataList.remove(selectedCity);
                cityAdapter.notifyDataSetChanged();
                selectedCity = null;
            } else {
                Toast.makeText(MainActivity.this, "No city selected or city does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }


}