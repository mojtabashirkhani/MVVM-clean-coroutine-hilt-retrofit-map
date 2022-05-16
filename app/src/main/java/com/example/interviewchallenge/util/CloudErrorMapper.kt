package com.example.interviewchallenge.util

import android.util.Log
import com.example.interviewchallenge.data.remote.model.ErrorModel
import com.example.interviewchallenge.data.remote.model.ErrorStatus
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class CloudErrorMapper @Inject constructor(private val moshi: Moshi) {

    fun mapToDomainErrorException(throwable: Throwable?): ErrorModel {
        val errorModel: ErrorModel? = when (throwable) {

            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {
                // handle UNAUTHORIZED situation (when token expired)
                if (throwable.code() == 401) {
                    ErrorModel(ErrorStatus.UNAUTHORIZED)
                } else {
                    getHttpError(throwable.response()?.errorBody())
                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel("TIME OUT!!",0,ErrorStatus.TIMEOUT)
            }

            // handle connection error
            is IOException -> {
                ErrorModel("CHECK CONNECTION",0,ErrorStatus.NO_CONNECTION)
            }

            is UnknownHostException -> {
                ErrorModel("CHECK CONNECTION",0,ErrorStatus.NO_CONNECTION)
            }
            else -> null
        }
        return errorModel!!
    }

    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            val result = body?.string()
            Log.d("getHttpError", "getErrorMessage() called with: errorBody = [$result]")
//            val json = Gson().fromJson(result, JsonObject::class.java)
            ErrorModel(  moshi.toString(), 400, ErrorStatus.BAD_RESPONSE)
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(ErrorStatus.NOT_DEFINED)
        }

    }

}