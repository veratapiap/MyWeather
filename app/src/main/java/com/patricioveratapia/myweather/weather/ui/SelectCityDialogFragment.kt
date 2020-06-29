package com.patricioveratapia.myweather.weather.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.patricioveratapia.myweather.R
import com.patricioveratapia.myweather.weather.extensions.setRipple
import com.patricioveratapia.myweather.weather.extensions.toast
import com.patricioveratapia.myweather.weather.ui.adapter.CityAdapter
import com.patricioveratapia.myweather.weather.ui.interfaces.DialogListener
import com.patricioveratapia.myweather.weather.util.State
import kotlinx.android.synthetic.main.dialog_fragment_select_city.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectCityDialogFragment : DialogFragment() {

    companion object {

        fun newInstance(): SelectCityDialogFragment {

            return SelectCityDialogFragment()
        }
    }

    private val loadingView by lazy { dialog_fragment_select_city_loading_view }

    private val recyclerView by lazy { dialog_fragment_select_city_recycler_view }

    private val buttonCurrentLocation by lazy { dialog_fragment_select_city_current_location_button }

    private val container by lazy { dialog_fragment_select_city_container }


    private val cityViewModel: CityViewModel by viewModel()

    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_select_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.let {

            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        setup()

        setupRecyclerView()

        setupCityListener()

        setupCurrentLocation()

        observeCities()

    }

    private fun setup() {

        container.setOnClickListener {

            dismiss()
        }
    }

    private fun setupCurrentLocation() {

        buttonCurrentLocation.setRipple()

        buttonCurrentLocation.setOnClickListener {

            if (parentFragment is DialogListener) {

                (parentFragment as DialogListener).onCurrentLocationSelected()

                dismiss()
            }
        }

    }

    private fun setupCityListener() {

        cityAdapter.onCityClickListener = {

            if (parentFragment is DialogListener) {

                (parentFragment as DialogListener).onCitySelected(it)

                dismiss()
            }
        }

    }

    private fun setupRecyclerView() {

        cityAdapter = CityAdapter()

        recyclerView.adapter = cityAdapter
    }

    private fun observeCities() {

        cityViewModel.cities.observe(viewLifecycleOwner, Observer {

            when (it) {

                is State.Success -> {

                    hideLoading()

                    showList(it.data)
                }

                is State.Error -> {

                    this.toast(getString(R.string.error_message))

                    dismiss()
                }

                is State.Loading -> showLoading()

            }
        })
    }

    private fun hideLoading() {

        loadingView.visibility = View.GONE

    }

    private fun showLoading() {

        loadingView.visibility = View.VISIBLE

    }

    private fun showList(data: List<String>?) {

        data?.let {

            cityAdapter.submitList(it)
        }
    }

}