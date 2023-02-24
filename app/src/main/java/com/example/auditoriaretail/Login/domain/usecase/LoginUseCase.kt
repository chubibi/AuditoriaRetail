package com.example.teamro.home.presentation.domain.usecase


import com.example.auditoriaretail.Login.domain.repository.LoginRepository
import com.example.auditoriaretail.Login.data.model.LoginModel
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(loginModel: LoginModel) = loginRepository.login(loginModel)
}
