package com.binar.secondhand.screen.daftar_jual.screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.secondhand.R
import com.binar.secondhand.screen.daftar_jual.TerjualViewModel

class TerjualFragment : Fragment() {

    companion object {
        fun newInstance() = TerjualFragment()
    }

    private lateinit var viewModel: TerjualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_terjual, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TerjualViewModel::class.java)
        // TODO: Use the ViewModel
    }

}