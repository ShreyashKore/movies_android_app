package dev.shreyash.androidmoviestask.data

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val apiKey: String,
    private val bearerToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val modifiedUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey).build()
        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $bearerToken")
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }
}