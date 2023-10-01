package com.dicoding.mygithubuser.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {
    val diskIo: Executor = Executors.newSingleThreadExecutor()
}