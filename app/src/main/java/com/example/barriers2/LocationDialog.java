package com.example.barriers2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
//import androidx.room.jarjarred.org.antlr.v4.runtime.misc.NotNull;

//import org.jetbrains.annotations.NotNull;

public class LocationDialog extends AppCompatDialogFragment {
    private EditText editName;
    private EditText editIpAddress;
    private EditText editLocationDescription;
    private ExampleDialogListener listener;

    @NonNull
//    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dailog_location, null);

        editName = view.findViewById(R.id.edit_name);
        editIpAddress = view.findViewById(R.id.edit_ip_address);
        editLocationDescription = view.findViewById(R.id.location_description);

        builder.setView(view)
                .setTitle("Add new location")
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = editName.getText().toString();
                        if(TextUtils.isEmpty(name)) {
                            //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                            Toast.makeText(getContext(), "Enter location name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String ipAddress = editIpAddress.getText().toString();
                        if(TextUtils.isEmpty(ipAddress)) {
                            //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                            Toast.makeText(getContext(), "Enter IP address", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String description = editLocationDescription.getText().toString();
                        if(TextUtils.isEmpty(description)) {
                            //TREBA DA NE IZLAZI IZ DIALOGA A DA PRIKAZE PORUKU SREDITI ! ! ! !
                            Toast.makeText(getContext(), "Enter location name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //here we need to call view model


                        listener.applyTexts(name, ipAddress, description);

                        dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }

    }

    public interface ExampleDialogListener {
        void applyTexts(String name, String ipAddress, String description);
    }
}
