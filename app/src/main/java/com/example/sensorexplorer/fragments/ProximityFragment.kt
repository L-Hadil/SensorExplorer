package com.example.sensorexplorer.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.sensorexplorer.R

class ProximityFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    private lateinit var proximityImage: ImageView
    private lateinit var proximityText: TextView
    private lateinit var proximityCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_proximity, container, false)

        proximityImage = view.findViewById(R.id.proximityImage)
        proximityText = view.findViewById(R.id.proximityText)
        proximityCardView = view.findViewById(R.id.proximityCardView)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximitySensor == null) {
            proximityImage.setImageResource(R.drawable.ic_far)
            proximityText.text = "Proximity sensor not available on this device."
        }
    }

    override fun onResume() {
        super.onResume()
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.values[0] < proximitySensor!!.maximumRange) {
                // Object is near
                proximityImage.setImageResource(R.drawable.ic_near)
                proximityText.text = "Object detected!"
            } else {
                // Object is far
                proximityImage.setImageResource(R.drawable.ic_far)
                proximityText.text = "No object detected, Bring an object closer!"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
