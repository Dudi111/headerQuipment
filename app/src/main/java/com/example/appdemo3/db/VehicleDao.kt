package com.example.appdemo3.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appdemo3.vehicalReportUi.shiftmodel.ResponseDetail


@Dao
interface VehicleDao {

    @Insert
    fun insertVehicalDetails(vehicalReport:List<ResponseDetail>)

    @Query(" SELECT * FROM "+ VicinityVehicleDatabase.Table_Name)
    fun getAllDetails(): LiveData<List<ResponseDetail>>
}