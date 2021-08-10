package io.github.thang86.weathertest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.thang86.weathertest.R
import io.github.thang86.weathertest.databinding.WeatherFragmentBinding

/**
 *
 *    WeatherFragment.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    lateinit var binding: WeatherFragmentBinding


    private val mainViewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.weather_fragment,
            container,
            false
        )
        binding.apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.search()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}