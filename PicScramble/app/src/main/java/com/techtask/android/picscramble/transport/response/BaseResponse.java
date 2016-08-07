package com.techtask.android.picscramble.transport.response;

public abstract class BaseResponse {
    private Object response;

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
