package com.example.countdowntimertask

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.countdowntimertask.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        printNumOdSecondsInLog()
        btnClickListener()
    }

    fun btnClickListener() {
        binding.buttonCountDown.setOnClickListener {
            val millis = System.currentTimeMillis()
            val currentTimeSeconds = millis / 1000

            if (binding.editText.text.toString().isNotBlank()) {
                val secondsInput = binding.editText.text.toString()
                val l: Long = secondsInput.toLong()
                if (l > currentTimeSeconds) {
                    val formatter = SimpleDateFormat("dd:hh:mm:ss", Locale.ENGLISH)
                    val dateString: kotlin.String = formatter.format(Date(l * 1000L))
                    binding.textViewCountDownTarget.text = dateString
                    //make counter here

                    val current: kotlin.String = formatter.format(Date(currentTimeSeconds * 1000L))
                    binding.textViewCountDown.text = current
                    countDown(l * 1000)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.enter_seconds_bigger_than_current_time),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Toast.makeText(this, getString(R.string.enter_seconds_if_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun countDown(noOfMinutes: Long) {
        countDownTimer = object : CountDownTimer(noOfMinutes, 1000) {
            override fun onTick(p0: Long) {
                val millis: Long = p0
                val diff = millis - System.currentTimeMillis()
                var x: Long = diff / 1000
                val seconds = x % 60
                x /= 60
                val minutes = x % 60
                x /= 60
                val hours = x % 24
                x /= 24
                val days = x
                val hms = "$days:$hours:$minutes:$seconds"



                System.out.println("Time : " + hms)
                binding.textViewCountDown.setText(hms)
            }

            override fun onFinish() {
                /*clearing all fields and displaying countdown finished message          */
                binding.textViewCountDown.setText("Count down completed");
                System.out.println("Time up")
            }
        }
        countDownTimer!!.start()
    }

    override fun onStop() {
        super.onStop()
        countDownTimer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        countDownTimer?.start()
    }

    fun printNumOdSecondsInLog() {
        val c = Calendar.getInstance()
            .apply {
                add(Calendar.DAY_OF_MONTH, 2)
            }
        Log.e("Time", "Time is ${c.timeInMillis / 1000}")
    }
}