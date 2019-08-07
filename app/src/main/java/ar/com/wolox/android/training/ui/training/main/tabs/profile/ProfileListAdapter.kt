package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.youtube.YoutubeListItem
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import kotlinx.android.synthetic.main.profile_item.view.*

class ProfileListAdapter(
    private val dataSet: MutableList<YoutubeListItem>,
    private val selectedItem: (YoutubeListItem) -> Unit
) : RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile: YoutubeListItem = dataSet[position]
        holder.bind(profile, selectedItem)
    }

    override fun getItemCount(): Int = dataSet.size

    fun addData(listOfProfiles: List<YoutubeListItem>) {
        this.dataSet.addAll(listOfProfiles)
        notifyDataSetChanged()
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            profile: YoutubeListItem,
            selectedItem: (YoutubeListItem) -> Unit
        ) {
            itemView.vTitle.text = profile.title
            itemView.vDescription.text = profile.description
            itemView.vYoutubePreview.initialize(profile.id, object : YouTubeThumbnailView.OnInitializedListener {
                override fun onInitializationSuccess(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader?) {
                    p1?.setVideo(profile.id)
                    p1?.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                        override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p1: String?) {
                            p0?.visibility = View.VISIBLE
                        }

                        override fun onThumbnailError(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader.ErrorReason?) {
                            println("onThumbnailError: " + p1?.name)
                        }
                    })
                }

                override fun onInitializationFailure(p0: YouTubeThumbnailView?, p1: YouTubeInitializationResult?) {
                    println("onInitializationFailure: " + p1?.name)
                }
            })

            itemView.vYoutubePreview.setOnClickListener { selectedItem(profile) }
            itemView.vPlayBtn.setOnClickListener { selectedItem(profile) }
        }
    }
}