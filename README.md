# KeepStateNavigator
Modify navigate fragment replace to add

Try to keep the logic consistent with FragmentNavigator



> sample: HostFragment->A->B-(popUpTo HostFragment,inclusive:false)->C

![image](https://github.com/GG811/KeepStateNavigator/blob/master/image/popUpto.gif?raw=true)




## Usage

1. Add KeepStateNavigator.kt to your project like this
```
val navController = findNavController(R.id.fragment)
val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)!!
val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.fragment)
navController.navigatorProvider.addNavigator(navigator)
navController.setGraph(R.navigation.navigation)
```
2. Modity navigation.xml fragment to keep_state_fragment

## Reference
- [navigation-keep-fragment-sample](https://github.com/STAR-ZERO/navigation-keep-fragment-sample)
- [BlogTest](https://github.com/CherryLover/BlogTest/blob/closeBefore/app/src/main/java/me/monster/blogtest/MainActivity.java)