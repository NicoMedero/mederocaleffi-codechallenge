package utn.frba.mobile.codechallenge.views.splash

import android.os.Handler

class SplashPresenter(private val view: SplashView) {

    private val splashTime = 2000L

    fun onViewCreated() {
        val handler = Handler()
        handler.postDelayed(
            { view.onSplashEnded() },
            splashTime
        )
    }
}