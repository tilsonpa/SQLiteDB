package com.instinctcoder.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentRepo {
    private com.instinctcoder.sqlitedb.DBHelper dbHelper;

    public StudentRepo(Context context) {
        dbHelper = new com.instinctcoder.sqlitedb.DBHelper(context);
    }

    public int insert(com.instinctcoder.sqlitedb.Student student) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(com.instinctcoder.sqlitedb.Student.KEY_age, student.age);
        values.put(com.instinctcoder.sqlitedb.Student.KEY_email,student.email);
        values.put(com.instinctcoder.sqlitedb.Student.KEY_name, student.name);

        // Inserting Row
        long student_Id = db.insert(com.instinctcoder.sqlitedb.Student.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(com.instinctcoder.sqlitedb.Student.TABLE, com.instinctcoder.sqlitedb.Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(com.instinctcoder.sqlitedb.Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(com.instinctcoder.sqlitedb.Student.KEY_age, student.age);
        values.put(com.instinctcoder.sqlitedb.Student.KEY_email,student.email);
        values.put(com.instinctcoder.sqlitedb.Student.KEY_name, student.name);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(com.instinctcoder.sqlitedb.Student.TABLE, values, com.instinctcoder.sqlitedb.Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>>  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                com.instinctcoder.sqlitedb.Student.KEY_ID + "," +
                com.instinctcoder.sqlitedb.Student.KEY_name + "," +
                com.instinctcoder.sqlitedb.Student.KEY_email + "," +
                com.instinctcoder.sqlitedb.Student.KEY_age +
                " FROM " + com.instinctcoder.sqlitedb.Student.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(com.instinctcoder.sqlitedb.Student.KEY_ID)));
                student.put("name", cursor.getString(cursor.getColumnIndex(com.instinctcoder.sqlitedb.Student.KEY_name)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }

    public com.instinctcoder.sqlitedb.Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                com.instinctcoder.sqlitedb.Student.KEY_ID + "," +
                com.instinctcoder.sqlitedb.Student.KEY_name + "," +
                com.instinctcoder.sqlitedb.Student.KEY_email + "," +
                com.instinctcoder.sqlitedb.Student.KEY_age +
                " FROM " + com.instinctcoder.sqlitedb.Student.TABLE
                + " WHERE " +
                com.instinctcoder.sqlitedb.Student.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        com.instinctcoder.sqlitedb.Student student = new com.instinctcoder.sqlitedb.Student();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(com.instinctcoder.sqlitedb.Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(com.instinctcoder.sqlitedb.Student.KEY_name));
                student.email  =cursor.getString(cursor.getColumnIndex(com.instinctcoder.sqlitedb.Student.KEY_email));
                student.age =cursor.getInt(cursor.getColumnIndex(com.instinctcoder.sqlitedb.Student.KEY_age));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }

}