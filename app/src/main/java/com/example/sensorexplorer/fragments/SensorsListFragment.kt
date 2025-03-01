package com.example.sensorexplorer.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.sensorexplorer.R

class SensorsListFragment : Fragment(R.layout.fragment_sensors_list) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        val sensorNames = sensorList.map { it.name }

        val listView = view.findViewById<ListView>(R.id.listViewSensors)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, sensorNames)
        listView.adapter = adapter


        listView.isFastScrollEnabled = true
    }
}


