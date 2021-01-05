package com.id124.wjobsid.util.form_validate

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidatePortfolio {
    companion object {
        fun valAppName(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your app name!"
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
                inputLayout.helperText = "Please enter app description!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valLinkPub(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your publication link!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valLinkRepo(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your repository link!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valWorkPlace(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your work place!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }
    }
}