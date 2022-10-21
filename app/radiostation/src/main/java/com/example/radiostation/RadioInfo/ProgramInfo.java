package com.example.radiostation.RadioInfo;

public class ProgramInfo {

    public String program_name;
    public String program_host;
    public String program_listen;
    public String Radio_name;
    public String radio_Rate;

    public String getRadio_Rate() {
        return radio_Rate;
    }

    public ProgramInfo(String program_name, String program_host, String program_listen, String radio_name, String radio_Rate) {
        this.program_name = program_name;
        this.program_host = program_host;
        this.program_listen = program_listen;
        Radio_name = radio_name;
        this.radio_Rate = radio_Rate;
    }

    public void setRadio_Rate(String radio_Rate) {
        this.radio_Rate = radio_Rate;
    }

    public String getRadio_name() {
        return Radio_name;
    }

    public void setRadio_name(String radio_name) {
        Radio_name = radio_name;
    }

    public ProgramInfo(){}
    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public String getProgram_host() {
        return program_host;
    }

    public void setProgram_host(String program_host) {
        this.program_host = program_host;
    }

    public String getProgram_listen() {
        return program_listen;
    }

    public void setProgram_listen(String program_listen) {
        this.program_listen = program_listen;
    }

    public ProgramInfo(String program_name, String program_host, String program_listen) {
        this.program_name = program_name;
        this.program_host = program_host;
        this.program_listen = program_listen;
    }

    public ProgramInfo(String program_name, String program_host, String program_listen, String radio_name) {
        this.program_name = program_name;
        this.program_host = program_host;
        this.program_listen = program_listen;
        Radio_name = radio_name;
    }


    @Override
    public String toString() {
        return "ProgramInfo{" +
                "program_name='" + program_name + '\'' +
                ", program_host='" + program_host + '\'' +
                ", program_listen='" + program_listen + '\'' +
                '}';
    }

    public String nameToString()
    {
        return program_name;
    }

    public String hostToString()
    {
        return program_host;
    }
    public String listenToString()
    {
        return program_listen;

    }
    public String RadioNameToString()
    {
        return Radio_name;
    }
}
