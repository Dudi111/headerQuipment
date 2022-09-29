package com.example.appdemo3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appdemo3.vehicalReportUi.shiftmodel.ResponseDetail


@Database(entities = [ResponseDetail::class], version = 1)
abstract class VicinityVehicleDatabase: RoomDatabase() {

    companion object{
        const val DB_Name= "VehicleDetails"
        const val Table_Name= "VehicleReport_table"
    }

    abstract fun vehicleDaoAccess() : VehicleDao?
}