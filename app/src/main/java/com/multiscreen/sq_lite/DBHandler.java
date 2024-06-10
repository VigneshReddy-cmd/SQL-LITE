package com.multiscreen.sq_lite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ROLLNO = "rollno";
    private static final String COLUMN_SECTION = "section";

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ROLLNO + " TEXT PRIMARY KEY, " + // Assuming rollno is the primary key
                COLUMN_SECTION + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addStudent(Students obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, obj.getName());
        values.put(COLUMN_ROLLNO, obj.getRollno());
        values.put(COLUMN_SECTION, obj.getSection());
        long id = db.insert(TABLE_NAME, null, values);
        if (id == -1) {
            Log.d("DBHandler", "addStudent: Failed to insert student");
            return false;
        } else {
            Log.d("DBHandler", "addStudent: Student inserted successfully");
            return true;
        }
    }

    public Students getStudent(String rollno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.query(TABLE_NAME, new String[]{COLUMN_NAME, COLUMN_ROLLNO, COLUMN_SECTION}, COLUMN_ROLLNO + "=?",
                new String[]{rollno}, null, null, null);
        if (cr != null && cr.moveToFirst()) {
            // Log the roll number to verify it matches
            Log.d("DBHandler", "getStudent: Found student with roll number " + rollno);

            // Log the column count to ensure query is returning expected number of columns
            int columnCount = cr.getColumnCount();
            Log.d("DBHandler", "getStudent: Column count: " + columnCount);

            Students stu = new Students(cr.getString(0), cr.getString(1), cr.getString(2));
            cr.close(); // Close the cursor to avoid memory leaks
            return stu;
        } else {
            Log.d("DBHandler", "getStudent: No student found with roll number " + rollno);
            return null;
        }
    }
    public List<Students> getAllStudents() {
        List<Students> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Students student = new Students(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLLNO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SECTION))
                );
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return  studentList;
    }
    public void printAllStudents() {
        List<Students> students = getAllStudents();
        if(students.size()==0)
        {
            Log.d("ERROR","NO DATA STOREd");
            return;
        }
        for (Students student : students) {
            Log.d("DBHandler", "Student: Name=" + student.getName() +
                    ", RollNo=" + student.getRollno() +
                    ", Section=" + student.getSection());
        }
    }




}
