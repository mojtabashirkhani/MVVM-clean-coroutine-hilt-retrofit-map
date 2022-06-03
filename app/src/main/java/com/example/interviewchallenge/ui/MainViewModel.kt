package com.example.interviewchallenge.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewchallenge.data.remote.usecase.DirectionUseCase
import com.example.interviewchallenge.data.remote.usecase.MatrixUseCase
import com.example.interviewchallenge.util.Event
import com.example.interviewchallenge.util.ExtensionFunctions.assignValue
import dagger.hilt.android.lifecycle.HiltViewModel
import org.neshan.servicessdk.direction.model.NeshanDirectionResult
import org.neshan.servicessdk.distancematrix.model.NeshanDistanceMatrixResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     private val directionUseCase: DirectionUseCase,
     private val matrixUseCase: MatrixUseCase,
) : ViewModel() {

    val direction = MutableLiveData<Event<NeshanDirectionResult>>()
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
    }



    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private val TAG = MainViewModel::class.java.name
    }
}