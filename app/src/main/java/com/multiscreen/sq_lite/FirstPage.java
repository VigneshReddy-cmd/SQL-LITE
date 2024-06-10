package com.multiscreen.sq_lite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FirstPage extends AppCompatActivity {
    private EditText name;
    private EditText rollno;
    private  EditText section;
    private Button submit;
    private  DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        name=findViewById(R.id.name);
        rollno=findViewById(R.id.rollno);
        section=findViewById(R.id.section);
        submit=findViewById(R.id.submit);

        db=new DBHandler(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stu_name=name.getText().toString();
                String stu_rollno=rollno.getText().toString();
                String stu_section=section.getText().toString();
                Students students=new Students(stu_name,stu_rollno,stu_section);
                if(!db.addStudent(students))
                {
                    Toast.makeText(FirstPage.this, "ERROR OCCURED PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(FirstPage.this, "Sucessfully Updated", Toast.LENGTH_SHORT).show();
                }
                name.setHint("Enter name");
                rollno.setHint("rollNo");
                section.setHint("section");
            }
        });
    }
}