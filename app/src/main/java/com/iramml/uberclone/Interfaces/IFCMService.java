package com.iramml.uberclone.Interfaces;

import com.iramml.uberclone.Model.FCMResponse;
import com.iramml.uberclone.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
        "Content-Type:application/json",
        "Authorization:key=AAAAuUdKoGQ:APA91bGMRptBQTSX9uakP-vcphxWkvb13aNkclnz1fxNlaPl9sCsWSQj5ficl9VklSjqujC7BXIFHy_qfwnEyBnomLAwj2Ijjmcq2jkgzaETLXzTMxl4DkLXgiqmkzcSWCyR5aVrG6O-"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);
}
