package cn.navigation.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*

private const val TAG = "KSFragmentNavigator"

@Navigator.Name("keep_state_fragment")
class KeepStateNavigator(
    private val mContext: Context,
    private val mFragmentManager: FragmentManager,
    private val mContainerId: Int
) :
    FragmentNavigator(mContext, mFragmentManager, mContainerId) {

    private val mBackStack = ArrayDeque<Int>()


    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring navigate() call: FragmentManager has already"
                        + " saved its state"
            )
            return null
        }
        var className = destination.className
        if (className[0] == '.') {
            className = mContext.packageName + className
        }
        val frag = mFragmentManager.fragmentFactory.instantiate(mContext.classLoader, className)
        frag.arguments = args
        val ft = mFragmentManager.beginTransaction()

        var enterAnim = navOptions?.enterAnim ?: -1
        var exitAnim = navOptions?.exitAnim ?: -1
        var popEnterAnim = navOptions?.popEnterAnim ?: -1
        var popExitAnim = navOptions?.popExitAnim ?: -1
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = if (enterAnim != -1) enterAnim else 0
            exitAnim = if (exitAnim != -1) exitAnim else 0
            popEnterAnim = if (popEnterAnim != -1) popEnterAnim else 0
            popExitAnim = if (popExitAnim != -1) popExitAnim else 0
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        }
        ft.add(mContainerId, frag)
        ft.setPrimaryNavigationFragment(frag)

        @IdRes val destId = destination.id
        val initialNavigation = mBackStack.isEmpty()
        if (!initialNavigation && mBackStack.size < mFragmentManager.fragments.size) {
            ft.hide(mFragmentManager.fragments[mBackStack.size - 1])
        }
        // TODO Build first class singleTop behavior for fragments
        val isSingleTopAdd = (navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId)

        val isAdded = when {
            initialNavigation -> {
                if (mFragmentManager.fragments.isNotEmpty()) {
                    mFragmentManager.fragments.forEach { ft.remove(it) }
                }
                true
            }
            isSingleTopAdd -> {
                // Single Top means we only want one instance on the back stack
                if (mBackStack.size > 1) {
                    mFragmentManager.popBackStack(
                        generateBackStackName(mBackStack.size, mBackStack.peekLast() ?: 0),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    val hideFragment =
                        mFragmentManager.fragments[mFragmentManager.fragments.size - 2]
                    if (hideFragment != null) ft.hide(hideFragment)

                    ft.addToBackStack(generateBackStackName(mBackStack.size, destId))
                } else {
                    ft.remove(mFragmentManager.fragments[0])
                }
                false
            }
            else -> {
                ft.addToBackStack(generateBackStackName(mBackStack.size + 1, destId))
                true
            }
        }

        if (navigatorExtras is Extras) {
            for ((key, value) in navigatorExtras.sharedElements) {
                ft.addSharedElement(key!!, value!!)
            }
        }
        val currentFragment: Fragment? = mFragmentManager.primaryNavigationFragment
        if (isAdded && currentFragment != null) {
            ft.hide(currentFragment)
        }

        ft.setReorderingAllowed(true)
        ft.commit()
        // The commit succeeded, update our view of the world
        // The commit succeeded, update our view of the world
        return if (isAdded) {
            mBackStack.add(destId)
            destination
        } else {
            null
        }
    }

    override fun popBackStack(): Boolean {
        if (mBackStack.isEmpty()) {
            return false
        }
        if (mFragmentManager.isStateSaved) {
            Log.i(
                TAG, "Ignoring popBackStack() call: FragmentManager has already"
                        + " saved its state"
            )
            return false
        }
        mFragmentManager.popBackStack(
            generateBackStackName(mBackStack.size, mBackStack.peekLast() ?: 0),
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        mBackStack.removeLast()
        return true
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}
