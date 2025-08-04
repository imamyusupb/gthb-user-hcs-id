package com.hcs.gitmeshow.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.hcs.gitmeshow.R
import com.hcs.gitmeshow.alarm.AlarmReceiver

class MySettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminderPreferences: SwitchPreferenceCompat
    private lateinit var reminder: String
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        alarmReceiver = AlarmReceiver()

        initReminder()
        initSharedPreferences()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun initReminder() {
        reminder = getString(R.string.reminder)
        reminderPreferences = findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
    }

    private fun initSharedPreferences() {
        val sh = preferenceManager.sharedPreferences
        if (sh != null) {
            reminderPreferences.isChecked = sh.getBoolean(reminder, false)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminder) {
            if (sharedPreferences != null) {
                reminderPreferences.isChecked = sharedPreferences.getBoolean(reminder, false)
            }
        }

        val state: Boolean = context?.let { PreferenceManager.getDefaultSharedPreferences(it).getBoolean(reminder, false) } == true

        setReminder(state)
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let { alarmReceiver.setRepeatingAlarm(it) }
        } else {
            context?.let { alarmReceiver.cancelAlarm(it) }
        }
    }


}