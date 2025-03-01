package com.example.sensorexplorer.fragments

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.sensorexplorer.R
import kotlin.math.sqrt

class AccelerometerFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var rootView: View? = null
    private lateinit var instructionText: TextView
    private lateinit var instructionCard: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false)

        // Find views
        instructionText = rootView!!.findViewById(R.id.instruction_text)
        instructionCard = rootView!!.findViewById(R.id.instruction_card)

        // Apply custom font
        val robotoFont = ResourcesCompat.getFont(requireContext(), R.font.roboto_con)
        instructionText.typeface = robotoFont

        // Initial instruction
        instructionText.text = "Move your phone and observe the color change."

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val acceleration = sqrt(
                (event.values[0] * event.values[0] +
                        event.values[1] * event.values[1] +
                        event.values[2] * event.values[2]).toDouble()
            )

            val (backgroundColor, message) = when {
                acceleration < 5 -> Pair(Color.GREEN, "Low movement: Stable ✅")
                acceleration < 10 -> Pair(Color.YELLOW, "Moderate movement: Be careful ⚠️")
                else -> Pair(Color.RED, "Strong movement: Shake detected ❗")
            }

            rootView?.setBackgroundColor(backgroundColor)
            instructionText.text = message

            // Change CardView background color dynamically
            instructionCard.setCardBackgroundColor(Color.WHITE)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
