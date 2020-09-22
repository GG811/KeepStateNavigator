# KeepStateNavigator
modify navigate fragment replace to add

## Usage

```
val navController = findNavController(R.id.fragment)
val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)!!
val navigator = KeepStateNavigator(this, navHostFragment.childFragmentManager, R.id.fragment)
navController.navigatorProvider.addNavigator(navigator)
navController.setGraph(R.navigation.navigation)
```

## Reference
- [navigation-keep-fragment-sample](https://github.com/STAR-ZERO/navigation-keep-fragment-sample)
- [BlogTest](https://github.com/CherryLover/BlogTest/blob/closeBefore/app/src/main/java/me/monster/blogtest/MainActivity.java)






