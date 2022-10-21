package com.example.radiostation;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import  androidx.appcompat.widget.SearchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.radiostation.Database.Radio_ProgramData;
import com.example.radiostation.Database.SqliteHelper;
import com.example.radiostation.RadioInfo.RadioInfo;
import com.example.radiostation.Tools.Adapter_tool;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Radio_ProgramData programData;
    private SqliteHelper mHepler;

    private RadioInfo radioInfo_getdata=new RadioInfo();
    private ListView listView;
    private List<RadioInfo> radioInfos=new ArrayList<RadioInfo>();
    private String s,s1,s2;

    private String radio_DBInfo=null;
    private SearchView searchView;
    private View view;
    private RelativeLayout  imageBack;
    Random r=new Random();
    private Adapter_tool adapter;
    private String itemGetName;

    private String radioID;
    private String radioRate;
    private String radioinfo;

    private int imageArrayId;
    private int[] imageArray;
    private int inamgeID;
    private int imageId;
    private String programCount;
    private String programCount1;

    @Override
    protected void onStart() {
        super.onStart();
        mHepler = SqliteHelper.getInstance(this);
        programData=Radio_ProgramData.getInstance(this);
        mHepler.getReadDBLink();
        mHepler.getWriteDBLink();
        programData.getWriteDBLink();
        programData.getReadDBLink();
        int i=0;
        ReRadioInfo();
    }
    private void ReRadioInfo()
    {
        List<RadioInfo> list_db=mHepler.queryAll();
        for (RadioInfo u: list_db
        ) {
            s=u.NametoString();
            s1=u.RatetoString();
            s2=u.InfotoString();
            RefreshListView();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHepler.CloseDBLink();
        programData.CloseDBLink();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        imageBack=findViewById(R.id.back_image);
        imageBack.setBackgroundResource(R.drawable.r_c2);
            //照片数组
        imageArray = new int[]{
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


        FloatingActionButton radioInfo_add=findViewById(R.id.action_add);
        FloatingActionButton radioInfo_Update=findViewById(R.id.action_Update);
        FloatingActionButton radioInfo_delete=findViewById(R.id.action_Delete);
        listView = findViewById(R.id.List_view);

        radioInfo_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioInfos.clear();
                adapter.notifyDataSetChanged();
                ResetData();

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
            radioInfos.clear();
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            List<RadioInfo> list_db=mHepler.queryByNameAndRate(searchView.getQuery().toString());

            for (RadioInfo u: list_db
            ) {
                s=u.NametoString();
                s1=u.RatetoString();
                s2=u.InfotoString();
                programCount=programData.rowFind(s)+"";
                inamgeID=u.getImageId();
                radioInfos.add(new RadioInfo(inamgeID,s,s1,s2,programCount));
            }
            return false;
        }
    });
    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {

            RefreshListView();
            return false;
        }
    });
    searchView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            radioInfos.clear();
        }
    });

        //删除全部
        radioInfo_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否确定清空");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(mHepler.deleteAll()>0)
                        {
                            Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                            if(programData.deleteAll()>0)
                            {

                            }
                            RefreshListView();

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
        });


        //添加数据
        radioInfo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                view = LayoutInflater.from(v.getContext()).inflate(R.layout.radio_addinfo,null,false);
                View viewToPhoto = LayoutInflater.from(v.getContext()).inflate(R.layout.radio_provide,null,false);
                EditText InputName= view.findViewById(R.id.inputName);
                EditText InputRate= view.findViewById(R.id.inputRate);
                EditText InputInfo= view.findViewById(R.id.inputInfo);
                ImageView Image= view.findViewById(R.id.inputImage);

                imageArrayId = r.nextInt(15);
                Image.setImageResource(imageArray[imageArrayId]);

                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name=InputName.getText().toString().trim();
                        String Rate=InputRate.getText().toString().trim();
                        String radioInfos1=InputInfo.getText().toString().trim();
                        programCount = programData.rowFind(name)+"";
                        RadioInfo radioInfo;
                        if(name !="" && Rate !="" && radioInfos1!=""  && (isNumeric(Rate)||Double.parseDouble(Rate)>0)) {

                            radioInfo   = new RadioInfo(imageArrayId,name, Rate, radioInfos1,programCount);
                        if(mHepler.insert(radioInfo)>0)
                        {
                            if(mHepler.rowfind()<0){
                                imageBack.setBackgroundResource(R.drawable.r_c2);
                            }

                            Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            radioInfos.clear();
                            adapter.notifyDataSetChanged();
                            listView.setAdapter(adapter);
                            ResetData();
                        }else
                        {
                            Toast.makeText(MainActivity.this,"添加失败，请检查电台名称或频率是否重复!",Toast.LENGTH_SHORT).show();
                        }
                        }else
                        {
                            Toast.makeText(MainActivity.this,"添加失败！",Toast.LENGTH_SHORT).show();
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


        adapter = new Adapter_tool(this,R.layout.radio_provide,radioInfos);
        listView.setAdapter(adapter);

        //长按触发事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView view3Name=view.findViewById(R.id.radio_name);//获取Item中的数据
                itemGetName = view3Name.getText().toString();
                List<RadioInfo> lists1=mHepler.queryByName(itemGetName);
                for (RadioInfo u:lists1
                ) {
                    radioID =u.NametoString();
                    radioRate =u.rateToString();
                    radioinfo =u.InfotoString();
                }
                View view1=LayoutInflater.from(view.getContext()).inflate(R.layout.select_function,null,false);
                ImageButton btn_update=view1.findViewById(R.id.Update_function);
                ImageButton  btn_delete=view1.findViewById(R.id.Delete_function);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

                builder.setView(view1);
                builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //更新
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    AlertDialog.Builder builder_Update=new AlertDialog.Builder(MainActivity.this);
                    View view2=LayoutInflater.from(view.getContext()).inflate(R.layout.radio_addinfo,null,false);

                    TextView textView_name=view2.findViewById(R.id.inputName);
                    TextView textview_rate=view2.findViewById(R.id.inputRate);
                    TextView textView_info=view2.findViewById(R.id.inputInfo);

                    textView_name.setText(radioID) ;
                    textview_rate.setText(radioRate) ;
                    textView_info.setText(radioinfo) ;
                    builder_Update.setView(view2);
                    builder_Update.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = textView_name.getText().toString();
                            String rate = textview_rate.getText().toString();
                            String info = textView_info.getText().toString();
                            if (name != null && rate != null && info != null && (isNumeric(rate)||Double.parseDouble(rate)>0)) {
                                RadioInfo radioInfo = new RadioInfo(name, rate, info);
                                if (mHepler.update(radioInfo) > 0) {
                                    Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    RefreshListView();
                                    dialog.dismiss();
                                }
                            }else
                            {
                                Toast.makeText(MainActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder_Update.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder_Update.show();
                    }
                });


                //删除
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder1=new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("提示");
                        builder1.setMessage("是否确定删除");
                        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView radioName=view .findViewById(R.id.radio_name);
                                TextView radioRate=view.findViewById(R.id.radio_rate);
                                if( mHepler.deleteByName(radioName.getText().toString(),radioRate.getText().toString())>0
                                )
                                {

                                    Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    if(programData.deleteAllByName(radioName.getText().toString())>0)
                                    {

                                    }
                                    RefreshListView();
                                }
                            }
                        });
                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder1.show();
                    }
                });

                builder.show();
                return true;
            }
        });


        //跳转
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               radioID = "";
               radioRate = "";
               radioinfo = "";
               TextView view1Name=view.findViewById(R.id.radio_name);//获取Item中的数据

               itemGetName = view1Name.getText().toString();

                List<RadioInfo> lists1=mHepler.queryByName(itemGetName);
               for (RadioInfo u:lists1
                    ) {
                   radioID =u.NametoString();
                   radioRate =u.rateToString();
                   radioinfo =u.InfotoString();
                   imageId =u.getImageId();
               }

                if(radioID !="") {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, IntentActivity.class);
                    intent.putExtra("radioName", radioID);
                    intent.putExtra("radioRate", radioRate);
                    intent.putExtra("radioinfo", radioinfo);
                    intent.putExtra("imageId",imageId+"");
                    startActivity(intent);
                }
           }
       });

    }

    private  void ResetData()
    {
    List<RadioInfo> list_db=mHepler.queryAll();
{
    if(mHepler.rowfind()<=0)
    {
        imageBack.setBackgroundResource(R.drawable.r_c2);
    }else
    {
        imageBack.setBackgroundResource(R.drawable.is_data);
    }

    for (RadioInfo u: list_db
    ) {

        s=u.NametoString();
        s1=u.RatetoString();
        s2=u.InfotoString();
        inamgeID=u.getImageId();
        programCount1=u.getRadioCount();
        programCount1=programData.rowFind(s)+"";
        radioInfos.add(new RadioInfo(inamgeID,s,s1,s2,programCount1));
    }}
}

//刷新ListView
private void RefreshListView()
{
    radioInfos.clear();
    adapter.notifyDataSetChanged();
    listView.setAdapter(adapter);
    ResetData();

}



    public static boolean isNumeric(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr<48 || chr>57)
                return false;
        }
        return true;
    }

}