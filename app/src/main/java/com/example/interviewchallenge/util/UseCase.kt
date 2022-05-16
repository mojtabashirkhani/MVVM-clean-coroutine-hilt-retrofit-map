package com.example.interviewchallenge.util

import com.example.interviewchallenge.data.remote.model.ErrorModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<T, in Params>(private val errorUtil: CloudErrorMapper) {

    private var parentJob: Job = Job()
    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(params: Params? = null): T

    fun execute(params: Params?, block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(params)
                }
                response(result)
            } catch (cancellationException: CancellationException) {
                response(cancellationException)
            } catch (e: Exception) {
                val error = errorUtil.mapToDomainErrorException(e)
                response(error)
            }
        }
    }


    private fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }



    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((ErrorModel) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (ErrorModel) -> Unit) {

            onError = block

        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }


        operator fun invoke(result: T) {
            onComplete?.let {
                it.invoke(result)
            }
        }

        operator fun invoke(error: ErrorModel) {
            onError?.let {
                it.invoke(error)

            }
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.let {
                it.invoke(error)
            }
        }
    }
}