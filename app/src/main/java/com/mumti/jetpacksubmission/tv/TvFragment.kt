package com.mumti.jetpacksubmission.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mumti.jetpacksubmission.R
import com.mumti.mycore.domain.model.TvShow
import com.mumti.jetpacksubmission.databinding.FragmentTvBinding
import com.mumti.jetpacksubmission.detail.DetailFilmActivity
import com.mumti.mycore.data.Resource
import com.mumti.mycore.ui.TvAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class TvFragment : Fragment(), TvAdapter.TvFragmentCallback {

    private var _binding: FragmentTvBinding? = null
    private val fragmentTvBinding get() = _binding!!

    private val viewModel: TvViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val adapter = TvAdapter(this)

            adapter.onItemClick = { selectedTvShow ->
                val intent = Intent(activity, DetailFilmActivity::class.java)
                intent.putExtra(DetailFilmActivity.EXTRA_TV, selectedTvShow)
                startActivity(intent)
            }

            viewModel.tvShow.observe(viewLifecycleOwner, { tv ->
                if (tv != null) {
                    when (tv) {
                        is Resource.Loading -> fragmentTvBinding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            fragmentTvBinding.progressBar.visibility = View.GONE
                            adapter.setTvShow(tv.data)
                            adapter.notifyDataSetChanged()
                        }
                        is Resource.Error -> {
                            fragmentTvBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentTvBinding.rvTvShow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                this.adapter = adapter
            }
        }
    }

    override fun onShareClick(tv: TvShow) {
        if (activity != null) {
            val mimeType = "text/plain"
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