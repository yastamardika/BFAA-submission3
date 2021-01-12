package com.ystmrdk.sub2_bfaa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class FollowerAdapter(userList: ArrayList<User>) :
    RecyclerView.Adapter<FollowerAdapter.ListViewHolder>() {

    var followerList = ArrayList<User>()

    init {
        followerList = userList
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(250, 250)).into(iv_avatar)
                tv_username.text = user.username
                tv_name.text = user.name
                tv_location.text = user.location
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(followerList[position])
    }

    override fun getItemCount(): Int = followerList.size
}