package com.techtask.android.picscramble.transport.response;

public interface ResponseHandler {
    void handleSuccess(BaseResponse response);

    void handleFailure(BaseResponse response);
}
