package com.hellocustomer.sdk.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hellocustomer.sdk.SdkLogger
import com.hellocustomer.sdk.databinding.FragmentHelloCustomerBottomSheetBinding
import com.hellocustomer.sdk.survey.WebViewActivity

internal class HelloCustomerBottomSheetDialog : BottomSheetDialogFragment(), HelloCustomerDialog {

    private val logger = SdkLogger
    private lateinit var viewModel: HelloCustomerViewModel

    private var _binding: FragmentHelloCustomerBottomSheetBinding? = null
    private val requireBinding: FragmentHelloCustomerBottomSheetBinding
        get() = requireNotNull(_binding)

    private val config: HelloCustomerDialogConfig
        get() = requireNotNull(requireArguments().getParcelable(ARG_CONFIG)) {
            "Bottom sheet's config argument is null.".also(logger::e)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = HelloCustomerViewModelFactory(config = config)
        viewModel = ViewModelProvider(this, factory).get(HelloCustomerViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentHelloCustomerBottomSheetBinding.inflate(inflater, container, false)

        return requireBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    //region API

    override fun show(fragmentManager: FragmentManager, tag: String): HelloCustomerDialog = apply {
        super.show(fragmentManager, tag)
    }

    override fun show(fragmentManager: FragmentManager): HelloCustomerDialog =
        show(fragmentManager, this::class.java.simpleName)

    override fun dismissDialog(): HelloCustomerDialog = apply {
        super.dismiss()
    }

    //endregion

    private fun setupView(): Unit = with(requireBinding) {
        cardView.bind(config)
        cardView.closeButton.setOnClickListener {
            dismissDialog()
        }
        cardView.setOnEvaluateClickListener { score ->
            viewModel.onEvaluate(score = score)
            dismissDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.navigation.observe(viewLifecycleOwner) { navigation ->
            when (navigation) {
                is HelloCustomerViewModel.Navigation.WebPage -> WebViewActivity.start(requireContext(), navigation.url)
            }
        }
    }

    companion object {

        private const val ARG_CONFIG = "ARG_CONFIG"

        fun newInstance(config: HelloCustomerDialogConfig): HelloCustomerBottomSheetDialog =
            HelloCustomerBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONFIG, config)
                }
            }
    }
}