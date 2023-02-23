package com.example.auditoriaretail.Register.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.R
import com.example.auditoriaretail.databinding.FrgRegisterBinding


class Frg_register : Fragment() {

    private lateinit var binding:FrgRegisterBinding

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
       binding.btnGuardar.setOnClickListener {
           findNavController().navigate(R.id.to_frgMainLogin)

       }
    }

}