package com.techtask.android.picscramble.listener;

import com.techtask.android.picscramble.transport.response.FailureResponse;
import com.techtask.android.picscramble.transport.response.SuccessResponse;

public interface ResponseListener {
    void onSuccess(SuccessResponse response);
    void onFailure(FailureResponse response);
}
