package com.soares.app.read.aswtest.presenter

import android.content.BroadcastReceiver
import com.soares.app.read.aswtest.model.PostalCode

interface IListPostalCodePresenter {
    fun isExistDatabaseApp() : Boolean
    fun executeDownloadExcel()
    fun broadcastReceiverView() : BroadcastReceiver
    fun setValuesInDatabaseContinue()
    fun getList(min: Int, max: Int) : List<PostalCode>
    fun getListFilter(postalCodeFilter: String, descFilter: String): List<PostalCode>

}