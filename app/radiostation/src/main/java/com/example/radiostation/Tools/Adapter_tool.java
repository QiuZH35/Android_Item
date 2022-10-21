package com.example.radiostation.Tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.radiostation.Database.Radio_ProgramData;
import com.example.radiostation.R;
import com.example.radiostation.RadioInfo.RadioInfo;

import java.util.List;
import java.util.Random;

public class Adapter_tool extends ArrayAdapter<RadioInfo> {
    private Radio_ProgramData programData;

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
    Random r=new Random();
    private  int ResourceId;


    public Adapter_tool(@NonNull Context context, int resource, @NonNull List<RadioInfo> objects) {
        super(context, resource, objects);
        ResourceId=resource;
    }

    @NonNull
    @Override    //各个控件之间初始化
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RadioInfo radioInfo=getItem(position);

        View view= LayoutInflater.from(getContext()).inflate(ResourceId,parent,false);   //渲染View
        ImageView imageView=view.findViewById(R.id.Image_name);
        TextView radio_name=view.findViewById(R.id.radio_name);
        TextView radio_rate=view.findViewById(R.id.radio_rate);
        TextView radio_number=view.findViewById(R.id.radio_number);
        TextView radio_info=view.findViewById(R.id.radio_info);


        imageView.setImageResource(ImageArrays[radioInfo.getImageId()]);
        radio_name.setText(radioInfo.getRadioName());
        radio_rate.setText(radioInfo.getRadioRate());
        radio_number.setText(radioInfo.radioCountToString());
        radio_info.setText(radioInfo.getRadioInfo());

        return view;
    }
}
