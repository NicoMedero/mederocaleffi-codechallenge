package mederocaleffi.nicolas.mobile.codechallenge.views.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mederocaleffi.nicolas.mobile.codechallenge.R
import mederocaleffi.nicolas.mobile.codechallenge.views.search.SearchActivity

/**
 * This splash fragment is just a view that shows one time
 * when the app starts.
 *
 * It could be used to load some data before send the user
 * to the search view, but right now it just shows the logo.
 */
class SplashFragment : Fragment(), SplashView {

    private lateinit var presenter: SplashPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = SplashPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.splash_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun onSplashEnded() {
        val intent = Intent(requireContext(), SearchActivity::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        fun newInstance() = SplashFragment()
    }
}