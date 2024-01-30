package dev.keikem.catzapp.screens.activity

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.keikem.catzapp.R

/* Единственная активити в приложений
* Что это нам дает:
* 1) Удобство работы с несколькими экранами, не будет путаницы в жизненом цикле
* 2) Можно делать красивые переходы между экранами, несколько активностей такого не позволяют
*/

@AndroidEntryPoint
class RootActivity : AppCompatActivity(R.layout.activity_root)