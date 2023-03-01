package com.example.auditoriaretail.Login.domain.usecase

import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository){
    operator fun invoke(loginModel: LoginModel) = loginRepository.login(loginModel)
}