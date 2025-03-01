package com.example.sensorexplorer.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sensorexplorer.R

class SensorAvailabilityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sensor_availability, container, false)

        val sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Liste des capteurs à vérifier
        val sensorsToCheck = listOf(
            Sensor.TYPE_ACCELEROMETER to "Accelerometer",
            Sensor.TYPE_GYROSCOPE to "Gyroscope",
            Sensor.TYPE_LIGHT to "Light Sensor",
            Sensor.TYPE_PROXIMITY to "Proximity Sensor",
            Sensor.TYPE_MAGNETIC_FIELD to "Magnetometer",
            Sensor.TYPE_PRESSURE to "Barometer (Pressure Sensor)"
        )

        val sensorStatusTextView = view.findViewById<TextView>(R.id.sensor_status_text)

        val result = StringBuilder()
        for ((sensorType, sensorName) in sensorsToCheck) {
            val sensor = sensorManager.getDefaultSensor(sensorType)
            if (sensor != null) {
                result.append("✅ $sensorName is available\n")
            } else {
                result.append("❌ $sensorName is NOT available\n")
            }
        }

        sensorStatusTextView.text = result.toString()

        return view
    }
}
