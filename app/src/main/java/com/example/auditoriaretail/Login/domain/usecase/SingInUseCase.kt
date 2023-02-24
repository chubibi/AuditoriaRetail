package com.example.auditoriaretail.Login.domain.usecase

import android.content.Intent
import com.example.teamro.home.presentation.domain.repository.SingInRepository
import javax.inject.Inject

class SingInUseCase @Inject constructor(private val singInRepository: SingInRepository) {
    operator fun invoke(data: Intent?) = singInRepository.saveGoogle(data)
}
