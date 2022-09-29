package com.example.appdemo3.vehicalReportUi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.appdemo3.R
import com.example.appdemo3.databinding.ActivityShiftReportBinding
import com.example.appdemo3.db.VicinityVehicleDatabase
import com.example.appdemo3.vehicalReportUi.shiftmodel.ResponseDetail
import com.example.appdemo3.vehicalReportUi.vehicalReportViewModel.VehicleDetailVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehicleReportActivity : AppCompatActivity() {

    companion object{
        const val lat = "52.164849"
        const val requestTime = "20210702020202"
        const val lng = "5.385008"
        const val radius = 1
    }

    private lateinit var reportViewModel : VehicleDetailVM
    private lateinit var vehicleDatabase : VicinityVehicleDatabase
    private lateinit var vehicleAdapter : VehicleReportAdapter
    private lateinit var vehicleActivityBinding : ActivityShiftReportBinding
    private var vehicleList = ArrayList<ResponseDetail>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vehicleActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_shift_report)

            vehicleDatabase = Room.databaseBuilder(
                this@VehicleReportActivity,
                VicinityVehicleDatabase::class.java, VicinityVehicleDatabase.DB_Name
            )
                .fallbackToDestructiveMigration()
                .build()


        reportViewModel = ViewModelProvider(this)[VehicleDetailVM::class.java]
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
}