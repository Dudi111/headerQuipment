package com.example.appdemo3.vehicalReportUi.shiftmodel


data class VehicleReport(
    val ErrorException: Any,
    val IsSuccess: Boolean,
    val Message: String,
    val ResponseCode: Int,
    val ResponseDetail: List<ResponseDetail>
)