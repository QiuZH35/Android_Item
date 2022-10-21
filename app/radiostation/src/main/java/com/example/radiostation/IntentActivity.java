package com.example.radiostation;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.radiostation.Database.Radio_ProgramData;
import com.example.radiostation.Database.SqliteHelper;
import com.example.radiostation.RadioInfo.ProgramInfo;
import com.example.radiostation.RadioInfo.RadioInfo;
import com.example.radiostation.Tools.IntentAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class IntentActivity extends AppCompatActivity {

    private SearchView searchView;
    private ImageView imageView;
    private TextView radioName;
    private TextView radioRate;
    private TextView radioInfo;
    private ListView program_listView;
    private FloatingActionButton radioInfo_introduce_add;
    private FloatingActionButton radioInfo__introduce_update;
    private FloatingActionButton radioInfo_introduce_delete;
    private RelativeLayout imageView_null;


    private Radio_ProgramData mHelper;
    private IntentAdapter adapter;
    private List<ProgramInfo> programInfo_list=new ArrayList<ProgramInfo>();

    private String radioName_program="";
    public boolean returnStatus=false;
    private String oldProgramName;
    private String oldProgramHost;
    private String oldProgramListen;
    int []ImageArrays={
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3,
            R.drawable.img_4,
            R.drawable.img_5,
            R.drawable.img_6,
            R.drawable.img_7,
            R.drawable.img_8,
            R.drawable.img_9,
            R.drawable.img_10,
            R.drawable.img_11,
            R.drawable.img_12,
            R.drawable.img_13,
            R.drawable.img_14,
            R.drawable.img_15,
    };

    @Override
    protected void onStart() {
        super.onStart();
        mHelper=Radio_ProgramData.getInstance(this);
        mHelper.getReadDBLink();
        mHelper.getWriteDBLink();
        refresh_Data();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.CloseDBLink();
        refresh_Data();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_introduce);

        imageView_null=findViewById(R.id.program_imageView);
        searchView = findViewById(R.id.radio_introduce_search);
        imageView = findViewById(R.id.radio_introduce_Image);
        radioName = findViewById(R.id.radio_introduce_name);
        radioRate = findViewById(R.id.radio_introduce_rate);
        radioInfo = findViewById(R.id.radio_introduce_info);
        program_listView = findViewById(R.id.Radio_introduce_ListView);


        imageView_null.setBackgroundResource(R.drawable.r_c2);

        //接收数据
        Intent intent=getIntent();
        intent.getData();
        radioName.setText(intent.getStringExtra("radioName"));
        radioRate.setText(intent.getStringExtra("radioRate"));
        radioInfo.setText(intent.getStringExtra("radioinfo"));
        imageView.setImageResource(ImageArrays[Integer.parseInt(intent.getStringExtra("imageId"))]);
        radioName_program=radioName.getText().toString();



        //设置适配器
        adapter=new IntentAdapter(this,R.layout.program_provide,programInfo_list);
        program_listView.setAdapter(adapter);



        radioInfo_introduce_add = findViewById(R.id.action_add_radioData);
        radioInfo__introduce_update = findViewById(R.id.action_Update_radioData);
        radioInfo_introduce_delete = findViewById(R.id.action_DeleteAll_radioData);

        //添加
        radioInfo_introduce_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogShow(v);
                refresh_Data();
                imageView_null.setBackgroundResource(R.drawable.isnull);
            }
        });

        //删除全部
        radioInfo_introduce_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(IntentActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否确定清空!");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( mHelper.deleteAllByName(radioName_program)>0)
                        {
                            imageView_null.setBackgroundResource(R.drawable.r_c2);
                            refresh_Data();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });


        //查询
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                programInfo_list.clear();
                adapter.notifyDataSetChanged();
                program_listView.setAdapter(adapter);
                List<ProgramInfo> list_db=mHelper.queryByNameOrHost(searchView.getQuery().toString(), radioName_program);
                for(ProgramInfo u:list_db)
                {
                    String programs=u.nameToString();
                    String programHost=u.hostToString();
                    String programListen=u.listenToString();
                    programInfo_list.add(new ProgramInfo(programs,programHost,programListen));
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                refresh_Data();
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programInfo_list.clear();
            }
        });




        //超链接
        program_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView programHosts=view.findViewById(R.id.programHost);
                Uri uri=Uri.parse("https://www.baidu.com/s?wd="+programHosts.getText().toString());
                Intent intent1=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent1);
            }
        });

        program_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                View newView=LayoutInflater.from(view.getContext()).inflate(R.layout.select_function,null,false);

               ImageButton btn_delete=newView.findViewById(R.id.Delete_function);
               ImageButton btn_update=newView.findViewById(R.id.Update_function);


                TextView newProgramName=view.findViewById(R.id.programName);
                TextView newProgramHost=view.findViewById(R.id.programHost);
                TextView newProgramListen=view.findViewById(R.id.programListen);

                String  ItemProgramName=newProgramName.getText().toString();
                oldProgramName = "";
                oldProgramHost = "";
                oldProgramListen = "";


                List<ProgramInfo> listChange=mHelper.queryByName( radioName_program,ItemProgramName);
                for (ProgramInfo u:listChange
                     ) {
                    oldProgramName =u.nameToString();
                    oldProgramHost =u.hostToString();
                    oldProgramListen =u.listenToString();
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(IntentActivity.this);
                builder.setView(newView);

                //删除一条数据
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mHelper.delete(ItemProgramName,radioName_program)>0)
                        {

                            Toast.makeText(IntentActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            refresh_Data();
                            if(mHelper.rowFind(radioName_program)<0)
                            {
                                imageView_null.setBackgroundResource(R.drawable.r_c2);
                            }
                        }

                    }
                });

                //更新数据
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder DateUpdate=new AlertDialog.Builder(IntentActivity.this);
                        View viewDate=LayoutInflater.from(v.getContext()).inflate(R.layout.radio_program_addinfo,null,false);

                        TextView newRadioName=viewDate.findViewById(R.id.inputName);
                        TextView newProgramName=viewDate.findViewById(R.id.inputProgramName);
                        TextView newProgramHost=viewDate.findViewById(R.id.inputHost);
                        TextView newProgramListen=viewDate.findViewById(R.id.inputListen);


                        newRadioName.setText(radioName_program);
                        newProgramName.setText(oldProgramName);
                        newProgramHost.setText(oldProgramHost);
                        newProgramListen.setText(oldProgramListen);
                        DateUpdate.setView(viewDate);

                        DateUpdate.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String old_programName=newProgramName.getText().toString();
                                String old_programHost=newProgramHost.getText().toString();
                                String old_programListen=newProgramListen.getText().toString();
                                if(old_programListen!=null&&old_programName!=null&&old_programHost!=null && (isNumeric(old_programListen) || Double.parseDouble(old_programListen)>0) )
                                {
                                    ProgramInfo newProgramInfo=new ProgramInfo(old_programName,old_programHost,old_programListen,radioName_program);
                                    if(mHelper.update(newProgramInfo)>0)
                                    {
                                        Toast.makeText(IntentActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        refresh_Data();
                                        dialog.dismiss();
                                    }else
                                    {
                                        Toast.makeText(IntentActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        DateUpdate.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                            }
                        });

                        DateUpdate.show();
                    }

                });
                builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    //显示警告框
    private void DialogShow(View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.radio_program_addinfo,null,false);
        EditText radioNames=view.findViewById(R.id.inputName);
        EditText programName=view.findViewById(R.id.inputProgramName);
        EditText programHost=view.findViewById(R.id.inputHost);
        EditText programListen=view.findViewById(R.id.inputListen);
        radioNames.setText(radioName_program);
        builder.setView(view);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=radioNames.getText().toString();
                String program=programName.getText().toString();
                String Host=programHost.getText().toString();
                String Listen=programListen.getText().toString();

                ProgramInfo programInfo;
                if((name !=null&&program!=null&&Host!=null&&Listen!=null)&&name.equals(radioName_program.trim()) && (isNumeric(Listen)|| Double.parseDouble(Listen)>0) )
                {

                    programInfo=new ProgramInfo(program,Host,Listen,name);
                    if(mHelper.insert(programInfo)>0)
                    {
                        Toast.makeText(IntentActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        programInfo_list.clear();
                        adapter.notifyDataSetChanged();
                        program_listView.setAdapter(adapter);
                        refresh_Data();
                    }else
                    {
                        Toast.makeText(IntentActivity.this,"添加失败，节目名称是否重复!",Toast.LENGTH_SHORT).show();
                    }

                }else
                {
                    Toast.makeText(IntentActivity.this,"添加失败，请确认电台是否存在!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


    //刷新数据
    public void refresh_Data()
    {
        programInfo_list.clear();
        adapter.notifyDataSetChanged();
        program_listView.setAdapter(adapter);

        List<ProgramInfo> list_db=mHelper.queryAll(radioName.getText().toString());
        for(ProgramInfo u:list_db)
        {
            String programs=u.nameToString();
            String programHost=u.hostToString();
            String programListen=u.listenToString();
            programInfo_list.add(new ProgramInfo(programs,programHost,programListen));
        }
        if(mHelper.rowFind(radioName_program)<=0)
        {
            imageView_null.setBackgroundResource(R.drawable.r_c2);
        }else
        {
            imageView_null.setBackgroundResource(R.drawable.is_data);
        }
    }

    //删除数据
    public boolean deleteAll()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否确定清空!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if( mHelper.deleteAllByName(radioName_program)>0)
               {
                   returnStatus=true;
               }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
        return returnStatus;
    }


    //判断字符是否为数字
    public static boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }

}
