package com.example.teamro.home.presentation.domain.usecase

import android.content.Intent
import com.example.auditoriaretail.Login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginGoogleUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(data: Intent?) = loginRepository.loginGoogle(data)
}
