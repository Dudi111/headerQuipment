package com.example.appdemo3.uiLoginScreen.network

import com.example.appdemo3.vehicalReportUi.shiftmodel.VehicleReport
import com.example.appdemo3.uiLoginScreen.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {



    @FormUrlEncoded
    @POST("token")
    @Headers(
        "RequestAPIClient:true",
        "Content-Type:application/x-www-form-urlencoded"
    )

    fun getLogindetail(
        @Field("username") username : String?,
        @Field("password") password : String?,
        @Field("grant_type") grant_type : String?
    ): Call<LoginResponse>


    @FormUrlEncoded
    @POST("api/boordComputer/GetVicinityVehicles")

    fun getShiftDetails(
        @Field("Latitude") Latitude : String?,
        @Field("RequestTime") RequestTime : String?,
        @Field("Longitude") Longitude : String?,
        @Field("Radius") Radius : Int?
    ):Call<VehicleReport>
}