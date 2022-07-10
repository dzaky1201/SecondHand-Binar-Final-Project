package com.binar.secondhand.screen.detailbuyer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.databinding.FragmentDetailBuyerBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBuyerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()
    var idProduct: Int = 0
    var name: String = ""
    var price: Int = 0
    var image: String = ""
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBuyerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getInt("idProduct")
        viewModel.getDetailProduct(data ?: 0)

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnBuy.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)
            val btnClose = view.findViewById<Button>(R.id.btnBuy)

            btnClose.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
        with(viewModel.detailStateEvent) {
            onLoading = {
                progressDialog = DialogWindow.progressCircle(
                    requireContext(),
                    "Sedang Memuat Detail Product...",
                    true
                )
            }
            onSuccess = {
                progressDialog?.dismiss()
                idProduct = it.id!!
                name = it.name.toString()
                price = it.base_price!!
                image = it.image_url.toString()
                binding.also { bin ->
                    Glide.with(view).load(it.image_url).into(bin.ivProduct)
                    Glide.with(view).load(it.user.imageUrl).into(bin.ivSellerProfile)
                    bin.tvName.text = it.name
                    if (it.Categories.isNullOrEmpty()) {
                        bin.tvCategory.text = "This item have no Category"

                    } else {
                        bin.tvCategory.text = it.Categories[0].name
                    }
                    bin.tvDescription.text = it.description
                    bin.tvNameSeller.text = it.user.fullname
                    bin.tvCity.text = it.user.city
                    bin.tvHarga.text = it.base_price.toString()

                    bin.btnBuy.setOnClickListener {
                        val dialog = BottomSheetDialog(requireActivity())
                        val view = layoutInflater.inflate(R.layout.bottom_sheet_detail, null)

                        val btnClose = view.findViewById<Button>(R.id.btnBuy)
                        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
                        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
                        val ivPoster = view.findViewById<ImageView>(R.id.ivPoster)
                        val etPrice = view.findViewById<EditText>(R.id.etPrice)

                        tvTitle.setText(name)
                        tvPrice.setText(price.toString())
                        Glide.with(view).load(image).into(ivPoster)
                        btnClose.setOnClickListener {
                            var request: OrderRequest = OrderRequest(
                                idProduct,
                                etPrice.text.toString().toInt(),
                            )
                            viewModel.orderProducts(request)
                        }
                        with(viewModel.orderStateEvent) {
                            onLoading = {
                                progressDialog = DialogWindow.progressCircle(
                                    requireContext(),
                                    "Tunggu Sebentar...",
                                    true
                                )
                            }
                            onSuccess = {
                                Toast.makeText(
                                    requireActivity(),
                                    "Success to Negotiate",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                                progressDialog?.dismiss()
                                Log.d("Order State", it.toString())
                            }
                            onFailure = { _, _ ->
                                progressDialog?.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    "Failed to Negotiate",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("Order State", "Failed")
                            }
                        }
                        dialog.setCancelable(true)
                        dialog.setContentView(view)
                        dialog.show()
                    }

                }
            }

            onFailure = { _, _ ->
                Log.d("Detail API", "Loading Detail")

            }
        }

    }
}