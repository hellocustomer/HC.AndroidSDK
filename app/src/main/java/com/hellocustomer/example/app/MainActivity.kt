package com.hellocustomer.example.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.hellocustomer.example.R
import com.hellocustomer.example.databinding.ActivityMainBinding
import com.hellocustomer.sdk.createTouchpoint
import java.util.concurrent.atomic.AtomicBoolean

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
        if (State.isDisplayed.compareAndSet(false, true)) {
            createTouchpoint("asdasd")
        }
    }
}

/**
 * Dummy state. Don't do this at home.
 */
object State {
    val isDisplayed = AtomicBoolean(false)
}