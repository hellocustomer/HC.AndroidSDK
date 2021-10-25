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
                authorizationToken = "eyJhbGciOiAiSFM1MTIiLCAidHlwIjogIkpXVCJ9.eyJhdWQiOiJhNDk2NTY1MC03MGVhLTRhZmMtOTBlOS01YTJmZjRmNzFjMjkiLCJzdWIiOiJlNGE2ZmNkZi0yYmE3LTQ3ZDQtYTA0NS0wNTAyZmI2ZTNmMGYiLCJpc3MiOiJodHRwczovL2hlbGxvY3VzdG9tZXIuY29tIiwiaWF0IjoxNjMxNzkyMjAzLCJleHAiOjE2NjMzMjgxNDUsIm1kIjp7InByIjp7ImExciI6ZmFsc2UsImFtciI6ZmFsc2UsInJiZSI6ZmFsc2UsInJ1ZSI6ZmFsc2UsInJyIjpmYWxzZSwiZ25wcyI6dHJ1ZSwiZ2NlcyI6dHJ1ZSwiZ2NzYXQiOnRydWUsImFhbnN3Ijp0cnVlfSwiaXAiOm51bGwsInRwaWRzIjpbIjIxY2VjYWI0LTljNjEtNDk0OC05MjNkLWQ2YjM4MjFiMzQzOSIsIjg1ZDNhYWQ3LWYxNTUtNDBhZS1iODM1LWU1MTM3ZDdiY2VjYiIsImQ1ZjA3NWI3LTg1NWUtNDQ1My04NmJiLWU4MTYxNDhmNzM2ZSJdfX0=.92NqRJ9qWh2WA8S/XbASdh+2lOPcrCMgoQKoy3+iCFR56ApiyQjW8hJ9b0URbkwKEvZKBv6pL4heNB9jc1NWyA==",
                companyId = "a4965650-70ea-4afc-90e9-5a2ff4f71c29",
                touchpointId = "85d3aad7-f155-40ae-b835-e5137d7bcecb",
            ),
            onSuccess = ::handleSuccess
        )
    }

    private fun loadTc2() {
        loadTouchpoint(
            context = this,
            config = HelloCustomerTouchpointConfig(
                authorizationToken = "eyJhbGciOiAiSFM1MTIiLCAidHlwIjogIkpXVCJ9.eyJhdWQiOiJhNDk2NTY1MC03MGVhLTRhZmMtOTBlOS01YTJmZjRmNzFjMjkiLCJzdWIiOiJlNGE2ZmNkZi0yYmE3LTQ3ZDQtYTA0NS0wNTAyZmI2ZTNmMGYiLCJpc3MiOiJodHRwczovL2hlbGxvY3VzdG9tZXIuY29tIiwiaWF0IjoxNjMxNzkyMjAzLCJleHAiOjE2NjMzMjgxNDUsIm1kIjp7InByIjp7ImExciI6ZmFsc2UsImFtciI6ZmFsc2UsInJiZSI6ZmFsc2UsInJ1ZSI6ZmFsc2UsInJyIjpmYWxzZSwiZ25wcyI6dHJ1ZSwiZ2NlcyI6dHJ1ZSwiZ2NzYXQiOnRydWUsImFhbnN3Ijp0cnVlfSwiaXAiOm51bGwsInRwaWRzIjpbIjIxY2VjYWI0LTljNjEtNDk0OC05MjNkLWQ2YjM4MjFiMzQzOSIsIjg1ZDNhYWQ3LWYxNTUtNDBhZS1iODM1LWU1MTM3ZDdiY2VjYiIsImQ1ZjA3NWI3LTg1NWUtNDQ1My04NmJiLWU4MTYxNDhmNzM2ZSJdfX0=.92NqRJ9qWh2WA8S/XbASdh+2lOPcrCMgoQKoy3+iCFR56ApiyQjW8hJ9b0URbkwKEvZKBv6pL4heNB9jc1NWyA==",
                companyId = "a4965650-70ea-4afc-90e9-5a2ff4f71c29",
                touchpointId = "d5f075b7-855e-4453-86bb-e816148f736e",
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
                authorizationToken = "eyJhbGciOiAiSFM1MTIiLCAidHlwIjogIkpXVCJ9.eyJhdWQiOiJhNDk2NTY1MC03MGVhLTRhZmMtOTBlOS01YTJmZjRmNzFjMjkiLCJzdWIiOiJlNGE2ZmNkZi0yYmE3LTQ3ZDQtYTA0NS0wNTAyZmI2ZTNmMGYiLCJpc3MiOiJodHRwczovL2hlbGxvY3VzdG9tZXIuY29tIiwiaWF0IjoxNjMxNzkyMjAzLCJleHAiOjE2NjMzMjgxNDUsIm1kIjp7InByIjp7ImExciI6ZmFsc2UsImFtciI6ZmFsc2UsInJiZSI6ZmFsc2UsInJ1ZSI6ZmFsc2UsInJyIjpmYWxzZSwiZ25wcyI6dHJ1ZSwiZ2NlcyI6dHJ1ZSwiZ2NzYXQiOnRydWUsImFhbnN3Ijp0cnVlfSwiaXAiOm51bGwsInRwaWRzIjpbIjIxY2VjYWI0LTljNjEtNDk0OC05MjNkLWQ2YjM4MjFiMzQzOSIsIjg1ZDNhYWQ3LWYxNTUtNDBhZS1iODM1LWU1MTM3ZDdiY2VjYiIsImQ1ZjA3NWI3LTg1NWUtNDQ1My04NmJiLWU4MTYxNDhmNzM2ZSJdfX0=.92NqRJ9qWh2WA8S/XbASdh+2lOPcrCMgoQKoy3+iCFR56ApiyQjW8hJ9b0URbkwKEvZKBv6pL4heNB9jc1NWyA==",
                companyId = "a4965650-70ea-4afc-90e9-5a2ff4f71c29",
                touchpointId = "21cecab4-9c61-4948-923d-d6b3821b3439"
            ),
            onSuccess = ::handleSuccess
        )
    }

    private fun handleSuccess(dialog: HelloCustomerDialog) {
        dialog.show(supportFragmentManager)
    }
}