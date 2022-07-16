package com.binar.secondhand.core.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BindingFragment<B: ViewBinding> : Fragment() {
    private var _binding: B? = null
    protected lateinit var binding: B

    abstract fun inflaterBinding(): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterBinding()
        binding = _binding!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateBinding()
    }

    abstract fun onCreateBinding()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}