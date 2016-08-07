package com.techtask.android.picscramble.transport;

import com.techtask.android.picscramble.transport.model.PhotoResult;
import com.techtask.android.picscramble.transport.response.FailureResponse;
import com.techtask.android.picscramble.transport.response.ResponseHandler;
import com.techtask.android.picscramble.transport.response.SuccessResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Handles the retrofit requests.
 */
public class RetrofitRequestHandler {
    private FlickrApiClient client = ServiceGenerator.createService(FlickrApiClient.class);

    public void loadPhotos(final ResponseHandler handler) {
        Call<PhotoResult> call = client.loadPhotos();
        call.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                if (response.isSuccessful()) {
                    SuccessResponse successResponse = new SuccessResponse();
                    PhotoResult result = response.body();
                    successResponse.setResponse(response.body());
                    handler.handleSuccess(successResponse);
                } else {
                    handler.handleFailure(new FailureResponse());
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                FailureResponse failureResponse = new FailureResponse();
                handler.handleFailure(failureResponse);
            }
        });
    }
}
