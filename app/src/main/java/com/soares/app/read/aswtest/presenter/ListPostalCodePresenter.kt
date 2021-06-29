package com.soares.app.read.aswtest.presenter

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.opencsv.CSVReaderBuilder
import com.soares.app.read.aswtest.Application
import com.soares.app.read.aswtest.data_room.PostalCodeRepository
import com.soares.app.read.aswtest.model.PostalCode
import com.soares.app.read.aswtest.view.IMainView
import java.io.File
import java.io.FileReader
import java.io.IOException


class ListPostalCodePresenter(context: Context, view: IMainView, repository: PostalCodeRepository) :
    IListPostalCodePresenter {
    var mView = view
    var viewContext = context
    var mRepository = repository
    var PATH_FILE_EXCEL =
        viewContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.path + "/codePostal.csv"
    var URL_TO_DOWNLOAD_EXCEL =
        "https://raw.githubusercontent.com/centraldedados/codigos_postais/master/data/codigos_postais.csv"
    var myDownLoadId = 0L

    override fun isExistDatabaseApp(): Boolean {
        return File(PATH_FILE_EXCEL).exists()
    }

    override fun executeDownloadExcel() {
        val request = DownloadManager
            .Request(Uri.parse(URL_TO_DOWNLOAD_EXCEL))
            .setDestinationInExternalFilesDir(
                viewContext,
                Environment.DIRECTORY_DOWNLOADS,
                "codePostal.csv"
            )
            .setAllowedOverMetered(true)
            .setMimeType("text/csv")
        var csv = viewContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownLoadId = csv.enqueue(request)
    }


    @WorkerThread
    override fun broadcastReceiverView(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == myDownLoadId) {
                    (viewContext.applicationContext as Application).executorService.execute {
                        var postalCode: PostalCode
                        try {
                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    mView.showListPostalCode(getList(1, 100))
                                },
                                3000
                            )
                            val reader =
                                CSVReaderBuilder(FileReader(PATH_FILE_EXCEL)).withSkipLines(1)
                                    .build()
                            var nextLine: Array<String>
                            while (reader.readNext().also { nextLine = it } != null) {
                                postalCode =
                                    PostalCode("${nextLine[14]}-${nextLine[15]}", "${nextLine[16]}")
                                mRepository.insert(postalCode)
                            }
                        } catch (e: IOException) {
                            println("ERROR ${e.message}")
                        } catch (e: NullPointerException) {
                            println("ERRO ${e.message}")
                        }
                    }
                }
            }
        }
    }


    @WorkerThread
    override fun setValuesInDatabaseContinue() {
        var postalCode: PostalCode
        (viewContext.applicationContext as Application).executorService.execute {
            try {
                var reader =
                    CSVReaderBuilder(FileReader(PATH_FILE_EXCEL)).withSkipLines(mRepository.lastPostalCode.id.toInt())
                        .build()
                var nextLine: Array<String>
                while (reader.readNext().also { nextLine = it } != null) {
                    postalCode =
                        PostalCode("${nextLine[14]}-${nextLine[15]}", "${nextLine[16]}")
                    mRepository.insert(postalCode)
                }
            } catch (e: IOException) {
                println("ERROR  ${e.message}")
            } catch (e: NullPointerException) {
                println("ERROR  ${e.message}")
            }
        }
    }

    override fun getList(min: Int, max: Int): List<PostalCode> {
        return mRepository.rangePostalCode(min, max)
    }

    @MainThread
    override fun getListFilter(postalCodeFilter: String, descFilter: String): List<PostalCode> {
        return mRepository.listPostalCodeFilter(postalCodeFilter, descFilter)
    }
}
