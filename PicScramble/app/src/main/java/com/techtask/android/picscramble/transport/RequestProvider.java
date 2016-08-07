package com.techtask.android.picscramble.transport;

import com.techtask.android.picscramble.listener.ResponseListener;
import com.techtask.android.picscramble.transport.response.FlickrResponseHandler;

/**
 * Handles the remote api requests.
 */
public class RequestProvider {
    private static RequestProvider provider;
    private RetrofitRequestHandler request;

    private RequestProvider() {
        request = new RetrofitRequestHandler();
    }

    public static RequestProvider getProvider() {
        if (provider == null) {
            provider = new RequestProvider();
        }
        return provider;
    }

    /**
     * Calls the flickr public photo feeds api.
     *
     * @param listener {@Link ResponseListener}.
     */
    public void fetchFlickrPublicPhotosApi(final ResponseListener listener) {
        request.loadPhotos(new FlickrResponseHandler(listener));
    }
}
