package org.example;

public class OutputFail {
    private boolean success;
    private String data;

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public void setData(String data) {
        this.data = data;
    }

    public boolean getSuccess() {
        return success;
    }
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Output{" +
                "success='" + success + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}