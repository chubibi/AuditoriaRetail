package com.example.auditoriaretail.Dashboard.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.R
import com.example.auditoriaretail.databinding.FrgDashboardBinding


class FrgDashboard : Fragment() {
    private lateinit var binding: FrgDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FrgDashboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cambiar.setOnClickListener {
            findNavController().navigate(R.id.to_userlistActivity)
        }
    }
}