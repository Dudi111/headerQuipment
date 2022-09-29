package com.example.appdemo3.vehicalReportUi

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.appdemo3.vehicalReportUi.shiftmodel.ResponseDetail
import kotlinx.android.synthetic.main.vehical_report.view.*

class VehicleViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun setData(vehicalReport : ResponseDetail){
        view.apply {
            tvBsnNumber.text= "BSN Number: ${vehicalReport.BSNNumber}"
            tvVehicalModel.text="Vehical model: ${vehicalReport.VehicleModel}"
            tvDriverName.text= "Driver name: ${vehicalReport.DriverName}"
            tvLicensePlate.text= "License plate: ${vehicalReport.LicensePlate}"
            tvVehicalNumber.text= "Vehical number: ${vehicalReport.VehicleNumber}"
        }
    }
}
