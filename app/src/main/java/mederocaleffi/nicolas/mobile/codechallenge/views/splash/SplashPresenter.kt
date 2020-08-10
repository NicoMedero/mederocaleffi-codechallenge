package mederocaleffi.nicolas.mobile.codechallenge.views.splash

import android.os.Handler

class SplashPresenter(private val view: SplashView) {

    private val splashTime = 2000L

    /**
     * In this method we can make a call to the API
     * to load SearchView with some data.
     *
     * For now, it delays the app start to show the splash view.
     */
    fun onViewCreated() {
        val handler = Handler()
        handler.postDelayed(
            { view.onSplashEnded() },
            splashTime
        )
    }
}