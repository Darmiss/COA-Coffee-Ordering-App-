package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cjcj55.scrum_project_1.databinding.RemovetoppinguiBinding;

public class RemoveToppingScreen extends Fragment {
    private RemovetoppinguiBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding =RemovetoppinguiBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //BINDINGS
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

