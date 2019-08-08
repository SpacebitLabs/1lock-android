package com.spacebitlabs.onelock.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spacebitlabs.onelock.R
import com.spacebitlabs.onelock.data.LoginData
import com.spacebitlabs.onelock.pwdetail.PwDetailFragment
import kotlinx.android.synthetic.main.list_item.view.*

class LoginListAdapter : RecyclerView.Adapter<LoginListAdapter.LoginVH>() {

    var loginList: List<LoginData> = ArrayList()

    override fun onBindViewHolder(holder: LoginVH, position: Int) {
        holder.bind(loginList[position])
    }

    override fun getItemCount(): Int {
        return loginList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val loginVH = LoginVH(view)
        return loginVH
    }

    class LoginVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(loginData: LoginData) {
            itemView.name.text = loginData.name
            itemView.username.text = loginData.username

            itemView.setOnClickListener {
                PwDetailFragment.show(itemView)
            }
        }
    }
}

