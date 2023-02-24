package com.example.teamro.home.presentation.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auditoriaretail.Loading
import com.example.auditoriaretail.Result
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.Login.domain.usecase.SingInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(private val singInUseCase: SingInUseCase) : ViewModel() {
    private val _stateSingIn: MutableLiveData<Result<UserModel>> = MutableLiveData()

    val stateSingIn: LiveData<Result<UserModel>> get() = _stateSingIn

    fun SingInGoogle(data: Intent?) {
        _stateSingIn.postValue(Loading())
        singInUseCase(data).observeForever {
            _stateSingIn.postValue(it)
        }
    }
}
