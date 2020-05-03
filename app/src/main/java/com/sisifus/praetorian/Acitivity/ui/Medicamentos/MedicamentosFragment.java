package com.sisifus.praetorian.Acitivity.ui.Medicamentos;

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
import com.sisifus.praetorian.Adapter.ApontamentoMedicacaoAdapter;
import com.sisifus.praetorian.Adapter.ApontamentoPressaoAdapter;
import com.sisifus.praetorian.Classes.Apontamento;
import com.sisifus.praetorian.R;

import java.util.ArrayList;
import java.util.List;

public class MedicamentosFragment extends Fragment {

    private RecyclerView mRecycleView;
    private ApontamentoMedicacaoAdapter adapter;
    private List<Apontamento> apontamentosM;
    private DatabaseReference databaseReference;
    private Apontamento apontamento;
    private LinearLayoutManager mLinearLayout;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicamentos, container, false);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleViewMedicacao);

        popularListaMedicacao();

        return view;

    }

    private void popularListaMedicacao(){
        mRecycleView.setHasFixedSize(true);
        mLinearLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(mLinearLayout);

        apontamentosM = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Monitoramento").orderByChild("dataMedicao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apontamentosM.clear();
                for (DataSnapshot apontamentosSnapshot: dataSnapshot.getChildren()){
                    apontamento = apontamentosSnapshot.getValue(Apontamento.class);
                    apontamentosM.add(apontamento);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ApontamentoMedicacaoAdapter(apontamentosM,getContext());
        mRecycleView.setAdapter(adapter);

    }

}
