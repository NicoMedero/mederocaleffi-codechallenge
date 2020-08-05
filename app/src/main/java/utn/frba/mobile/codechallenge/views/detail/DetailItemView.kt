package utn.frba.mobile.codechallenge.views.detail

interface DetailItemView {
    fun getIdIfItemWasLiked(): Int?

    fun setLikeStatus()

    fun stopProgressBar()
}