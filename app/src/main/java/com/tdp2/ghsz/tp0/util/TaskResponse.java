package com.tdp2.ghsz.tp0.util;

public class TaskResponse<T> {
    public final boolean success;
    public final int code;
    public final T data;

    public TaskResponse(boolean success, int code, T data) {
        this.success = success;
        this.code = code;
        this.data = data;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "success=" + success +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
