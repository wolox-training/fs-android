package ar.com.wolox.android.trainingFS.ui.training.main.tabs.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.trainingFS.model.youtube.YoutubeItem
import kotlinx.android.synthetic.main.profile_item.view.*

class ProfileListAdapter(
    private val dataSet: MutableList<YoutubeItem>,
    private val selectedItem: (YoutubeItem) -> Unit
) : RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile: YoutubeItem = dataSet[position]
        holder.bind(profile, selectedItem)
    }

    override fun getItemCount(): Int = dataSet.size

    fun addData(listOfProfiles: List<YoutubeItem>) {
        this.dataSet.addAll(listOfProfiles)
        notifyDataSetChanged()
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            profile: YoutubeItem,
            selectedItem: (YoutubeItem) -> Unit
        ) {
            itemView.vTitle.text = profile.title
            itemView.vDescription.text = profile.description

            itemView.vYoutubeImgPreview.setImageURI(profile.highUrlImg)
            itemView.vYoutubeImgPreview.setOnClickListener { selectedItem(profile) }

            itemView.vPlayBtn.setOnClickListener { selectedItem(profile) }
        }
    }
}