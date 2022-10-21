package com.example.newsearchview;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>  {
private List<program_template> program_templates;

public Myadapter(List<program_template> list){
    program_templates=list;

}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        program_template template= program_templates.get(viewType);
    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.program_item,parent,false);
        ViewHolder holder=new ViewHolder(view);/*创建缓存*/
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),template.getProgram_name(),Toast.LENGTH_SHORT).show();
            }
        });


    return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
program_template template= program_templates.get(position);
holder.program_Imageview.setImageResource(template.getImage_view());
holder.program_name.setText(template.getProgram_name());
holder.program_number.setText(template.getProgram_number());
        holder.program_Imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                View view1=LayoutInflater.from(v.getContext()).inflate(R.layout.program_item,null,false);
                ImageView imageView=view1.findViewById(R.id.Image_view);
                imageView.setImageResource(template.getImage_view());
                builder.setView(view1);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return program_templates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
ImageView  program_Imageview;
TextView program_name,program_number;
LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout=itemView.findViewById(R.id.all_program);
            program_Imageview=itemView.findViewById(R.id.Image_view);
            program_name=itemView.findViewById(R.id.program_name);
            program_number=itemView.findViewById(R.id.program_number);
        }
    }
}
