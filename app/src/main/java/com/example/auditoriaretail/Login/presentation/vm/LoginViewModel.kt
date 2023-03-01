package com.example.auditoriaretail.Login.presentation.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auditoriaretail.Loading
import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.Login.domain.usecase.LoginUseCase
import com.example.auditoriaretail.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _stateLogin: MutableLiveData<Result<UserModel>> = MutableLiveData()

    val stateLogin: LiveData<Result<UserModel>> get() = _stateLogin

    fun login(dataLoginModel: LoginModel) {
        _stateLogin.postValue(Loading())
        loginUseCase(dataLoginModel).observeForever{
            _stateLogin.postValue(it)
        }
    }
}
