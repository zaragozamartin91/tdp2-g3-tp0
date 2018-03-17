package com.tdp2.ghsz.tp0.util;

public class TaskResponse<T> {
    public final boolean success;
    public final String msg;
    public final T data;

    public TaskResponse(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
