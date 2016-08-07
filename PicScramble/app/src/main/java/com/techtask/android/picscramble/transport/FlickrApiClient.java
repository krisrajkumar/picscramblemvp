package com.techtask.android.picscramble.transport;

import com.techtask.android.picscramble.transport.model.PhotoResult;

import retrofit2.Call;
import retrofit2.http.POST;

interface FlickrApiClient {
    @POST("feeds/photos_public.gne?format=json&nojsoncallback=?")
    Call<PhotoResult> loadPhotos();
}
