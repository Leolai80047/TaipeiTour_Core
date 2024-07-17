package com.leodemo.taipei_tour_core.data.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory private constructor(): CallAdapter.Factory() {

    companion object {
        fun create(): ApiResponseCallAdapterFactory {
            return ApiResponseCallAdapterFactory()
        }
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(callType) != ApiResponse::class.java) return null
        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
        return ApiResponseCallAdapter(resultType)
    }
}

class ApiResponseCallAdapter(
    private val response: Type
): CallAdapter<Type, Call<ApiResponse<Type>>> {
    override fun responseType(): Type {
        return response
    }

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return ApiCall(call)
    }

}
