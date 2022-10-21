package com.example.radiostation.RadioInfo;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

public class RadioInfo {
    public int ImageId;

    public String RadioName;
    public String RadioRate;
    public String RadioInfo;
    public String radioCount;

    public RadioInfo(int imageId, String radioName, String radioRate, String radioInfo, String radioCount) {
        ImageId = imageId;
        RadioName = radioName;
        RadioRate = radioRate;
        RadioInfo = radioInfo;
        this.radioCount = radioCount;
    }

    public String getRadioCount() {
        return radioCount;
    }

    public void setRadioCount(String radioCount) {
        this.radioCount = radioCount;
    }

    public RadioInfo()
    {

    }

    public RadioInfo(String radioName, String radioRate, String radioNumber)
    {
        RadioName = radioName;
        RadioRate = radioRate;
        RadioInfo = radioNumber;
    }
    public RadioInfo(int imageId, String radioName, String radioRate, String radioNumber) {
        ImageId = imageId;
        RadioName = radioName;
        RadioRate = radioRate;
        RadioInfo = radioNumber;
    }


    @Override
    public String toString() {
        return "RadioInfo{" +
                ", RadioName='" + RadioName + '\'' +
                ", RadioRate='" + RadioRate + '\'' +
                ", RadioNumber='" + RadioInfo + '\'' +
                '}';
    }

    public String NametoString() {
        return   RadioName;
    }
    public String RatetoString() {
        return   RadioRate;
    }
    public String InfotoString() {
        return   RadioInfo;
    }
    public String radioCountToString(){return radioCount;}
    public int imageIdToString(){
        return ImageId;
    }



    public String rateToString()
    {
        return RadioRate;
    }
    public String numberToString()
    {
        return RadioInfo;
    }

    public String getRadioInfo() {
        return RadioInfo;
    }

    public void setRadioInfo(String radioInfo) {
        RadioInfo = radioInfo;
    }

    public int getImageId() {
        return ImageId;
    }


    public String getRadioName() {
        return RadioName;
    }

    public void setRadioName(String radioName) {
        RadioName = radioName;
    }

    public String getRadioRate() {
        return RadioRate;
    }

    public void setRadioRate(String radioRate) {
        RadioRate = radioRate;
    }

    public String getRadioNumber() {
        return RadioInfo;
    }

    public void setRadioNumber(String radioNumber) {
        RadioInfo = radioNumber;
    }


}
