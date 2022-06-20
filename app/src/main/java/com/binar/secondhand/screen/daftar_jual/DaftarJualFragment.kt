package com.binar.secondhand.screen.daftar_jual

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binar.secondhand.R

class DaftarJualFragment : Fragment() {

    companion object {
        fun newInstance() = DaftarJualFragment()
    }

    private lateinit var viewModel: DaftarJualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daftar_jual, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DaftarJualViewModel::class.java)
        // TODO: Use the ViewModel
    }

}