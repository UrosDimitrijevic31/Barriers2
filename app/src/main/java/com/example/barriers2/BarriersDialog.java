package com.example.barriers2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

//import org.jetbrains.annotations.NotNull;

public class BarriersDialog extends AppCompatDialogFragment {
    private EditText editName;
    private EditText editIpAddress;
    private EditText editLocation;
    private ExampleDialogListenerBarrier listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_barriers, null);

        builder.setView(view)
                .setTitle("Add new barrier")
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editName.getText().toString();
                        String ipAddress = editIpAddress.getText().toString();
                        String location = editLocation.getText().toString();
                        listener.applyTextsBarrier(name, ipAddress, location);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        editName = view.findViewById(R.id.edit_name_barrier);
        editIpAddress = view.findViewById(R.id.edit_ip_address_barrier);
        editLocation = view.findViewById(R.id.edit_location);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (BarriersDialog.ExampleDialogListenerBarrier) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListenerBarrier");
        }

    }

    public interface ExampleDialogListenerBarrier {
        void applyTextsBarrier(String name, String ipAddress, String location);
    }
}
