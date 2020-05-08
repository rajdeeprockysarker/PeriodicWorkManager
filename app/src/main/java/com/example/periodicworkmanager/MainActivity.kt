package com.example.periodicworkmanager

import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.provider.CalendarContract
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.periodicworkmanager.Database.AppDatabase
import com.example.periodicworkmanager.Database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = Data.Builder()
                .putString(KEY_TASK_DESC, "Hey"+ Calendar.getInstance().getTime().toString())
                .build()


        val constraints = Constraints.Builder()

                .build()

        //// OneTimeWorkRequest


    /**    val request = OneTimeWorkRequest.Builder(ServiceInputToDB::class.java)
                .setInputData(data)
                .setConstraints(constraints)
                .build()*/


        ////// PeriodicWorkRequest
        val request = PeriodicWorkRequest.Builder(ServiceInputToDB::class.java,15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build()



        WorkManager.getInstance().enqueue(request)



        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
                .observe(this, Observer { workInfo ->
                    if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {

                        val data = workInfo.outputData


                        // displayMessage("Work finished!")
                    }
                })




    }

    companion object {
        const val KEY_TASK_DESC = "key_task_desc"
    }




}
