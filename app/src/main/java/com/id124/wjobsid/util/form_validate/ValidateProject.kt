package com.id124.wjobsid.util.form_validate

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidateProject {
    companion object {
        fun valProjectName(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter your project name!"
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
                inputLayout.helperText = "Please enter your project description!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }

        fun valDeadline(inputLayout: TextInputLayout, editText: EditText): Boolean {
            val text = editText.text.toString().trim()

            if (text.isEmpty()) {
                inputLayout.isHelperTextEnabled = true
                inputLayout.helperText = "Please enter deadline of your project!"
                editText.requestFocus()

                return false
            } else {
                inputLayout.isHelperTextEnabled = false
            }

            return true
        }
    }
}