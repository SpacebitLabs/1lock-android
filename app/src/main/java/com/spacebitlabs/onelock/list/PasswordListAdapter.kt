package com.spacebitlabs.onelock.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spacebitlabs.onelock.MockData
import com.spacebitlabs.onelock.R
import com.spacebitlabs.onelock.data.Password
import com.spacebitlabs.onelock.pwdetail.PwDetailFragment
import kotlinx.android.synthetic.main.list_item.view.*

class PasswordListAdapter : RecyclerView.Adapter<PasswordListAdapter.PasswordVH>() {

    val DATA = MockData.PASSWORDS

    override fun onBindViewHolder(holder: PasswordVH, position: Int) {
        holder.bind(DATA[position])
    }

    override fun getItemCount(): Int {
        return DATA.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val passwordVh = PasswordVH(view)
        return passwordVh
    }

    class PasswordVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(password: Password) {
            itemView.name.text = password.name
            itemView.username.text = password.username

            itemView.setOnClickListener {
                PwDetailFragment.show(itemView)
            }
        }
    }
}

