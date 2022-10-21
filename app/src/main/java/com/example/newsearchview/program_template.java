package com.example.newsearchview;

import java.util.List;

public class program_template {
    private int Image_view;
    private String program_name,program_number;

    public void setImage_view(int image_view) {
        Image_view = image_view;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public void setProgram_number(String program_number) {
        this.program_number = program_number;
    }

    public program_template(int image_view, String program_name, String program_number) {
        Image_view = image_view;
        this.program_name = program_name;
        this.program_number = program_number;
    }

    public int getImage_view() {
        return Image_view;
    }

    public String getProgram_name() {
        return program_name;
    }

    public String getProgram_number() {
        return program_number;
    }
}
