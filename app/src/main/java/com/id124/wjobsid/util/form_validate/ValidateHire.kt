package com.id124.wjobsid.util.form_validate

import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.textfield.TextInputLayout

class ValidateHire {
    companion object {
        fun valPrice(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter price of your project!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valMessage(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your message!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }
    }
}