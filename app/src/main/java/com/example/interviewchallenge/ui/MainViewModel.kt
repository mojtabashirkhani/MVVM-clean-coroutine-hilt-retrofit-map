package com.example.interviewchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewchallenge.data.remote.model.reverse.ReverseMapIr
import com.example.interviewchallenge.data.remote.model.reverse.ReverseNeshan
import com.example.interviewchallenge.data.remote.usecase.ReverseMapIrUseCase
import com.example.interviewchallenge.data.remote.usecase.ReverseNeshanUseCase
import com.example.interviewchallenge.util.Event
import com.example.interviewchallenge.util.ExtensionFunctions.assignValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     private val reverseMapIrUseCase: ReverseMapIrUseCase,
     private val reverseNeshanUseCase: ReverseNeshanUseCase
) : ViewModel() {

    val reverseMapIr = MutableLiveData<Event<ReverseMapIr>>()
    val reverseNeshan = MutableLiveData<Event<ReverseNeshan>>()

    fun getNeshanRverseParams(params: ReverseNeshanUseCase.ReverseParams) {
        reverseNeshanUseCase.execute(params){
            onComplete {
                reverseNeshan.assignValue(Event(it))
            }
            onError {
                it.message
            }
            onCancel {
                it.message
            }
        }
    }

    fun getMapIrReverseParams(params: ReverseMapIrUseCase.ReverseParams) {
        reverseMapIrUseCase.execute(params) {
            onComplete {
                reverseMapIr.assignValue(Event(it))
            }
            onError {
                it.message
            }
            onCancel {
                it.message
            }
        }
    }

   /* val direction = MutableLiveData<Event<NeshanDirectionResult>>()
    val matrix = MutableLiveData<Event<NeshanDistanceMatrixResult>>()


    fun getDirectionParams(params: DirectionUseCase.DirectionParams) {
        directionUseCase.execute(params) {
            onComplete {
                 direction.assignValue(Event(it))
            }
            onError {
                it.message
            }
            onCancel {
                it.message
            }
        }
    }



    fun getMatrixParams(params: MatrixUseCase.MatrixParams) {
         matrixUseCase.execute(params) {
                    onComplete {
                        matrix.assignValue(Event(it))
                    }
                    onError {
                        it.message
                    }
                    onCancel {
                        it.message
                    }
                }
    }*/



    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private val TAG = MainViewModel::class.java.name
    }
}