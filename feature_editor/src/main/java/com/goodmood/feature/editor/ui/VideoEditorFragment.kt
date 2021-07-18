package com.goodmood.feature.editor.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentVideoEditorBinding
import com.goodmood.core.ui.base.BaseFragment
import com.goodmood.feature.editor.viewmodel.EditorViewModel
import com.goodmood.feature.editor.viewmodel.ExportResult
import com.goodmood.platform.log.AppLog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

@AndroidEntryPoint
class VideoEditorFragment : BaseFragment() {

    private lateinit var binding: FragmentVideoEditorBinding
    private val args: VideoEditorFragmentArgs by navArgs()
    private val editorViewModel by activityViewModels<EditorViewModel>()
    private val exportSnackBar: Snackbar? by lazy {
        view?.let { Snackbar.make(it, "", Snackbar.LENGTH_INDEFINITE) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            editorViewModel.cancelExport()
            exportSnackBar?.dismiss()
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editorViewModel.inputVideoPath = args.videoPath
        childFragmentManager.apply {
            beginTransaction().replace(R.id.previewContainer, PreviewFragment()).commit()
            beginTransaction().replace(R.id.editContainer, EditFragment()).commit()
        }

        observeExportResult()
    }

    override fun onDestroyView() {
        editorViewModel.clearEditor()
        super.onDestroyView()
    }

    private fun observeExportResult() {
        disposable.add(
            editorViewModel.exportResult
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    showExportResult(it)
                }, {
                    AppLog.e("Observe export result failed -> $it.message")
                })
        )
    }

    private fun showExportResult(result: ExportResult) {
        exportSnackBar?.let { snackBar ->
            if (!snackBar.isShown) snackBar.show()
            when (result) {
                is ExportResult.Success -> {
                    // Save edited video to local database for display in video list fragment
                    disposable.add(
                        editorViewModel.saveExportedVideo(result.output)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorComplete()
                            .subscribe {
                                snackBar
                                    .setText("Done!")
                                    .setAction("Dismiss") {
                                        snackBar.dismiss()
                                        findNavController().popBackStack()
                                    }
                            }
                    )
                }

                is ExportResult.Failed -> {
                    snackBar
                        .setText(result.reason)
                        .setAction("Dismiss") { snackBar.dismiss() }
                }

                is ExportResult.InProgress -> snackBar.setText(result.progress)
            }
        }
    }
}