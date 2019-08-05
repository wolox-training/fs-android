package ar.com.wolox.android.training.ui.training.main.tabs.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.wolox.android.R
import ar.com.wolox.android.training.model.ProfileItem
import kotlinx.android.synthetic.main.profile_item.view.vDescription

class ProfileListAdapter(
    private val dataSet: MutableList<ProfileItem>,
    private val selectedItem: (ProfileItem) -> Unit
) : RecyclerView.Adapter<ProfileListAdapter.ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false))
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile: ProfileItem = dataSet[position]
        holder.bind(profile, selectedItem)
    }

    override fun getItemCount(): Int = dataSet.size

    fun addData(listOfProfiles: List<ProfileItem>) {
        this.dataSet.addAll(listOfProfiles)
        notifyDataSetChanged()
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            profile: ProfileItem,
            selectedItem: (ProfileItem) -> Unit
        ) {
            itemView.vDescription.text = (profile.nextPage + " | " + profile.prevPage)
        }
    }
}