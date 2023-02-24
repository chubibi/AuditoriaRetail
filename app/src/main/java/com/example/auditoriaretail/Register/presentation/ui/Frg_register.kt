package com.example.auditoriaretail.Register.presentation.ui

import android.content.Intent
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
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.Loading
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.R
import com.example.auditoriaretail.SuccessResult
import com.example.auditoriaretail.databinding.FrgRegisterBinding
import com.example.auditoriaretail.util.FormValidation.validateEmail
import com.example.auditoriaretail.util.FormValidation.validateName
import com.example.auditoriaretail.util.FormValidation.validatePassword
import com.example.teamro.home.presentation.viewmodel.SingInViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@AndroidEntryPoint
class Frg_register : Fragment() {

    private val GOOGLE_SING_IN = 1000
    private lateinit var binding:FrgRegisterBinding
    private lateinit var dataUser: UserModel
    private val viewModel: SingInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FrgRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnGuardar.setOnClickListener {
                val dataUser = UserModel(
                    inputNombre.text.toString(),
                    inputEmail.text.toString(),
                    inputPassword.toString(),
                    "5538924729",
                    singIn = 1
                )
            }

            viewModel.stateSingIn.observe(viewLifecycleOwner) {
                when (it) {
                    is SuccessResult -> {
                        //customAlert.dismiss()
                        //val intent = Intent(requireContext(), ActRegister::class.java)
                        //intent.putExtra("user", it.result)
                        //startActivity(intent)
                        Toast.makeText(requireContext(), "Se registrooo ${it.result}", Toast.LENGTH_SHORT).show()
                    }
                    is Error -> {
                        /*customAlert.setType(Type.FAIL)
                        customAlert.setMessage("Error")
                        customAlert.setPositiveText("Ok") {
                            customAlert.dismiss()
                        }*/
                        Toast.makeText(requireContext(), "Hubo error", Toast.LENGTH_SHORT).show()
                    }
                    is Loading -> {
                        /*customAlert.setCancelable(false)
                        customAlert.setColor(com.mh.custom_alert.R.color.colorAccentCustomAlert)
                        customAlert.setTitle("Registro")
                        customAlert.setType(Type.PROGRESS)
                        customAlert.setMessage("Cargando")
                        customAlert.show()*/
                        Toast.makeText(requireContext(), "Cargando hdptm", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d("ELSE REGISTER","ERROR DESCONOCIDO")
                    }
                }
            }
            inputNombre.validate(nombreUsuario, ::validateName)
            inputEmail.validate(txtInputEmail, ::validateEmail)
            inputPassword.validate(contraseAUsuario, ::validatePassword)
            //etPhone.validate(inputPhone, ::validatePhone)
        }

        }

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
                    binding.btnGuardar.background = ContextCompat.getDrawable(
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

    /*private fun showAlert(
        type: IntArray,
        message: String,
        boolean: Boolean
    ) {
        customAlert = CustomAlert(requireActivity(), Theme.SYSTEM)
        customAlert.setTitle("Registro")
        customAlert.setType(type)
        customAlert.setMessage(message)
        customAlert.setPositiveText(
            "Ok"
        ) {
            customAlert.dismiss()
        }
        customAlert.show()
    }*/

    private fun validateError() {
        with(binding) {
            if (nombreUsuario.error == null && inputNombre.text.toString()
                    .isNotEmpty() && txtInputEmail.error == null && inputEmail.text.toString()
                    .isNotEmpty() && contraseAUsuario.error == null && inputPassword.text.toString()
                    //.isNotEmpty() && inputPhone.error == null && etPhone.text.toString()
                    .isNotEmpty()

            ) {
                binding.btnGuardar.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.round_enable
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SING_IN) {
            viewModel.SingInGoogle(data)
        }
    }
    }