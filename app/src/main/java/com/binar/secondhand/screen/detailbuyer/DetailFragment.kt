package com.binar.secondhand.screen.detailbuyer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.secondhand.R
import com.binar.secondhand.core.data.local.DataPreferences
import com.binar.secondhand.core.data.remote.detail.request.OrderRequest
import com.binar.secondhand.core.data.remote.detail.request.WishListRequest
import com.binar.secondhand.core.utils.DialogWindow
import com.binar.secondhand.core.utils.formatRupiah
import com.binar.secondhand.databinding.FragmentDetailBuyerBinding
import com.binar.secondhand.screen.akun.AkunViewModel
import com.binar.secondhand.screen.wishlist.WishlistViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBuyerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModel()
    private val viewModelAkun: AkunViewModel by viewModel()
    private val viewModelWishlist: WishlistViewModel by viewModel()
    var idProduct: Int = 0
    var idWishlist: Int = 0
    var name: String = ""
    var price: Int = 0
    var image: String = ""
    var checkProduct: Boolean = false
    var checkLoggedIn: Boolean = false
    var checkWishlist: Boolean = false
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


        val userManager = viewModelAkun.userManager
        val prefrences = DataPreferences.get
        val token = prefrences.token
        val data = arguments?.getInt("idProduct")
        idWishlist = arguments?.getInt("idWishlist")!!
        val isFromDaftarJual = arguments?.getBoolean("isFromDaftarJual")

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModelAkun.getUser()
        viewModelWishlist.getListWishlist()

        Log.d("token", token)

        userManager.onSuccess = {
            checkLoggedIn = true
        }
        userManager.onFailure = { _, _ ->
            checkLoggedIn = false
            binding.btnBuy.setText("Login untuk Order ")
            binding.btnBuy.isClickable = false
            binding.btnBuy.setBackgroundResource(R.drawable.shape_btn_detail_two)
        }



        if (isFromDaftarJual == true){
            binding.btnBuy.isVisible = false
        }

        with(viewModelWishlist.listWishlistStateEvent){
            onSuccess = {
                Log.d("PRODUCT ID",data.toString())
                Log.d("Wishlist","Success")
                for(i in 0..it.size-1){
                    if(it[i].productId == data){
                        Log.d("Wishlist","foundit")
                        idWishlist = it[i].id
                        binding.icFavoriteFalse.setImageResource(R.drawable.ic_favorite_true)
                        checkWishlist = true
                        break
                    }else{
                        binding.icFavoriteFalse.setImageResource(R.drawable.ic_favorite_false)
                        checkWishlist = false
                        Log.d("Wishlist","notfound")
                    }
                }
            }
        }
        viewModelWishlist.readStateWishlist().observe(viewLifecycleOwner){
            checkWishlist = it
        }
        viewModel.getDetailProduct(data ?: 0)
        Log.d("checkwishlist",checkWishlist.toString())

        binding.icFavoriteFalse.setOnClickListener {
            if(checkWishlist == false){
                val request: WishListRequest = WishListRequest(
                    idProduct
                )
                viewModel.addToWishlist(request)

                with(viewModel.wishlistStateEvent){
                    onSuccess = {
                        binding.icFavoriteFalse.setImageResource(R.drawable.ic_favorite_true)
                        viewModelWishlist.getListWishlist()
                        viewModelWishlist.stateWishlist.postValue(true)
                        with(viewModelWishlist.listWishlistStateEvent){
                            onSuccess = {
                              idWishlist = it.last().id
                            }
                        }
                    }
                }
            }else{

                viewModelWishlist.deleteWishlist(idWishlist)
                binding.icFavoriteFalse.setImageResource(R.drawable.ic_favorite_false)
                viewModelWishlist.stateWishlist.postValue(false)
                viewModelWishlist.setStringSucess().observe(viewLifecycleOwner){
                    print("Delete wishlist $it")
                }
            }


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
                viewModel.checkOrdersProduct()
            }
            onSuccess = {
                if(it!=null){
                    progressDialog?.dismiss()
                    with(viewModel.checkOrdersProductStateEvent) {
                        onSuccess = {
                            progressDialog?.dismiss()
                            for (i in 0..it.size - 1) {
                                checkProduct = if (it[i].productId == idProduct) {
                                    Log.d("Product ID", it[i].productId.toString())
                                    binding.btnBuy.setText("Order sedang dalam Proses..")
                                    binding.btnBuy.setBackgroundResource(R.drawable.shape_btn_detail_two)
                                    Toast.makeText(requireActivity(), it[i].status, Toast.LENGTH_LONG)
                                    true
                                } else {
                                    false
                                }
                            }

                        }

                        onFailure = { _, _ ->

                        }
                    }
                    progressDialog?.dismiss()
                    idProduct = it.id!!
                    name = it.name!!
                    price = it.base_price!!
                    image = it.image_url!!



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
                        bin.tvHarga.text = it.base_price.toDouble().formatRupiah()


                        if (token != null) {
                            bin.btnBuy.setOnClickListener {
                                Log.d("Check Product", checkProduct.toString())
                                Log.d("Nama Product", name)
                                Log.d("Harga Product", price.toString())

                                if (bin.btnBuy.text != "Order sedang dalam Proses..") {
                                    val dialog = BottomSheetDialog(requireActivity())
                                    val view =
                                        layoutInflater.inflate(R.layout.bottom_sheet_detail, null)

                                    val btnClose = view.findViewById<Button>(R.id.btnBuy)
                                    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
                                    val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
                                    val ivPoster = view.findViewById<ImageView>(R.id.ivPoster)
                                    val etPrice = view.findViewById<EditText>(R.id.etPrice)

                                    tvTitle.setText(name)
                                    tvPrice.setText(price.toDouble().formatRupiah())
                                    Glide.with(view).load(image).into(ivPoster)
                                    btnClose.setOnClickListener {
                                        val request: OrderRequest = OrderRequest(
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
                                                "Berhasil mengorder..",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            dialog.dismiss()
                                            progressDialog?.dismiss()
                                            Log.d("Order State", it.toString())
                                            findNavController().popBackStack()
                                        }
                                        onFailure = { _, _ ->
                                            progressDialog?.dismiss()
                                            Toast.makeText(
                                                requireContext(),
                                                "Barang sudah habis",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("Order State", "Failed")
                                        }
                                    }
                                    dialog.setCancelable(true)
                                    dialog.setContentView(view)
                                    dialog.show()
                                } else {
                                    bin.btnBuy.isClickable = false
                                    Toast.makeText(
                                        requireActivity(),
                                        "Pesanan Sudah di Order",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }
                        }
                        if (token == null) {
                            bin.btnBuy.setOnClickListener {
                                null
                            }
                        }


                    }
                }else{
                    findNavController().popBackStack()
                }

            }
            onFailure = { _, _ ->
                Toast.makeText(context,"Produk tidak tersedia",Toast.LENGTH_SHORT)

                progressDialog?.dismiss()
                progressDialog = DialogWindow.progressCircle(
                    requireContext(),
                    "Produk tidak tersedia",
                    true,
                )
                progressDialog?.dismiss()
                findNavController().popBackStack()
            }
        }

    }
}