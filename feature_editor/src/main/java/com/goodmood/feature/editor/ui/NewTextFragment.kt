package com.goodmood.feature.editor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentNewTextBinding
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.viewmodel.EditorViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.random.Random

class NewTextFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewTextBinding
    private val editorViewModel: EditorViewModel by activityViewModels()
    private var newText = Text(Random.nextLong(), "", "", 0, 0, 0)

    companion object {
        const val KEY_TEXT = "key_text"
        fun newInstance(text: Text? = null): NewTextFragment {
            return if (text == null) {
                NewTextFragment()
            } else {
                NewTextFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(KEY_TEXT, text)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddText.setOnClickListener { addNewText() }
        binding.btnCancel.setOnClickListener { dismiss() }

        binding.sliderTextSize.value = 24f
        binding.tvTextSize.text = String.format(
            "%s (%dpx)",
            getString(R.string.newtext_size),
            binding.sliderTextSize.value.toInt()
        )

        binding.sliderTextSize.addOnChangeListener { _, value, _ ->
            binding.tvTextSize.text =
                String.format("%s (%dpx)", getString(R.string.newtext_size), value.toInt())
        }

        // Set font for new text, we have only one font for this version.
        newText.fontPath = editorViewModel.getFontFile()

        arguments?.let {
            if (it.containsKey(KEY_TEXT)) {
                newText = it.get(KEY_TEXT) as Text
                binding.inputText.setText(newText.text)
                binding.sliderTextSize.value = newText.fontSize.toFloat()
            }
        }
    }

    private fun addNewText() {
        val text = binding.edtInputText.editText?.text.toString() ?: ""
        if (text.isEmpty()) {
            Toast.makeText(context, "Text must not empty", Toast.LENGTH_SHORT).show()
            return
        }

        val fontSize = binding.sliderTextSize.value
        newText.text = text
        newText.fontSize = fontSize.toInt()
        editorViewModel.updateTool(newText)
        dismiss()
    }
}