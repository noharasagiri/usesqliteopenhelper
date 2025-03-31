package com.example.usesqliteopenhelper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnCreateDB;
    private Button btnInsertData;
    private MySqliteHelper mMySqliteHelper;
    private SQLiteDatabase db;
    private ListView listView;
    private ArrayList<student_info> studentlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMySqliteHelper = new MySqliteHelper(this);
        btnCreateDB = findViewById(R.id.btnCreateDB);
        btnInsertData = findViewById(R.id.btnInsertData);
        listView = findViewById(R.id.listView);
        studentlist = new ArrayList<>();

        btnCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = mMySqliteHelper.getWritableDatabase();
                TextView txv = findViewById(R.id.textView);
                txv.setText("数据库路径: " + db.getPath());
                db.close();
            }
        });

        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                displayData();
            }
        });
    }

    private void insertData() {
        db = mMySqliteHelper.getWritableDatabase();
        
        // 插入10条测试数据
        String[][] testData = {
            {"张三", "数学", "85"},
            {"李四", "英语", "92"},
            {"王五", "物理", "78"},
            {"赵六", "化学", "88"},
            {"钱七", "生物", "95"},
            {"孙八", "语文", "82"},
            {"周九", "历史", "90"},
            {"吴十", "地理", "87"},
            {"郑十一", "政治", "93"},
            {"王十二", "计算机", "96"}
        };

        for (String[] data : testData) {
            String sql = "INSERT INTO " + MySqliteHelper.TABLE_NAME + 
                        " (" + MySqliteHelper.NAME + ", " + 
                        MySqliteHelper.COURSE + ", " + 
                        MySqliteHelper.SCORE + ") VALUES (?, ?, ?)";
            db.execSQL(sql, data);
        }
        
        db.close();
    }

    private void displayData() {
        db = mMySqliteHelper.getReadableDatabase();
        String[] columns = {MySqliteHelper.ID, MySqliteHelper.NAME, MySqliteHelper.COURSE, MySqliteHelper.SCORE};
        Cursor cursor = db.query(MySqliteHelper.TABLE_NAME, columns, null, null, null, null, null);
        
        studentlist.clear();
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

        List<Map<String, String>> data = new ArrayList<>();
        for (student_info student : studentlist) {
            Map<String, String> item = new HashMap<>();
            item.put("name", student.getName());
            item.put("course", student.getCourse());
            item.put("score", String.valueOf(student.getScore()));
            data.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_1,
                new String[]{"name", "course", "score"},
                new int[]{android.R.id.text1});
        
        listView.setAdapter(adapter);
    }
}