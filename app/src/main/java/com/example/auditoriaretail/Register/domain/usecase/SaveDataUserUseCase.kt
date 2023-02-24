package com.example.auditoriaretail.Register.domain.usecase


import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.Register.domain.repository.DataUserRepository
import javax.inject.Inject

/**
 * Use case for saving a user's data in firebase
 * @property dataUserRepository [DataUserRepository] Repository for operations in firebase
 */
class SaveDataUserUseCase @Inject constructor(private val dataUserRepository: DataUserRepository) {
    /**
     * Run the use case
     * @param userData [UserModel]
     */
    operator fun invoke(userData: UserModel) = dataUserRepository.saveData(userData)
}
