package com.example.interviewchallenge.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.interviewchallenge.data.remote.model.directionModel.DirectionModel
import com.example.interviewchallenge.data.remote.usecase.DirectionUseCase
import com.example.interviewchallenge.data.remote.usecase.MatrixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.neshan.servicessdk.direction.model.NeshanDirectionResult
import org.neshan.servicessdk.distancematrix.model.NeshanDistanceMatrixResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     private val directionUseCase: DirectionUseCase,
     private val matrixUseCase: MatrixUseCase,
) : ViewModel() {

    val direction = MutableLiveData<NeshanDirectionResult>()
    val matrix = MutableLiveData<NeshanDistanceMatrixResult>()


    fun getDirectionParams(params: DirectionUseCase.DirectionParams) {
        directionUseCase.execute(params) {
            onComplete {
                 direction.postValue(it)
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
                        matrix.postValue(it)
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