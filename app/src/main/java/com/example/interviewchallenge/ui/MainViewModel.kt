package com.example.interviewchallenge.ui

import androidx.lifecycle.ViewModel
import com.example.interviewchallenge.data.remote.model.directionModel.DirectionModel
import com.example.interviewchallenge.data.remote.usecase.DirectionUseCase
import com.example.interviewchallenge.data.remote.usecase.MatrixUseCase
import com.example.interviewchallenge.util.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val directionUseCase: DirectionUseCase,
    private val matrixUseCase: MatrixUseCase
) : ViewModel() {
     init {
         directionUseCase.execute(DirectionUseCase.DirectionParams(), {
             onComplete {  }
             onError {  }
             onCancel {  }
         })
         matrixUseCase.execute(MatrixUseCase.MatrixParams(), {
             onComplete {  }
             onError {  }
             onCancel {  }
         })
     }

    override fun onCleared() {
        super.onCleared()
    }
}