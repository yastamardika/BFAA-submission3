package com.ystmrdk.consumerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ystmrdk.consumerapp.model.User
import kotlinx.android.synthetic.main.item_user.view.*


class UserAdapter(usersData: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    lateinit var context: Context
    var list = ArrayList<User>()

    init {
        list = usersData
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

//                setOnClickListener {
//                    val detailIntent = Intent(context, DetailActivity::class.java)
//                    detailIntent.putExtra(DetailActivity.EXTRA_DATA, user)
//                    context.startActivity(detailIntent)
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(userData: User)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}