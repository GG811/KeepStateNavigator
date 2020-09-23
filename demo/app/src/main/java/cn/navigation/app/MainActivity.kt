package cn.navigation.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)!!
        val navigator =
                KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.fragment)
        navController.navigatorProvider.addNavigator(navigator)
        //navController.setGraph(R.navigation.navigation)
        navController.setGraph(R.navigation.navigation_keep)

        Timer().schedule(RefreshFragmentStack(), 0, 200)
    }

    private fun setFragmentStackData() {

        if (!supportFragmentManager.fragments[0].isAdded) return
        fragment_stack.text =
                supportFragmentManager.fragments[0].childFragmentManager.fragments.joinToString(
                        separator = "\n"
                ) {
                    it.javaClass.simpleName
                }

        if (supportFragmentManager.fragments[0].childFragmentManager.backStackEntryCount == 0) {
            fragment_back_stack.text = getString(R.string.empty)
            return
        }

        var text = ""
        (0 until supportFragmentManager.fragments[0].childFragmentManager.backStackEntryCount).forEach {
            val entry =
                    supportFragmentManager.fragments[0].childFragmentManager.getBackStackEntryAt(it)
            text += entry.name + "  " + entry.id + "\n"
        }
        fragment_back_stack.text = text
    }

    inner class RefreshFragmentStack : TimerTask() {
        override fun run() {
            this@MainActivity.runOnUiThread {
                setFragmentStackData()
            }
        }
    }
}
