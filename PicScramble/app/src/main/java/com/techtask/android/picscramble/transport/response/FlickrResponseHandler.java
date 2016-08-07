package com.techtask.android.picscramble.transport.response;

import com.techtask.android.picscramble.listener.ResponseListener;
import com.techtask.android.picscramble.model.PhotoInfo;
import com.techtask.android.picscramble.transport.model.Item;
import com.techtask.android.picscramble.transport.model.PhotoResult;

import java.util.ArrayList;
import java.util.List;

public class FlickrResponseHandler implements ResponseHandler {
    private ResponseListener responseListener;

    public FlickrResponseHandler(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void handleSuccess(BaseResponse response) {
        SuccessResponse successResponse = (SuccessResponse) response;
        PhotoResult result = (PhotoResult) successResponse.getResponse();
        List<PhotoInfo> infoList = new ArrayList<>();
        int i = 0;
        for (Item item : result.getItems()) {
            infoList.add(new PhotoInfo(i, item.getTitle(), item.getMedia().getM()));
            i++;
        }
        successResponse.setData(infoList);
        responseListener.onSuccess(successResponse);
    }

    @Override
    public void handleFailure(BaseResponse response) {
        responseListener.onFailure((FailureResponse) response);
    }
}
