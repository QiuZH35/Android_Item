package com.example.newsearchview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private List<program_template> program;
    private RecyclerView recyclerView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.Rv_recyclerview);
        searchView=findViewById(R.id.Search_view);
        templateInit();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Myadapter myadapter= new Myadapter(program);
        recyclerView.setAdapter(myadapter);
    }
    private void templateInit()
    {
        program=new ArrayList<>();
        program.add(new program_template(R.drawable.img,"上海","88.8\t\t\t\t"+"15"));
        program.add(new program_template(R.drawable.img_1,"广西","88.8\t\t\t\t"+"15"));
        program.add(new program_template(R.drawable.img_2,"北京","88.8\t\t\t\t"+"15"));
        program.add(new program_template(R.drawable.img_3,"珠江","88.8\t\t\t\t"+"15"));

    }

}