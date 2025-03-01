package com.example.sensorexplorer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sensorexplorer.R

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        view.findViewById<Button>(R.id.btn_sensors).setOnClickListener {
            navController.navigate(R.id.sensorsListFragment)
        }



        view.findViewById<Button>(R.id.btn_sensor_availability).setOnClickListener {
            navController.navigate(R.id.sensorAvailabilityFragment)
        }

               view.findViewById<Button>(R.id.btn_accelerometer).setOnClickListener {
                   navController.navigate(R.id.accelerometerFragment)
               }

                      view.findViewById<Button>(R.id.btn_direction).setOnClickListener {
                          navController.navigate(R.id.directionFragment)
                      }

                              view.findViewById<Button>(R.id.btn_shake_flash).setOnClickListener {
                                  navController.navigate(R.id.shakeFlashFragment)
                              }

                                            view.findViewById<Button>(R.id.btn_proximity).setOnClickListener {
                                                navController.navigate(R.id.proximityFragment)
                                            }

                                                       view.findViewById<Button>(R.id.btn_geolocation).setOnClickListener {
                                                           navController.navigate(R.id.geolocationFragment)
                                                       }

    }
}
