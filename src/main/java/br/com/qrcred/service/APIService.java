package br.com.qrcred.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                "Content-Type:application/json",
                "Authorization:key=AAAAhJ7OrTg:APA91bHqBruVAvQIUOkOXOG8ydDXXD6Y0uelukhUNA992ObnET3w_1n-4AjznbhTQyO1geWlksMqBYo6h4tc-8i3gZK2EWcJuQxFHFW7MFFnNZXn6uv-kuIO59nh2L8FTmDsgKer-1ic"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);

}
