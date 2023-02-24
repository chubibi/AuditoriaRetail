package com.example.auditoriaretail.Register.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.Register.domain.usecase.SaveDataUserUseCase
import com.example.auditoriaretail.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    //private val getGoalUseCase: GetGoalUseCase,
    private val saveDataUserUseCase: SaveDataUserUseCase
) : ViewModel() {
    //private val _goals: MutableLiveData<MutableList<Goal>> = MutableLiveData()
    private val _gender: MutableLiveData<String> = MutableLiveData()
    private val _goal: MutableLiveData<String> = MutableLiveData()
    private val _age: MutableLiveData<Int> = MutableLiveData()
    private val _phone: MutableLiveData<Int> = MutableLiveData()
    private val _allData: MutableLiveData<Boolean> = MutableLiveData()
    private val _stateRegister: MutableLiveData<State> = MutableLiveData()

    val allData: LiveData<Boolean> get() = _allData
    //val goals: LiveData<MutableList<Goal>> get() = _goals
    val registerState: LiveData<State> get() = _stateRegister

    /*fun getGoals() {
        getGoalUseCase().observeForever {
            _goals.postValue(it)
        }
    }*/

    fun getPhone(phone:Int){
        _phone.postValue(phone)
    }

    fun saveGender(gender: String) {
        _gender.postValue(gender)
    }

    fun saveGoal(goal: String) {
        _goal.postValue(goal)
    }

    fun saveAge(age: Int) {
        _age.postValue(age)
    }

    fun saveUserData(dataUserModel: UserModel) {
        _stateRegister.postValue(State.Loading)
        if (_gender.value != null && _goal.value != null && _age.value != null) {
            //dataUserModel.goal = _goal.value!!
            //dataUserModel.gender = _gender.value!!
            //dataUserModel.age = _age.value!!
            dataUserModel.phone = _phone.value.toString()
            saveDataUserUseCase(dataUserModel).observeForever {
                _stateRegister.postValue(it)
            }
        } else {
            _stateRegister.postValue(State.Error("Falatan llenar datos"))
        }
    }
}
