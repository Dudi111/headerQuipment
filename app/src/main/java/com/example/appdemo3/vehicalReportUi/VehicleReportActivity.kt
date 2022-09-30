package com.example.appdemo3.vehicalReportUi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.appdemo3.R
import com.example.appdemo3.databinding.ActivityShiftReportBinding
import com.example.appdemo3.db.VicinityVehicleDatabase
import com.example.appdemo3.uiLoginScreen.MainActivity
import com.example.appdemo3.vehicalReportUi.shiftmodel.ResponseDetail
import com.example.appdemo3.vehicalReportUi.vehicalReportViewModel.VehicleDetailVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VehicleReportActivity : AppCompatActivity(), LocationListener {

    companion object{
        const val lat = "52.164849"
        const val requestTime = "20210702020202"
        const val lng = "5.385008"
        const val radius = 1
        var endTime : String = ""
    }

    private lateinit var reportViewModel : VehicleDetailVM
    private lateinit var vehicleDatabase : VicinityVehicleDatabase
    private lateinit var vehicleAdapter : VehicleReportAdapter
    private lateinit var vehicleActivityBinding : ActivityShiftReportBinding
    private lateinit var locationManager: LocationManager
    private var vehicleList = ArrayList<ResponseDetail>()
    private val locationPermissionCode = 2
    private var currentLat =""
    private var currentLng =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vehicleActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_shift_report)

            vehicleDatabase = Room.databaseBuilder(
                this@VehicleReportActivity,
                VicinityVehicleDatabase::class.java, VicinityVehicleDatabase.DB_Name
            )
                .fallbackToDestructiveMigration()
                .build()

        endTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        reportViewModel = ViewModelProvider(this)[VehicleDetailVM::class.java]
        getLocation()
        reportViewModel.getVehicleReport(lat, requestTime, lng, radius)
        observers()
    }

    private fun observers(){
        reportViewModel.vehicleReportliveData.observe(this){
            if (it != null){
                if (it.ResponseCode == 200){
                    CoroutineScope(Dispatchers.IO).launch {
                        vehicleDatabase.vehicleDaoAccess()!!.insertVehicalDetails(it.ResponseDetail)
                    }
                }
            }else{
                Toast.makeText(this, R.string.report_fail, Toast.LENGTH_SHORT).show()

            }
        }

        vehicleDatabase.vehicleDaoAccess()!!.getAllDetails().observe(this){
            vehicleList = it as ArrayList<ResponseDetail>
            setAdapter()
        }
    }

    private fun setAdapter(){
        vehicleAdapter = VehicleReportAdapter(this,vehicleList )
        val layoutManager = LinearLayoutManager(this)
        vehicleActivityBinding.recyclerViewMain.adapter = vehicleAdapter
        vehicleActivityBinding.recyclerViewMain.layoutManager = layoutManager
    }

    override fun onBackPressed() {
        logoutDialog()
    }

    private fun logoutDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage(R.string.dialog_message)
        builder.setIcon(R.drawable.ic_baseline_logout_24)
        builder.setPositiveButton("Yes"){ _, _ ->
            finish()
        }
        builder.setNegativeButton("No"){ _, _ ->

        }
        val alertDialog= builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0f, this)
        val gpss= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        vehicleActivityBinding.tvVehicalDetails.text = "Latitude: " + gpss!!.latitude + " , Longitude: " + gpss!!.longitude
        currentLat = "Latitude: " + gpss!!.latitude
        currentLng= " , Longitude: " + gpss!!.longitude

    }

    override fun onLocationChanged(location: Location) {
        vehicleActivityBinding.tvVehicalDetails.text = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        currentLat = "Latitude: " + location.latitude
        currentLng= " , Longitude: " + location.longitude

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isTokenActive(start: String, stop: String): Boolean
    {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val date1: Date = simpleDateFormat.parse(MainActivity.startTime)
        val date2: Date = simpleDateFormat.parse(endTime)
        val differenceInMilliSeconds: Long = Math.abs(date2.getTime() - date1.getTime())
        val differenceInHours: Long = (differenceInMilliSeconds / (60 * 60 * 1000) % 24)

        val differenceInMinutes: Long = differenceInMilliSeconds / (60 * 1000) % 60
        val differenceInSeconds: Long = differenceInMilliSeconds / 1000 % 60
        var totalDiff = differenceInHours*3600 + differenceInMinutes*60 + differenceInSeconds

        return totalDiff.toInt() < MainActivity.expireTime.toInt()
    }
}