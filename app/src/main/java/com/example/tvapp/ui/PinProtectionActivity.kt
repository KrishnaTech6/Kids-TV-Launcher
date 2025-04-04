package com.example.tvapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tvapp.R

class PinProtectionActivity : AppCompatActivity() {
    private lateinit var pinInput: EditText
    private lateinit var submitButton: Button
    private var appPackage: String? = null
    private var isExitProtection: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pin)

        pinInput = findViewById(R.id.pinInput)
        submitButton = findViewById(R.id.submitButton)

        appPackage = intent.getStringExtra("APP_PACKAGE")
        isExitProtection = intent.getBooleanExtra("EXIT_PROTECTION", false)

        submitButton.setOnClickListener {
            val enteredPin = pinInput.text.toString()
            if ( enteredPin == CORRECT_PIN ) {
                if (isExitProtection) {
                    // Exit the app
                    finishAffinity()
                } else {
                    appPackage?.let { pkg ->
                        val launchIntent = packageManager.getLaunchIntentForPackage(pkg)
                        if (launchIntent != null) {
                            startActivity(launchIntent)
                        } else {
                            Toast.makeText(this, "Cannot launch app!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    finish()
                }
            } else {
                Toast.makeText(this, "Incorrect PIN!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val CORRECT_PIN = "1234"
    }
}
