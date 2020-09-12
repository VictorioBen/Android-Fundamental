package com.workspace.githubappconsumer2.activity.activity


import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.workspace.githubappconsumer2.R
import com.workspace.githubappconsumer2.activity.broadcast.AlarmReceiver
import com.workspace.githubappconsumer2.activity.utils.TimePickerFragment
import kotlinx.android.synthetic.main.activity_alarm.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity(), View.OnClickListener,
    TimePickerFragment.DialogTimeListener {
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var sharedPreferences: SharedPreferences


    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
        private const val switch = "status"
        private const val pref = "preferences"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        title = getString(R.string.alarm)
        btnTime.setOnClickListener(this)
        alarmReceiver = AlarmReceiver()
        sharedPreferences = getSharedPreferences(pref, MODE_PRIVATE)
        val time = tvTime.text.toString()
        setAlarm(time)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnTime -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
        }
    }


    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        tvTime.text = dateFormat.format(calendar.time)
        tvTimeAt.text = dateFormat.format(calendar.time)

    }


    private fun setAlarm(time: String) {
        val notifId = AlarmReceiver.ID_REPEATING
        val editor = sharedPreferences.edit()
        alarmSwitch.isChecked = sharedPreferences.getBoolean(switch, false)
        alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(
                    this, time, notifId
                )

                editor.putBoolean(switch, true)


            } else {
                alarmReceiver.cancelAlarm(this, notifId)
                editor.putBoolean(switch, false)

            }
            editor.apply()
        }


    }


}




