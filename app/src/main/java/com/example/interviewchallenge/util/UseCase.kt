package com.example.interviewchallenge.util

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


typealias CompletionBlock<T> = UseCase.Request<T>.() -> Unit

abstract class UseCase<T, in Params>() {

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
               response(e)
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
        private var onError: ((Exception) -> Unit)? = null
        private var onCancel: ((CancellationException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (Exception) -> Unit) {

            onError = block

        }

        fun onCancel(block: (CancellationException) -> Unit) {
            onCancel = block
        }


        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: Exception) {
            onError?.invoke(error)
        }

        operator fun invoke(error: CancellationException) {
            onCancel?.invoke(error)
        }
    }
}