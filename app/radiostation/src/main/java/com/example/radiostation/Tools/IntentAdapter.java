package com.example.radiostation.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.radiostation.R;
import com.example.radiostation.RadioInfo.ProgramInfo;


import java.util.List;

public class IntentAdapter extends ArrayAdapter<ProgramInfo> {

    private  int ResourceId;

    public IntentAdapter(@NonNull Context context, int resource, @NonNull List<ProgramInfo> objects) {
        super(context, resource, objects);
        ResourceId=resource;
    }

    //初始化控件
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProgramInfo programInfo=getItem(position);

        View view= LayoutInflater.from(getContext()).inflate(ResourceId,parent,false);
        TextView programName=view.findViewById(R.id.programName);
        TextView programHost=view.findViewById(R.id.programHost);
        TextView programListen=view.findViewById(R.id.programListen);

        programName.setText(programInfo.getProgram_name());
        programHost.setText(programInfo.getProgram_host());
        programListen.setText(programInfo.getProgram_listen());

        return view;
    }


}
