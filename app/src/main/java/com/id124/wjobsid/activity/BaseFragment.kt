package com.id124.wjobsid.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.id124.wjobsid.util.SharedPreference

abstract class BaseFragment<FragmentBinding : ViewDataBinding> : Fragment() {
    protected lateinit var bind: FragmentBinding
    protected lateinit var sharedPref: SharedPreference
    protected lateinit var userDetail: HashMap<String, String>
    protected var setLayout: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bind = DataBindingUtil.inflate(inflater, setLayout!!, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)
    }
}