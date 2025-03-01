package com.example.sensorexplorer.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.sensorexplorer.R

class ShakeFlashFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var lastTime: Long = 0
    private var isFlashOn = false
    private lateinit var flashAnimation: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shake_flash, container, false)
        flashAnimation = view.findViewById(R.id.flashAnimation) // Récupération de l'icône
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
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
        event?.let {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastTime) > 500) { // Éviter les activations multiples
                val x = it.values[0]
                val y = it.values[1]
                val z = it.values[2]

                val deltaX = Math.abs(x - lastX)
                val deltaY = Math.abs(y - lastY)
                val deltaZ = Math.abs(z - lastZ)

                if ((deltaX > 12 || deltaY > 12 || deltaZ > 12)) { // Détection du secouement
                    toggleFlashlight()
                }

                lastX = x
                lastY = y
                lastZ = z
                lastTime = currentTime
            }
        }
    }

    private fun toggleFlashlight() {
        val cameraManager = requireActivity().getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0] // Caméra arrière
            isFlashOn = !isFlashOn
            cameraManager.setTorchMode(cameraId, isFlashOn)

            // ✅ Changer l'icône du flash
            if (isFlashOn) {
                flashAnimation.setImageResource(R.drawable.ic_flash_on)
            } else {
                flashAnimation.setImageResource(R.drawable.ic_flash_off)
            }

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
