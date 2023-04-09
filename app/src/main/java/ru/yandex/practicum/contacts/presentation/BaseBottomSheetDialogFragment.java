package ru.yandex.practicum.contacts.presentation.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.yandex.practicum.contacts.databinding.FragmentBottomSheetBinding;

public abstract class BaseBottomSheetDialogFragment<T extends BaseBottomSheetViewModel> extends BottomSheetDialogFragment {

    private final Class<T> viewModelClass;

    protected FragmentBottomSheetBinding binding;
    protected T viewModel;

    public BaseBottomSheetDialogFragment(Class<T> viewModelClass) {
        this.viewModelClass = viewModelClass;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            final View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(viewModelClass);
        binding.applyButton.setOnClickListener(v -> viewModel.onApplyClick());
        binding.resetButton.setOnClickListener(v -> viewModel.onResetClick());
    }

    @Override
    public void onDestroy() {
        binding = null;
        super.onDestroy();
    }
}
