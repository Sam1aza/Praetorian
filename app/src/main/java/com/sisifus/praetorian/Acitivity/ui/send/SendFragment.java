package com.sisifus.praetorian.Acitivity.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.sisifus.praetorian.Acitivity.CadastroUsuarioActivity;
import com.sisifus.praetorian.Acitivity.MainActivity;
import com.sisifus.praetorian.Classes.GeneratorPDF;
import com.sisifus.praetorian.R;

public class SendFragment extends Fragment {

    private Button botao;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        botao = (Button) view.findViewById(R.id.botaoGerarPDF);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;

    }
}