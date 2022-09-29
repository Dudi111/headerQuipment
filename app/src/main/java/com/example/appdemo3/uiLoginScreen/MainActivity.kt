package com.example.appdemo3.uiLoginScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appdemo3.R
import com.example.appdemo3.databinding.ActivityMainBinding
import com.example.appdemo3.vehicalReportUi.VehicleReportActivity
import com.example.appdemo3.uiLoginScreen.viewmodel.LoginViewModel
import java.math.BigInteger
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    companion object{
        var accessToken : String =""
        const val grantType = "password"
    }


    private lateinit var LoginviewModel : LoginViewModel
    private lateinit var ActivityMainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        LoginviewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        observers()
    }

    fun onClickLogin(v: View)
    {
                val passwordd = ActivityMainBinding.etLoginPassword.text.toString()
                val md5password = md5(passwordd).toLowerCase()

                LoginviewModel.getLoginResponse(ActivityMainBinding.etLoginemail.text.toString(), md5password, grantType)
    }

    private fun observers(){
        LoginviewModel.loginliveData.observe(this){
            if (it != null){
                accessToken = it.access_token
                val intent= Intent(this, VehicleReportActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, R.string.login_fail , Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        ActivityMainBinding.etLoginPassword.text.clear()
        ActivityMainBinding.etLoginemail.text.clear()
    }

    private fun md5(input : String) : String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}