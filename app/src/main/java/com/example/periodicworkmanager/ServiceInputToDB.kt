package com.example.periodicworkmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.periodicworkmanager.Database.AppDatabase
import com.example.periodicworkmanager.Database.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time
import java.time.Instant.now
import java.time.LocalTime.now
import java.util.*

class ServiceInputToDB(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val data = inputData
        val desc = data.getString(MainActivity.KEY_TASK_DESC)
        displayNotification("Hey I am your work", desc)
        val data1 = Data.Builder()
            .putString(KEY_TASK_OUTPUT, "Task Finished Successfully")
            .build()

        insertValutToDB()

        return Result.success(data1)
    }

    private fun displayNotification(task: String, desc: String?) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "simplifiedcoding",
                "simplifiedcoding",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val builder =
            NotificationCompat.Builder(applicationContext, "simplifiedcoding")
                .setContentTitle(task)
                .setContentText(Calendar.getInstance().getTime().toString())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
        manager.notify(1, builder.build())
        Log.e("Time", Calendar.getInstance().getTime().toString())
    }

    companion object {
        const val KEY_TASK_OUTPUT = "key_task_output"
    }

    fun insertValutToDB() {


        val db: AppDatabase = AppDatabase.getAppDatabase(applicationContext) as AppDatabase

        //  addAll().execute(db)
        CoroutineScope(Dispatchers.IO).launch {
            val user = User()
            user.firstName = "Ajaydfd"
            user.lastName = "Saini"
            user.age = 27
            user.timestamp = Calendar.getInstance().getTime().toString()
            db.userDao()?.insertAll(user)
            Log.e("Value", db.userDao()?.countUsers().toString());
            // db.userDao()?.all
        }
    }

}