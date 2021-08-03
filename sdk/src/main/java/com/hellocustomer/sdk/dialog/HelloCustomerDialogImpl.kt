package com.hellocustomer.sdk.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.databinding.FragmentHelloCustomerDialogBinding
import com.hellocustomer.sdk.logger.defaultLogger
import com.hellocustomer.sdk.evaluation.EvaluationButtonView
import com.hellocustomer.sdk.survey.WebViewActivity

internal class HelloCustomerDialogImpl : DialogFragment(), HelloCustomerDialog {

    private val logger = defaultLogger()
    private lateinit var viewModel: HelloCustomerViewModel

    private var _binding: FragmentHelloCustomerDialogBinding? = null
    private val requireBinding: FragmentHelloCustomerDialogBinding
        get() = requireNotNull(_binding)

    private val config: HelloCustomerConfig
        get() = requireNotNull(requireArguments().getParcelable(ARG_CONFIG)) {
            "Dialog's config argument is null.".also(logger::e)
        }

    private val requireWindow: Window
        get() = requireNotNull(dialog?.window) {
            "Required dialog's window is null.".also(logger::e)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HelloCustomerViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentHelloCustomerDialogBinding.inflate(requireActivity().layoutInflater)

        setupView()

        return Dialog(requireContext()).apply {
            setContentView(requireBinding.root)

            requireNotNull(window) {
                "Cannot set the animation attribute. The dialog's window is null.".also(logger::e)
            }.also { window ->
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.attributes.windowAnimations = R.style.HelloCustomer_Dialog_Animation
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        observeViewModel()

        return view
    }

    override fun show(fragmentManager: FragmentManager, tag: String): HelloCustomerDialog = apply {
        super.show(fragmentManager, tag)
    }

    override fun show(fragmentManager: FragmentManager): HelloCustomerDialog =
        show(fragmentManager, this::class.java.simpleName)

    override fun dismissDialog(): HelloCustomerDialog = apply {
        super.dismiss()
    }

    private fun setupView(): Unit = with(requireBinding) {
        cardView.bind(config)
        cardView.closeButton.setOnClickListener {
            dismissDialog()
        }
        cardView.buttonClickListener = View.OnClickListener {
            viewModel.onEvaluate(url = config.surveyUrl)
            dismissDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.navigation.observe(this) { navigation ->
            when (navigation) {
                is HelloCustomerViewModel.Navigation.WebPage -> WebViewActivity.start(requireContext(), navigation.url)
            }
        }
    }

    companion object {

        private const val ARG_CONFIG = "ARG_CONFIG"

        fun newInstance(config: HelloCustomerConfig): HelloCustomerDialogImpl =
            HelloCustomerDialogImpl().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONFIG, config)
                }
            }
    }
}