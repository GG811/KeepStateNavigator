package cn.navigation.app.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.navigation.app.R
import kotlinx.android.synthetic.main.fragment_child.*

class CFragment : Fragment(R.layout.fragment_child) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = resources.getText(R.string.c_content)
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        next.setOnClickListener {
            findNavController().navigate(CFragmentDirections.actionCFragmentSelf())
        }
    }


}