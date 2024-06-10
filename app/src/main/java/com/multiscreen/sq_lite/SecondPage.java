package com.multiscreen.sq_lite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondPage extends AppCompatActivity {
    private EditText search_rollno;
    private Button search_button;
    private TextView result;
    private TextView res_name,res_rollno,res_sec;
    private  DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        search_rollno=findViewById(R.id.search_rollno);
        search_button=findViewById(R.id.search_button);
        result=findViewById(R.id.result);
        res_name=findViewById(R.id.res_name);
        res_rollno=findViewById(R.id.res_rollno);
        res_sec=findViewById(R.id.res_sec);
        db=new DBHandler(SecondPage.this);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollno=search_rollno.getText().toString();
                db.printAllStudents();
                Students student=db.getStudent(rollno);
                if(student==null)
                {
                    Toast.makeText(SecondPage.this, "ERROR DETAILS NOT FOUND", Toast.LENGTH_SHORT).show();
                    search_rollno.setHint("Enter roll_no");
                }
                else {
                    result.setText("Result");
                    res_name.setText(student.getName());
                    res_rollno.setText(student.getRollno());
                    res_sec.setText(student.getRollno());
                }
            }
        });
    }
}