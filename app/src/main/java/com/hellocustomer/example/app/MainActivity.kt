package com.hellocustomer.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.hellocustomer.example.BuildConfig
import com.hellocustomer.example.R
import com.hellocustomer.example.databinding.ActivityMainBinding
import com.hellocustomer.sdk.HelloCustomerTouchpointConfig
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import com.hellocustomer.sdk.font.FontBuilder
import com.hellocustomer.sdk.loadTouchpoint
import java.util.UUID

typealias ActivityBindingInflater<Binding> = (layoutInflater: LayoutInflater) -> Binding

class MainActivity : AppCompatActivity() {

    private val bindingInflater: ActivityBindingInflater<ActivityMainBinding>
        get() = ActivityMainBinding::inflate

    private var binding: ActivityMainBinding? = null

    private val requireBinding: ActivityMainBinding
        get() = requireNotNull(binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding!!.root)
        setupView()
    }

    private fun setupView() {
        requireBinding.button1.setOnClickListener { loadTc1() }
        requireBinding.button2.setOnClickListener { loadTc2() }
        requireBinding.button3.setOnClickListener { loadTc3() }
    }

    private fun loadTc1() {
        loadTouchpoint(
            config = HelloCustomerTouchpointConfig(
                authorizationToken = BuildConfig.HcAuthToken,
                companyId = UUID.fromString(BuildConfig.HcCompanyId),
                touchpointId = UUID.fromString(BuildConfig.HcTouchpoint1Id),
            ),
            onSuccess = ::handleSuccess
        )
    }

    private fun loadTc2() {
        loadTouchpoint(
            context = this,
            config = HelloCustomerTouchpointConfig(
                authorizationToken = BuildConfig.HcAuthToken,
                companyId = UUID.fromString(BuildConfig.HcCompanyId),
                touchpointId = UUID.fromString(BuildConfig.HcTouchpoint2Id),
                metadata = mapOf(
                    "appVersion" to BuildConfig.VERSION_CODE.toString()
                ),
                respondentFirstName = "John",
                respondentLastName = "Smith",
                respondentEmailAddress = "john@smith.com",
                questionFont = FontBuilder.FromId(R.font.bad_script),
                hintFont = FontBuilder.FromId(R.font.architects_daughter),
            ),
            onSuccess = { helloCustomerDialog: HelloCustomerDialog ->
                helloCustomerDialog.show(supportFragmentManager)
            },
            onError = { throwable: Throwable ->
                Log.e("ERROR", "An error occurred.", throwable)
            }
        )
    }

    private fun loadTc3() {
        loadTouchpoint(
            config = HelloCustomerTouchpointConfig(
                authorizationToken = BuildConfig.HcAuthToken,
                companyId = UUID.fromString(BuildConfig.HcCompanyId),
                touchpointId = UUID.fromString(BuildConfig.HcTouchpoint3Id),
            ),
            onSuccess = ::handleSuccess
        )
    }

    private fun handleSuccess(dialog: HelloCustomerDialog) {
        dialog.show(supportFragmentManager)
    }
}