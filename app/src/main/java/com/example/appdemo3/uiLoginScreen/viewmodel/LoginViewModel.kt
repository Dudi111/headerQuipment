package com.example.appdemo3.uiLoginScreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdemo3.uiLoginScreen.model.LoginResponse
import com.example.appdemo3.uiLoginScreen.network.NewNetworkcall
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {

    private var loginMutableData= MutableLiveData<LoginResponse>()
    val loginliveData: LiveData<LoginResponse> = loginMutableData

    private var failmutableLiveData = MutableLiveData<String>()
    val failliveData: LiveData<String> = failmutableLiveData



    fun getLoginResponse( username:String?, password: String?, grant:String?){

        NewNetworkcall.getapiservice().getLogindetail(username, password, grant).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.body() != null){
                    loginMutableData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                failmutableLiveData.postValue("ERROR")
            }

        })

    }


}