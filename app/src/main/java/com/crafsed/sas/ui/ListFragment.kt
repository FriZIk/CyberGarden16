package com.crafsed.sas.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crafsed.sas.R
import com.crafsed.sas.data.ListData.Companion.TEST
import com.crafsed.sas.databinding.ListFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null
    private lateinit var bs: BottomSheetBehavior<LinearLayout>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val adapter = ListAdapter{ onItemClicked() }

    private fun onItemClicked() {
//        (requireActivity() as MainActivity).toLecture()

        parentFragmentManager.beginTransaction().replace(R.id.lectureFrame, FragmentLecture()).commit()

        bs.isDraggable = true
        bs.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("", "onCreateView: TEST", )
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
        adapter.data = TEST

        bs = BottomSheetBehavior.from(binding.bottomSheet)
    }
}