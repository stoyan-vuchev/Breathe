package choehaualen.breath

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltBreathApp : BreathApp()

open class BreathApp : Application()