package utn.frba.mobile.codechallenge.views.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import utn.frba.mobile.codechallenge.R
import utn.frba.mobile.codechallenge.views.search.SearchActivity

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