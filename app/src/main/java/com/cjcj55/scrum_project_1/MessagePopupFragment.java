package com.cjcj55.scrum_project_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
//USED LIKE THIS: MessagePopupFragment messageDialog = MessagePopupFragment.newInstance("YOUR MESSAGE HERE");
//                 messageDialog.show(getChildFragmentManager(), "MessagePopupFragment");
//Put your own custom message(String) in "YOUR MESSAGE HERE", popup will appear in middle of screen
    public class MessagePopupFragment extends DialogFragment {
        private static final String ARG_MESSAGE = "message";

        private String mMessage;

        public static MessagePopupFragment newInstance(String message) {
            MessagePopupFragment fragment = new MessagePopupFragment();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mMessage = getArguments().getString(ARG_MESSAGE);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.popup_message, container, false);
            TextView messageTextView = view.findViewById(R.id.popuptext);
            messageTextView.setText(mMessage);
            return view;
        }
    }
