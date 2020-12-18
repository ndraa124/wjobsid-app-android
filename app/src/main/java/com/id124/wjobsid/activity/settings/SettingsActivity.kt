package com.id124.wjobsid.activity.settings

import android.os.Bundle
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.id124.wjobsid.R
import com.id124.wjobsid.activity.BaseActivity
import com.id124.wjobsid.databinding.ActivitySettingsEngineerBinding

class SettingsActivity : BaseActivity<ActivitySettingsEngineerBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_settings_engineer
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = "Edit Profile"

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment(sharedPref.getLevelUser()))
                .commit()
        }
    }

    class SettingsFragment(private val level: Int) : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            if (level == 0) {
                setPreferencesFromResource(R.xml.settings_engineer, rootKey)
                prefsAccount()
                prefsEngineer()
            } else {
                setPreferencesFromResource(R.xml.settings_company, rootKey)
                prefsAccount()
                prefsCompany()
            }
        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            when (preference?.key) {
                "ac_name" -> {
                    Log.d("acName", "$newValue")
                }
                "ac_email" -> {
                    Log.d("acEmail", "$newValue")
                }
                "ac_phone" -> {
                    Log.d("acPhone", "$newValue")
                }
            }

            when (preference?.key) {
                "en_job_title" -> {
                    Log.d("enJobTitle", "$newValue")
                }
                "en_job_type" -> {
                    Log.d("enJobType", "$newValue")
                }
                "en_domicile" -> {
                    Log.d("enDomicile", "$newValue")
                }
                "en_description" -> {
                    Log.d("enDescription", "$newValue")
                }
            }

            when (preference?.key) {
                "cn_company" -> {
                    Log.d("cnCompany", "$newValue")
                }
                "cn_position" -> {
                    Log.d("cnPosition", "$newValue")
                }
                "cn_field" -> {
                    Log.d("cnField", "$newValue")
                }
                "cn_description" -> {
                    Log.d("cnDescription", "$newValue")
                }
                "cn_instagram" -> {
                    Log.d("cnInstagram", "$newValue")
                }
                "cn_linkedin" -> {
                    Log.d("cnLinkedin", "$newValue")
                }
            }

            return true
        }

        private fun prefsAccount() {
            val acName = findPreference<EditTextPreference>("ac_name")
            val acEmail = findPreference<EditTextPreference>("ac_email")
            val acPhone = findPreference<EditTextPreference>("ac_phone")

            acName?.onPreferenceChangeListener = this@SettingsFragment
            acEmail?.onPreferenceChangeListener = this@SettingsFragment
            acPhone?.onPreferenceChangeListener = this@SettingsFragment

            acName?.text = "Indra David Pesik"
            acEmail?.text = "indradavidpesik@gmail.com"
            acPhone?.text = "082292192488"
        }

        private fun prefsEngineer() {
            val enJobTitle = findPreference<ListPreference>("en_job_title")
            val enJobType = findPreference<ListPreference>("en_job_type")
            val enDomicile = findPreference<EditTextPreference>("en_domicile")
            val enDescription = findPreference<EditTextPreference>("en_description")

            enJobTitle?.onPreferenceChangeListener = this@SettingsFragment
            enJobType?.onPreferenceChangeListener = this@SettingsFragment
            enDomicile?.onPreferenceChangeListener = this@SettingsFragment
            enDescription?.onPreferenceChangeListener = this@SettingsFragment

            enJobTitle?.value = "web developer"
            enJobType?.value = "freelance"
            enDomicile?.text = "Manado, Sulawesi Utara"
            enDescription?.text = "Lorem ipsum"
        }

        private fun prefsCompany() {
            val cnCompany = findPreference<EditTextPreference>("cn_company")
            val cnPosition = findPreference<EditTextPreference>("cn_position")
            val cnField = findPreference<EditTextPreference>("cn_field")
            val cnDescription = findPreference<EditTextPreference>("cn_description")
            val cnInstagram = findPreference<EditTextPreference>("cn_instagram")
            val cnLinkedin = findPreference<EditTextPreference>("cn_linkedin")

            cnCompany?.text = "PT. Kawanua Tech"
            cnPosition?.text = "Chief Technology Officer"
            cnField?.text = "Software Engineering"
            cnDescription?.text = "Lorem ipsum"
            cnInstagram?.text = "@ptkawanuatech"
            cnLinkedin?.text = "@kawanuatech"
        }
    }
}