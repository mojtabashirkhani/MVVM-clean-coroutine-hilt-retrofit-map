package com.example.interviewchallenge.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interviewchallenge.data.remote.model.directionModel.DirectionModel
import com.example.interviewchallenge.data.remote.usecase.DirectionUseCase
import com.example.interviewchallenge.data.remote.usecase.MatrixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.neshan.servicessdk.direction.model.NeshanDirectionResult
import org.neshan.servicessdk.distancematrix.model.NeshanDistanceMatrixResult
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    directionUseCase: DirectionUseCase,
    matrixUseCase: MatrixUseCase
) : ViewModel() {

    val direction = MutableLiveData<NeshanDirectionResult>()
    val matrix = MutableLiveData<NeshanDistanceMatrixResult>()

    val _directionParams = MutableLiveData<DirectionUseCase.DirectionParams>()
    val _matrixParams = MutableLiveData<MatrixUseCase.MatrixParams>()


    fun setDirectionParams(params: DirectionUseCase.DirectionParams) {
        if (_directionParams.value == params)
            return
        _directionParams.postValue(params)
    }

    fun setMatrixParams(params: MatrixUseCase.MatrixParams) {
        if (_matrixParams.value == params)
            return
        _matrixParams.postValue(params)
    }

    init {

        directionUseCase.execute(
            DirectionUseCase.DirectionParams(
                "service.VNlPhrWb3wYRzEYmstQh3GrAXyhyaN55AqUSRR3V",
                "35.767234,51.330743",
                "35.767238,51.330745"
            )
        ) {
            onComplete {
                Log.d(TAG, it.toString())
                direction.value = it
            }
            onError {
                it.message
            }
            onCancel { }
        }
       /* matrixUseCase.execute(_matrixParams.value) {
            onComplete {
                Log.d(TAG, it.toString())
                matrix.value = it
            }
            onError { }
            onCancel { }
        }*/
    }


    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        private val TAG = MainViewModel::class.java.name
    }
}