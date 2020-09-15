package com.example.contacts.newcontactscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contacts.R

class NewContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_contact, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = NewContactFragment()
    }
}