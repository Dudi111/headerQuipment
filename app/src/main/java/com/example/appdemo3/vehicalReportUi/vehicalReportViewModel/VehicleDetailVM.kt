package com.example.appdemo3.vehicalReportUi.vehicalReportViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdemo3.uiLoginScreen.network.NewNetworkcall
import com.example.appdemo3.vehicalReportUi.shiftmodel.VehicleReport
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehicleDetailVM() : ViewModel() {

    private var vehicleReportMutableData = MutableLiveData<VehicleReport>()
    val vehicleReportliveData : LiveData<VehicleReport> = vehicleReportMutableData


    fun getVehicleReport(lat:String?, requestTime:String?, lng:String?, radius:Int?){
        NewNetworkcall.getapiservice().getShiftDetails( lat, requestTime, lng, radius).enqueue(object : Callback<VehicleReport>
        {
            override fun onResponse(call: Call<VehicleReport>, response: Response<VehicleReport>) {
                vehicleReportMutableData.postValue(response.body())
            }

            override fun onFailure(call: Call<VehicleReport>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}