package com.crafsed.sas.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.crafsed.sas.R
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CodeDialog : DialogFragment() {
    val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_code, container, false).apply {
            findViewById<Button>(R.id.codeCancelButton).setOnClickListener {
                dialog?.cancel()
            }

            findViewById<Button>(R.id.codeConfirmButton).setOnClickListener {
                findViewById<Button>(R.id.codeConfirmButton).isEnabled = false
                findViewById<TextView>(R.id.textViewCode).apply {
                    visibility = View.VISIBLE
                    setTextColor(resources.getColor(android.R.color.darker_gray))
                    text = "Идёт проверка..."
                }
                viewModel.sendCode(findViewById<EditText>(R.id.codeEditText).text.toString())

                viewModel.codeResponse.observe(viewLifecycleOwner) {
                    when (it) {
                        true -> {
                            findViewById<TextView>(R.id.textViewCode).apply {
                                setTextColor(resources.getColor(android.R.color.holo_green_dark))
                                text = "Код принят"
                            }
                        }
                        false -> {
                            findViewById<TextView>(R.id.textViewCode).apply {
                                setTextColor(resources.getColor(android.R.color.holo_red_dark))
                                text = "Введен неверный код"
                            }
                        }
                    }
                    findViewById<Button>(R.id.codeConfirmButton).isEnabled = true
                }

            }
        }

    }
}