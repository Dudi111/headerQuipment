package com.example.appdemo3.vehicalReportUi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdemo3.R
import com.example.appdemo3.vehicalReportUi.shiftmodel.ResponseDetail

class VehicleReportAdapter(private val context : Context, private var vehicalList : List<ResponseDetail>) : RecyclerView.Adapter<VehicleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val newview= LayoutInflater.from(parent.context).inflate(R.layout.vehical_report,parent,false)
        return VehicleViewHolder(newview)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val model = vehicalList[position]
        holder.setData(model)
    }

    override fun getItemCount(): Int {
        return vehicalList.size
    }
}