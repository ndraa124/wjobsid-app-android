package com.id124.wjobsid.util.form_validate

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class ValidateSkill {
    companion object {
        fun valSkill(inSkil: TextInputLayout, etSkill: EditText): Boolean {
            val text = etSkill.text.toString().trim()

            if (text.isEmpty()) {
                inSkil.isHelperTextEnabled = true
                inSkil.helperText = "Please enter your skill!"
                etSkill.requestFocus()

                return false
            } else {
                inSkil.isHelperTextEnabled = false
            }

            return true
        }
    }
}