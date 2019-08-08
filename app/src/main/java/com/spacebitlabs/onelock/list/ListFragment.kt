package com.spacebitlabs.onelock.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.spacebitlabs.onelock.R
import com.spacebitlabs.onelock.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action") {
                    val intent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
                    intent.data = Uri.parse("package:${activity?.packageName}")
                    startActivity(intent)
                }.show()
        }

        val viewModel = ViewModelFactory.create(this, ListViewModel::class.java)

        list.layoutManager = LinearLayoutManager(context)
        val adapter = LoginListAdapter()
        list.adapter = adapter

        adapter.loginList = viewModel.getAllLogins()
    }

}

