package com.uxi.fragmentsdemo.API;

import com.uxi.fragmentsdemo.Model.*;
import com.uxi.fragmentsdemo.Model.LoginRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("order/TestLogin")
    Call<JsonObject> loginUser(@Body LoginRequest loginRequest);

    @POST("order/create_user")
    Call<SignUpDetails> SaveOrder(

            @Body ResponseSignUp create

    );


}
