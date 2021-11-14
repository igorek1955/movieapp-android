package com.example.movieapp

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class BackgroundExecutors {
    companion object {
        private var instance: BackgroundExecutors? = null
        fun getInstance(): BackgroundExecutors {
            if (instance == null) {
                instance = BackgroundExecutors()
            }
            return instance!!
        }
    }

    val mNetworkIO: ScheduledExecutorService = Executors.newScheduledThreadPool(3);
}