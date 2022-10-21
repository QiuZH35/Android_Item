package com.example.radiostation.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.radiostation.RadioInfo.ProgramInfo;
import com.example.radiostation.RadioInfo.RadioInfo;

import java.util.ArrayList;
import java.util.List;

public class Radio_ProgramData extends SQLiteOpenHelper {

    private static final String DB_NAME="Program.db";
    private static final String TB_NAME="program_info";
    private static final int DB_VERSION=1;

    private static Radio_ProgramData mHelper=null;
    private SQLiteDatabase mRDB=null;
    private SQLiteDatabase mWDB=null;


    public Radio_ProgramData(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public static Radio_ProgramData getInstance(Context context){
        if(mHelper==null)
        {
            mHelper=new Radio_ProgramData(context);
        }
        return mHelper;
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
    public long insert(ProgramInfo programInfo)
    {
        ContentValues values=new ContentValues();
        values.put("programName",programInfo.getProgram_name());
        values.put("programHost",programInfo.getProgram_host());
        values.put("programListen",programInfo.getProgram_listen());
        values.put("radioName",programInfo.getRadio_name());
        return mWDB.insert(TB_NAME,null,values);
    }

    //删除数据
    public long delete(String name,String findRadioname)
    {
        return mWDB.delete(TB_NAME,"programName=? and radioName =? ",new String[]{name,findRadioname});
    }

    //删除全部
    public long deleteAllByName(String radioname)
    {
        return mWDB.delete(TB_NAME," radioName=?",new String[]{radioname});
    }

    public long deleteAll()
    {
        return mWDB.delete(TB_NAME," 1=1",null);

    }
    //更新数据
    public long update(ProgramInfo programInfo)
    {
        ContentValues values=new ContentValues();
        values.put("programName",programInfo.program_name);
        values.put("programHost",programInfo.program_host);
        values.put("programListen",programInfo.program_listen);
        values.put("radioName",programInfo.Radio_name);
        return mWDB.update(TB_NAME,values," radioName=? and programname=?",new String[]{programInfo.Radio_name,programInfo.program_name});
    }


    public long rowFind(String findRadioName)
    {
        int i=0;
        Cursor cursor= mWDB.query(TB_NAME,null,"radioName=?",new String []{findRadioName},null,null,null,null);
        while(cursor.moveToNext())
        {
            i+=1;
        }
        return i;
    }

    //查询所有数据
    public List<ProgramInfo> queryAll(String findRadioName)
    {
        List<ProgramInfo> list=new ArrayList<>();
        Cursor cursor= mWDB.query(TB_NAME,null,"radioName=?",new String []{findRadioName},null,null,null,null);
        while(cursor.moveToNext())
        {
           ProgramInfo programInfo=new ProgramInfo();
           programInfo.program_name=cursor.getString(1);
           programInfo.program_host=cursor.getString(2);
           programInfo.program_listen=cursor.getString(3);
            list.add(programInfo);
        }
        return list;
    }

    //根据名字查询
    public List<ProgramInfo> queryByName(String findRadioName,String program)
    {
        List<ProgramInfo> list=new ArrayList<>();
        Cursor cursor= mWDB.query(TB_NAME,null,"radioName=? and programName=?",new String []{findRadioName,program},null,null,null,null);
        while(cursor.moveToNext())
        {
            ProgramInfo programInfo=new ProgramInfo();
            programInfo.program_name=cursor.getString(1);
            programInfo.program_host=cursor.getString(2);
            programInfo.program_listen=cursor.getString(3);
            list.add(programInfo);
        }
        return list;
    }


    //按名字或主持人查询
    public List<ProgramInfo> queryByNameOrHost(String name,String radioname)
    {
        List<ProgramInfo> list=new ArrayList<>();
        Cursor cursor= mWDB.query(TB_NAME,null,"(programName like ? or programHost like ?) and radioName=?",new String[]{"%"+name+"%","%"+name+"%",radioname},null,null,null,null);
        while(cursor.moveToNext())
        {
            ProgramInfo programInfo=new ProgramInfo();
            programInfo.program_name=cursor.getString(1);
            programInfo.program_host=cursor.getString(2);
            programInfo.program_listen=cursor.getString(3);
            programInfo.Radio_name=cursor.getString(4);
            list.add(programInfo);
        }
        return list;
    }



    public Radio_ProgramData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override  //创建表
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS "+TB_NAME+"( id integer primary key autoincrement,"+"programName varchar not null UNIQUE, programHost varchar not null , programListen varchar not null ,radioName varchar not null);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
