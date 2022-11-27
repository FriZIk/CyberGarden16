package com.crafsed.sas.ui.lectures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crafsed.sas.R
import com.crafsed.sas.data.PairDescription
import com.crafsed.sas.databinding.ListFragmentBinding
import com.crafsed.sas.vm.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null
    lateinit var bs: BottomSheetBehavior<LinearLayout>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    val adapter = ListAdapter { onItemClicked() }

    val viewModel: MainViewModel by sharedViewModel()

    private fun onItemClicked() {
//        (requireActivity() as MainActivity).toLecture()

        if (!viewModel.isLector) {
            parentFragmentManager.beginTransaction().replace(R.id.lectureFrame, FragmentLecture())
                .commit()
        } else {
            parentFragmentManager.beginTransaction().replace(R.id.lectureFrame, FragmentLecturePrepod())
                .commit()
        }

        bs.isDraggable = true
        bs.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("", "onCreateView: TEST")
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter

        viewModel.getSchedule()

        viewModel.schedule.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.data = it.pairs
            }
        }

        bs = BottomSheetBehavior.from(binding.bottomSheet)

        if (viewModel.bsExpand){
            openBottomSheet()
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.bsExpand){
            openBottomSheet()
        }
    }

    fun openBottomSheet() {
    }
}