package com.leodemo.taipei_tour_core.data.retrofit

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall<T>(
    private val call: Call<T>
) : Call<ApiResponse<T>> {
    override fun clone(): Call<ApiResponse<T>> {
        return ApiCall(call)
    }

    override fun execute(): Response<ApiResponse<T>> {
        val result = try {
            val response = call.execute()
            if (response.raw().code in 200..299) {
                ApiResponse.Success(response.body() ?: Unit as T)
            } else {
                ApiResponse.Failure(Exception())
            }
        } catch (e: Exception) {
            ApiResponse.Failure(e)
        }
        return Response.success(result)
    }

    override fun enqueue(callback: Callback<ApiResponse<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = ApiResponse.Success(response.body() ?: Unit as T)
                callback.onResponse(this@ApiCall, Response.success(apiResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val apiResponse = ApiResponse.Failure(t)
                callback.onResponse(this@ApiCall, Response.success(apiResponse))
            }
        })
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun cancel() {
        return call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
}