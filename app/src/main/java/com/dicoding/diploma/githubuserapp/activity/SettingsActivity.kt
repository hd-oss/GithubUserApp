package com.dicoding.diploma.githubuserapp.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.diploma.githubuserapp.R
import com.dicoding.diploma.githubuserapp.alarm.AlarmReceiver

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var reminderswitch: SwitchPreferenceCompat
        private lateinit var alarmReceiver: AlarmReceiver
        private lateinit var key: String

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.root_preferences)
            init()
            setSummaries()
        }

        private fun init() {
            key = resources.getString(R.string.key_reminder)
            reminderswitch = findPreference<SwitchPreferenceCompat>(key) as SwitchPreferenceCompat
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(p0: SharedPreferences, p1: String?) {
            alarmReceiver = AlarmReceiver()
            if (p1 == key) {
                if (p0.getBoolean(key, true)) {
                    activity?.let {
                        alarmReceiver.setRepeatingAlarm(
                            it,
                            AlarmReceiver.TYPE_REMINDER,
                            "09:00"
                        )
                    }
                } else {
                    activity?.let { alarmReceiver.cancelAlarm((it)) }
                }
            }
        }

        private fun setSummaries() {
            val sh = preferenceManager.sharedPreferences
            reminderswitch.isChecked = sh.getBoolean(key, false)
        }
    }
}