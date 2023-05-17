package org.example;

import com.google.gson.JsonObject;

public class OutputSuccess {
    private boolean success;
    private JsonObject data;

    public void setSuccess(boolean success) {this.success = success;}
    public void setData(JsonObject data) {this.data = data;}

    public boolean getSuccess() {return success;}
    public JsonObject getData() {return data;}

    @Override
    public String toString() {
        return "OutputSuccess{" +
                "success='" + success + '\'' +
                ", data=" + data +
                '}';
    }
}