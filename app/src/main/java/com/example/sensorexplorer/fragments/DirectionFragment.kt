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
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.sensorexplorer.R

class DirectionFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var directionTextView: TextView? = null

    private lateinit var arrows: List<ImageView>
    private var activeArrow: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_direction, container, false)
        directionTextView = view.findViewById(R.id.text_direction)

        arrows = listOf(
            view.findViewById(R.id.arrow_top_left),
            view.findViewById(R.id.arrow_up),
            view.findViewById(R.id.arrow_top_right),
            view.findViewById(R.id.arrow_left),
            view.findViewById(R.id.arrow_right),
            view.findViewById(R.id.arrow_bottom_left),
            view.findViewById(R.id.arrow_down),
            view.findViewById(R.id.arrow_bottom_right)
        )

        return view
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
            val x = event.values[0]  // Détection gauche/droite
            val y = event.values[1]  // Détection haut/bas

            val (direction, arrowIndex) = when {
                x < -3 && y > 3 -> "Top Right" to 2
                x > 3 && y > 3 -> "Top Left" to 0
                x < -3 && y < -3 -> "Bottom Right" to 7
                x > 3 && y < -3 -> "Bottom Left" to 5
                x < -3 -> "Right" to 4
                x > 3 -> "Left" to 3
                y > 3 -> "Up" to 1
                y < -3 -> "Down" to 6
                else -> "Stationary" to -1
            }

            directionTextView?.text = "Direction: $direction"

            // Réinitialiser toutes les flèches en noir
            arrows.forEach { it.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black)) }

            // Mettre la bonne flèche en rouge
            if (arrowIndex >= 0) {
                arrows[arrowIndex].setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                activeArrow = arrows[arrowIndex]
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Rien à faire ici
    }
}
