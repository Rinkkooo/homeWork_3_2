package com.example.homework_3_2;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.activity.result.contract.ActivityResultContracts;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainFragment extends Fragment {

    EditText etEnterGmail, etTheme, etMessageSend;
    Button sendBtn;

    private ActivityResultLauncher<Intent> gmailLauncher;
    //public static final int OPEN_GMAIL = 228;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etEnterGmail = view.findViewById(R.id.et_enter_gmail);
        etTheme = view.findViewById(R.id.et_theme);
        etMessageSend = view.findViewById(R.id.et_message_for_send);
        sendBtn = view.findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGmail();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gmailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK){
                        Toast.makeText(requireContext(), "Письмо успешно отправлено", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void openGmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/*");

        String newGmail = etEnterGmail.getText().toString();
        String theme = etTheme.getText().toString();
        String messageForSend = etMessageSend.getText().toString();

        if (!newGmail.isEmpty() && !messageForSend.isEmpty()) {
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{newGmail});
            intent.putExtra(Intent.EXTRA_SUBJECT, theme);
            intent.putExtra(Intent.EXTRA_TEXT, messageForSend);
            gmailLauncher.launch(intent);
        } else {
            Toast.makeText(requireContext(), "Введите адрес получателя и сообщение", Toast.LENGTH_SHORT).show();
        }

    }

}