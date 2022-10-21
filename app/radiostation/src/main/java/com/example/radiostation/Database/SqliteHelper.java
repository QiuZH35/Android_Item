package com.example.radiostation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.radiostation.RadioInfo.RadioInfo;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {


    private static final String DB_NAME="RadioInfo.db";
    private static final String TB_NAME="radio_info";
    private static final int DB_VERSION=1;

    private static SqliteHelper mHelper=null;
    private SQLiteDatabase mRDB=null;
    private SQLiteDatabase mWDB=null;

    //private 单例模式
    private SqliteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    //利用单例模式获取数据库帮助器的唯一实例
    public static SqliteHelper getInstance(Context context){
        if(mHelper==null)
        {
            mHelper=new SqliteHelper(context);
        }
        return mHelper;
    }


    public SqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //获取数据库读连接
    public SQLiteDatabase getReadDBLink()
    {
        if(mRDB==null || !mRDB.isOpen())
        {
            mRDB=mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    //获取数据库写连接
    public SQLiteDatabase getWriteDBLink()
    {
        if(mWDB==null || !mWDB.isOpen())
        {
            mWDB=mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    //关闭数据库读写连接
    public void CloseDBLink()
    {
            if(mRDB !=null && !mRDB.isOpen())
            {
                mRDB.close();;
                mRDB=null;
            }
            if(mWDB !=null && !mWDB.isOpen())
            {
                mWDB.close();;
                mWDB=null;
            }
    }

  //插入数据
    public long insert(RadioInfo radioInfo)
    {
        ContentValues values=new ContentValues();
        values.put("radioName",radioInfo.getRadioName());
        values.put("radioRate",radioInfo.getRadioRate());
        values.put("radioInfo",radioInfo.getRadioNumber());
        values.put("radioImage ",radioInfo.getImageId());
        values.put("radioCount",radioInfo.getRadioCount());
        return mWDB.insert(TB_NAME,null,values);
    }

        //删除
    public long deleteAll()
    {
        return mWDB.delete(TB_NAME,"1=1",null);
    }

    public long deleteByName(String name,String rate)
    {
        return mWDB.delete(TB_NAME,"radioname=? and radiorate=?",new String[]{name,rate});
    }

    //更新
    public long update(RadioInfo radioInfo)
    {
        ContentValues values=new ContentValues();
        values.put("radioName",radioInfo.RadioName);
        values.put("radioRate",radioInfo.RadioRate);
        values.put("radioInfo",radioInfo.RadioInfo);
        values.put("radioImage ",radioInfo.ImageId);
        return mWDB.update(TB_NAME,values,"Radioname=? or Radiorate=?",new String[]{radioInfo.RadioName,radioInfo.RadioRate});
    }

    //查询
    public List<RadioInfo> queryAll()
    {
        List<RadioInfo> list=new ArrayList<>();
        Cursor cursor= mWDB.query(TB_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            RadioInfo user=new RadioInfo();
            user.RadioName=cursor.getString(1);
            user.RadioRate=cursor.getString(2);
            user.RadioInfo=cursor.getString(3);
            user.ImageId=cursor.getInt(4);
            user.radioCount=cursor.getString(5);
            list.add(user);
        }
        return list;
    }
    //查询数据的行数
    public long rowfind()
    {
        int i=0;
        Cursor cursor= mWDB.query(TB_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext())
        {   i+=1;

            return i;
        }
        return i;
    }

    //根据电台名查询
    public List<RadioInfo> queryByName(String findName)
    {
        List<RadioInfo> list=new ArrayList<>();
        Cursor cursor= mWDB.query(TB_NAME,null,"Radioname=? or RadioRate=? ",new String[]{findName,findName},null,null,null);
        while(cursor.moveToNext())
        {
            RadioInfo user=new RadioInfo();
            user.RadioName=cursor.getString(1);
            user.RadioRate=cursor.getString(2);
            user.RadioInfo=cursor.getString(3);
            user.ImageId=cursor.getInt(4);
            user.radioCount=cursor.getString(5);
            list.add(user);
        }
        return list;

    }
    public List<RadioInfo> queryByNameAndRate(String findName)
    {
        List<RadioInfo> list=new ArrayList<>();
        Cursor cursor= mWDB.query(TB_NAME,null,"Radioname like ? or RadioRate like ? or radioInfo like ?",new String[]{"%"+findName+"%","%"+findName+"%","%"+findName+"%"},null,null,null);
        while(cursor.moveToNext())
        {
            RadioInfo user=new RadioInfo();
            user.RadioName=cursor.getString(1);
            user.RadioRate=cursor.getString(2);
            user.RadioInfo=cursor.getString(3);
            user.ImageId=cursor.getInt(4);
            user.radioCount=cursor.getString(5);
            list.add(user);

        }
        return list;

    }




    @Override   //创建表
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "+TB_NAME+"( id integer primary key autoincrement,"+"radioName varchar not null UNIQUE, radioRate varchar not null UNIQUE, radioInfo varchar not null,radioImage int not null,radioCount varchar not null);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
