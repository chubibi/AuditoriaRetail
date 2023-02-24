package com.example.auditoriaretail.Login.presentation.vm

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auditoriaretail.Loading
import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.teamro.home.presentation.domain.usecase.LoginGoogleUseCase
import com.example.teamro.home.presentation.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.auditoriaretail.Result


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginGoogleUseCase: LoginGoogleUseCase
) : ViewModel() {
    private val _stateLogin: MutableLiveData<Result<UserModel>> = MutableLiveData()

    val stateLogin: LiveData<Result<UserModel>> get() = _stateLogin

    fun login(dataLoginModel: LoginModel) {
        _stateLogin.postValue(Loading())
        loginUseCase(dataLoginModel).observeForever {
            _stateLogin.postValue(it)
        }
    }

    fun loginGoogle(data: Intent) {
        _stateLogin.postValue(Loading())
        loginGoogleUseCase(data).observeForever {
            _stateLogin.postValue(it)
        }
    }
}
