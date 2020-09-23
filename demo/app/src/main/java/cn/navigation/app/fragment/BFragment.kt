package cn.navigation.app.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cn.navigation.app.R
import kotlinx.android.synthetic.main.fragment_child.*

class BFragment : Fragment(R.layout.fragment_child) {

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = "${resources.getText(R.string.b_content)}  ${System.currentTimeMillis()}"
        back.setOnClickListener {
            findNavController().popBackStack()
        }

        next.setOnClickListener {
            findNavController().navigate(BFragmentDirections.actionBFragmentToCFragment())
        }
    }


}