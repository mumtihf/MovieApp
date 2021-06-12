package com.mumti.favorite.tv

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mumti.jetpacksubmission.R
import com.mumti.mycore.domain.model.TvShow
import com.mumti.jetpacksubmission.databinding.FragmentFavTvBinding
import com.mumti.jetpacksubmission.detail.DetailFilmActivity
import com.mumti.mycore.ui.TvAdapter
import com.mumti.jetpacksubmission.tv.TvViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavTvFragment : Fragment(), TvAdapter.TvFragmentCallback {

    private var _binding: FragmentFavTvBinding? = null
    private val favFragmentTvBinding get() = _binding!!

    private val viewModel: TvViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavTvBinding.inflate(layoutInflater, container, false)
        return favFragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val tvAdapter = TvAdapter(this)

            tvAdapter.onItemClick = { selectedTvShow ->
                val intent = Intent(activity, DetailFilmActivity::class.java)
                intent.putExtra(DetailFilmActivity.EXTRA_TV, selectedTvShow)
                startActivity(intent)
            }

            viewModel.favoriteTvShow.observe(viewLifecycleOwner, { tv ->
                favFragmentTvBinding.progressBar.visibility = View.VISIBLE
                if (tv != null) {
                    favFragmentTvBinding.progressBar.visibility = View.GONE
                    tvAdapter.setTvShow(tv)
                    tvAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Empty Data", Toast.LENGTH_SHORT).show()
                }
            })

            with(favFragmentTvBinding.rvTvShow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
        }
    }

    override fun onShareClick(tv: TvShow) {
        if (activity != null) {
            val mimeType = "plain/text"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan Tv Show sekarang.")
                .setText(resources.getString(R.string.share_text, tv.title))
                .startChooser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}