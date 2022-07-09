package com.binar.secondhand.screen.detailbuyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.binar.secondhand.R
import com.binar.secondhand.databinding.FragmentFragmentaBuyBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class FragmentaBuy : Fragment() {
    lateinit var binding: FragmentFragmentaBuyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragmentaBuyBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nego.setOnClickListener{
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.nego, null)
            val btnNego = view.findViewById<Button>(R.id.kirim)

            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            btnNego.setOnClickListener {
                // on below line we are calling a dismiss
                // method to close our dialog.
                Toast.makeText(requireContext(),"Harga berhasil dikirim", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            // below line is use to set cancelable to avoid
            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(true)

            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)

            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()
        }
    }
}