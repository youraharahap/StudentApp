package com.example.studentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnView;
    EditText editTextName, editTextStudentId;
    Spinner spinnerMajor;

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

        editTextName = findViewById(R.id.editTextName);
        editTextStudentId = findViewById(R.id.editTextStudentId);
        spinnerMajor = findViewById(R.id.spinnerMajor);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        // Spinner (sama seperti FragmentSpinner di week 3)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                StudentModel.MAJORS
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String studentId = editTextStudentId.getText().toString();
                String major = spinnerMajor.getSelectedItem().toString();

                if (name.length() <= 0 || studentId.length() <= 0) {
                    Toast.makeText(MainActivity.this, "Enter data", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    StudentModel studentModel = new StudentModel(studentId, name, major);
                    boolean success = dbHelper.addStudent(studentModel);

                    if (success) {
                        Toast.makeText(MainActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                        editTextName.setText("");
                        editTextStudentId.setText("");
                        spinnerMajor.setSelection(0);
                    } else {
                        Toast.makeText(MainActivity.this, "Student ID already exists", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewStudent.class);
                startActivity(intent);
            }
        });
    }
}