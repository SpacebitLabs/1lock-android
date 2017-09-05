package com.spacebitlabs.onelock.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spacebitlabs.onelock.MockData
import com.spacebitlabs.onelock.PasswordListAdapter.PasswordVH
import com.spacebitlabs.onelock.R
import kotlinx.android.synthetic.main.list_item.view.*

class PasswordListAdapter : RecyclerView.Adapter<PasswordVH>() {
    val DATA = MockData.PASSWORDS

    override fun onBindViewHolder(holder: PasswordVH?, position: Int) {
        holder?.bind(DATA[position])
    }

    override fun getItemCount(): Int {
        return DATA.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PasswordVH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        val passwordVh = PasswordVH(view)
        return passwordVh
    }

    class PasswordVH(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(password: Password) {
            with(password) {
                itemView.name.text = password.name
                itemView.username.text = password.username
            }
        }
    }
}

