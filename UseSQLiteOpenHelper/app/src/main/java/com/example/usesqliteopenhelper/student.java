package com.example.usesqliteopenhelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/23.
 */
public class student extends Activity {
    private MySqliteHelper mMySqliteHelper;
    private SQLiteDatabase db;
    private ArrayList<student_info> studentlist;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_open_helper);
        
        //创建或打开数据库
        mMySqliteHelper = new MySqliteHelper(this);
        db = mMySqliteHelper.getReadableDatabase();
        studentlist = new ArrayList<>();
        
        //扫描数据库,将数据库信息放入studentlist
        String[] columns = {MySqliteHelper.ID, MySqliteHelper.NAME, MySqliteHelper.COURSE, MySqliteHelper.SCORE};
        Cursor cursor = db.query(MySqliteHelper.TABLE_NAME, columns, null, null, null, null, null);
        
        if (cursor != null && cursor.moveToFirst()) {
            do {
                student_info student = new student_info();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.ID)));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.NAME)));
                student.setCourse(cursor.getString(cursor.getColumnIndexOrThrow(MySqliteHelper.COURSE)));
                student.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(MySqliteHelper.SCORE)));
                studentlist.add(student);
            } while (cursor.moveToNext());
        }
        
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        //获取ListView,并通过Adapter把studentlist的信息显示到ListView
        listView = findViewById(R.id.student_lv);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return studentlist.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if(convertView == null) {
                    view = View.inflate(getBaseContext(), R.layout.studentlayout, null);
                } else {
                    view = convertView;
                }

                student_info student = studentlist.get(position);
                TextView id = view.findViewById(R.id.stu_number);
                TextView name = view.findViewById(R.id.stu_name);
                TextView course = view.findViewById(R.id.stu_age);
                
                id.setText(String.valueOf(student.getId()));
                name.setText(student.getName());
                course.setText(student.getCourse());
                
                return view;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        });
    }
}