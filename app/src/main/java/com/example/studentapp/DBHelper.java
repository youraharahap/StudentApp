package com.example.studentapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "student_db";
    private static final String TB_NAME = "student";
    private static final String Col_ID = "student_id";
    private static final String Col_Name = "name";
    private static final String Col_Major = "major";
    private SQLiteDatabase mySQLite;

    // student_id = PRIMARY KEY -> otomatis UNIK (tidak boleh kembar)
    private static final String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("
            + Col_ID + " TEXT PRIMARY KEY NOT NULL, " + Col_Name + " TEXT NOT NULL, "
            + Col_Major + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
    }

    // return true kalau berhasil, false kalau Student ID sudah ada
    public boolean addStudent(StudentModel studentModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.Col_ID, studentModel.getStudentId());
        contentValues.put(DBHelper.Col_Name, studentModel.getName());
        contentValues.put(DBHelper.Col_Major, studentModel.getMajor());
        mySQLite = this.getWritableDatabase();
        long result = mySQLite.insert(DBHelper.TB_NAME, null, contentValues);
        return result != -1;
    }

    public void updateStudent(StudentModel studentModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.Col_Name, studentModel.getName());
        contentValues.put(DBHelper.Col_Major, studentModel.getMajor());
        mySQLite = this.getWritableDatabase();
        mySQLite.update(TB_NAME, contentValues, Col_ID + " = ?",
                new String[]{studentModel.getStudentId()});
    }

    public void deleteStudent(String studentId) {
        mySQLite = this.getWritableDatabase();
        mySQLite.delete(TB_NAME, Col_ID + " = ?", new String[]{studentId});
    }

    public List<StudentModel> getStudentList() {
        String viewData = "SELECT student_id, name, major FROM " + TB_NAME;
        mySQLite = this.getReadableDatabase();
        List<StudentModel> storeStudent = new ArrayList<>();
        Cursor cursor = mySQLite.rawQuery(viewData, null);
        if (cursor.moveToFirst()) {
            do {
                String studentId = cursor.getString(0);
                String name = cursor.getString(1);
                String major = cursor.getString(2);
                storeStudent.add(new StudentModel(studentId, name, major));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeStudent;
    }
}