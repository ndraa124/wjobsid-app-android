package com.id124.wjobsid.util.form_validate

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidateExperience {
    companion object {
        fun valPosition(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your position!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valCompany(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your company!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valStart(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter date start!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valEnd(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter date end!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valDescription(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your description!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }
    }
}