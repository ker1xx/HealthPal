package com.example.healthpal.remote.service.workout

import okhttp3.Interceptor
import okhttp3.Response

class WorkoutInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "x-rapidapi-key",
                "fcbb75b78amshc0dc91a39e04b3bp12d01ejsn34cb618621b8"
            )
            .build()
        return chain.proceed(request)
    }
}