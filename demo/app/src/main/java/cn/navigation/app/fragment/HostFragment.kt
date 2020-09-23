package cn.navigation.app.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.navigation.app.R
import kotlinx.android.synthetic.main.fragment_host.*

class HostFragment : Fragment(R.layout.fragment_host) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jump.setOnClickListener {
            findNavController().navigate(HostFragmentDirections.actionHostFragmentToAFragment())
        }
    }
}