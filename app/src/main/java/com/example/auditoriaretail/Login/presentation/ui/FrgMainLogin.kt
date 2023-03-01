package com.example.auditoriaretail.Login.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.Loading
import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.presentation.vm.LoginViewModel
import com.example.auditoriaretail.R
import com.example.auditoriaretail.SuccessResult
import com.example.auditoriaretail.databinding.FrgMainLoginBinding
import com.example.auditoriaretail.util.FormValidation.validateEmail
import com.example.auditoriaretail.util.FormValidation.validatePassword
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.Executor


@AndroidEntryPoint
class FrgMainLogin : Fragment() {

    private lateinit var executor: Executor;
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    //Login
    private val viewModel: LoginViewModel by viewModels()
    //private val GOOGLE_SING_IN_ = 1000
    private lateinit var firebaseAuth: FirebaseAuth
    //Binding
    private lateinit var binding:FrgMainLoginBinding //Agregar todos los ID que hay en las vistas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FrgMainLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {//Sobreescribir el ciclo
        super.onViewCreated(view, savedInstanceState)

        //START LOGIN
        with(binding){
            etEmail.validate(inputEmail, ::validateEmail)
            etPassword.validate(inputPassword, ::validatePassword)
            binding.btnIniciarSesion.setOnClickListener {
                viewModel.login(
                    LoginModel(
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                    ),
                )
                biometricPrompt.authenticate(promptInfo)
            }
            viewModel.stateLogin.observe(viewLifecycleOwner) {
                when (it) {
                    is Loading -> {
                        /*customAlert = CustomAlert(requireActivity(), Theme.SYSTEM)
                        customAlert.setColor(com.mh.custom_alert.R.color.colorAccentCustomAlert)
                        customAlert.setCancelable(false)
                        customAlert.setTitle("Login")
                        customAlert.setType(Type.PROGRESS)
                        customAlert.setMessage("Cargando")
                        customAlert.show()*/
                        Log.d("STATE","Cargando")
                    }
                    is SuccessResult -> {
                        lifecycleScope.launch {
                            /*customAlert.setType(Type.SUCCESS)
                            customAlert.setMessage("Exitoso")
                            delay(1000)
                            customAlert.dismiss()
                            val actDashboard = Intent(requireContext(), ActDashboard::class.java)
                            startActivity(actDashboard)*/
                            Log.d("STATE","Si paso")
                            findNavController().navigate(R.id.to_frgDashboard)
                        }
                    }
                    is Error -> {
                        /*customAlert.setType(Type.FAIL)
                        customAlert.setMessage("Error")
                        customAlert.setPositiveText("Ok") {
                            customAlert.dismiss()
                        }*/
                        Log.d("STATE","Error")
                    }
                    else -> {}
                }
            }
        }

        //BiometricData
        executor=ContextCompat.getMainExecutor(requireContext())
        biometricPrompt=androidx.biometric.BiometricPrompt(this@FrgMainLogin,executor,object:androidx.biometric.BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), "Demasiados intentos, Ingresa con contraseña", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(), "Bienvenido", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), "Huella no reconocida", Toast.LENGTH_SHORT).show()
            }
        })
        promptInfo=androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autentificación Retail")
            .setSubtitle("Ingresa tu Huella")
            .setNegativeButtonText("Regresar al Inicio")
            .build()
        //EndBiometricData
    }

    //Funcion para validar campos
    private fun EditText.validate(
        input: TextInputLayout,
        validate: (String) -> Boolean
    ) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!validate(text.toString())) {
                    //input.error = getString(R.string.form_invalidate)
                    input.error = "Formulario invalido"
                    binding.btnIniciarSesion.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.round
                    )
                } else {
                    input.error = null
                    validateError()
                }
            }
        })
    }

    private fun validateError() {
        with(binding) {
            if (
                inputEmail.error == null && etEmail.text.toString().isNotEmpty() &&
                inputPassword.error == null && etPassword.text.toString().isNotEmpty()
            ) {
                binding.btnIniciarSesion.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.round_enable,
                )
            }
        }
    }
}