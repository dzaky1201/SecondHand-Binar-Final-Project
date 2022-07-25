package com.binar.secondhand.screen.daftar_jual.screen.diminati

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.core.data.remote.daftar_jual.request.UpdateStatusProductReq
import com.binar.secondhand.core.domain.model.daftar_jual.SellerProductInterestedEntity
import com.binar.secondhand.core.domain.model.daftar_jual.UpdateStatusProduct
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.core.utils.dateformat
import com.binar.secondhand.core.utils.formatRupiah
import com.binar.secondhand.databinding.FragmentDiminatiDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel


class DiminatiDetailFragment : Fragment() {

    private var _binding: FragmentDiminatiDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<DiminatiViewModel>()
    private var progressDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiminatiDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getInt("idDiminati")
        viewModel.getDetailSellerOrder(args!!)

        with(viewModel.sellerDetailOrder) {
            onLoading = {
                progressDialog =
                    DialogWindow.progressCircle(
                        requireContext(),
                        "Loading...",
                        true
                    )
            }

            onSuccess = { detail ->
                progressDialog?.dismiss()
                with(binding) {
                    txtName.text = detail.user?.fullName
                    txtCity.text = detail.user?.city
                    tvProductName.text = detail.productName
                    Glide.with(root).load(detail.user?.imageUrl).into(ivAkun)
                    Glide.with(root).load(detail.imageProduct).into(ivPosterImage)
                    tvDitawar.text = "Ditawar "+detail.price?.toDouble()?.formatRupiah()
                    tvBasePrice.text = "Harga awal "+detail.basePrice?.toDouble()?.formatRupiah()
                    tvDate.text = detail.transactionDate?.dateformat()

                }
                binding.apply {
                    btnTerima.setOnClickListener {
                        val updateStatus = UpdateStatusProductReq(
                            "accepted"
                        )
                        viewModel.updateStatusOrder(args, updateStatus)
                    }
                    btnTolak.setOnClickListener {
                        viewModel.updateStatusOrder(args, UpdateStatusProductReq("declined"))
                    }
                }

                viewModel.updateStatusOrder.apply {
                    onLoading = {
                        progressDialog = DialogWindow.progressCircle(
                            requireContext(),
                            "Mengupdate status order...",
                            true
                        )
                    }

                    onSuccess = { data ->
                        progressDialog?.dismiss()
                        if (data.status == "accepted") {
                            showDialogSuccess(data, detail)
                        }else{
                            findNavController().popBackStack()
                        }
                    }
                }
            }

            onFailure = { _, _ ->
                progressDialog?.dismiss()
            }

        }

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    @SuppressLint("InflateParams")
    private fun showDialogSuccess(
        data: UpdateStatusProduct,
        productDetail: SellerProductInterestedEntity
    ) {
        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog_success, null)
        dialog.setContentView(view)
        dialog.show()

        val imgAkun = view.findViewById<ImageView>(R.id.iv_akun)
        val imgProduct = view.findViewById<ImageView>(R.id.iv_product)
        val txtName = view.findViewById<TextView>(R.id.txtName)
        val txtCity = view.findViewById<TextView>(R.id.txtCity)
        val txtProductName = view.findViewById<TextView>(R.id.tvProductName)
        val txtBasePrice = view.findViewById<TextView>(R.id.tvBasePrice)
        val txtOffering = view.findViewById<TextView>(R.id.tvDitawar)
        val btnCall = view.findViewById<MaterialButton>(R.id.btnCall)

        val phone = productDetail.user?.phoneNumber
        txtName.text = productDetail.user?.fullName
        txtCity.text = productDetail.user?.city
        txtProductName.text = data.productName
        Glide.with(view).load(productDetail.user?.imageUrl).into(imgAkun)
        Glide.with(view).load(data.imageProduct).into(imgProduct)
        txtOffering.text = resources.getString(R.string.fixPrice, data.price?.toDouble()?.formatRupiah())
        txtBasePrice.apply {
            text = "Harga awal "+data.basePrice?.toDouble()?.formatRupiah()
            paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        btnCall.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=+62$phone&text=Halo, Selamat penawaran anda terpilih untuk membeli produk kami"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}