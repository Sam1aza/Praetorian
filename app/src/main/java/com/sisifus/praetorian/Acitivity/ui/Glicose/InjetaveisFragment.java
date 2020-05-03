package com.sisifus.praetorian.Acitivity.ui.Glicose;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sisifus.praetorian.Adapter.ApontamentoInsulinaAdapter;
import com.sisifus.praetorian.Classes.Apontamento;
import com.sisifus.praetorian.R;

import java.util.ArrayList;
import java.util.List;

public class InjetaveisFragment extends Fragment {

    private RecyclerView mRecycleView;
    private ApontamentoInsulinaAdapter adapter;
    private List<Apontamento> apontamentos;
    private DatabaseReference databaseReference;
    private Apontamento apontamento;
    private LinearLayoutManager mLinearLayout;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_injetaveis, container, false);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleViewInsulina);

        popularListaInjetaveis();

        return view;

    }

    private void popularListaInjetaveis(){
        mRecycleView.setHasFixedSize(true);
        mLinearLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(mLinearLayout);

        apontamentos = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = databaseReference
                .child("Monitoramento")
                .orderByChild("dataMedicao")
                .limitToLast(10)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apontamentos.clear();
                for (DataSnapshot apontamentosSnapshot : dataSnapshot.getChildren()) {
                    apontamento = apontamentosSnapshot.getValue(Apontamento.class);
                    apontamentos.add(apontamento);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ApontamentoInsulinaAdapter(apontamentos,getContext());
        mRecycleView.setAdapter(adapter);

    }



}
