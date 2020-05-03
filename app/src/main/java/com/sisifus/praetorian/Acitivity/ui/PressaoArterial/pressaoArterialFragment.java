package com.sisifus.praetorian.Acitivity.ui.PressaoArterial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.sisifus.praetorian.Adapter.ApontamentoPressaoAdapter;
import com.sisifus.praetorian.Classes.Apontamento;
import com.sisifus.praetorian.R;

import java.util.ArrayList;
import java.util.List;

public class pressaoArterialFragment extends Fragment {

    private RecyclerView mRecycleView;
    private ApontamentoPressaoAdapter adapter;
    private List<Apontamento> apontamentosP;
    private DatabaseReference databaseReference;
    private Apontamento apontamento;
    private LinearLayoutManager mLinearLayout;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pressaoarterial, container, false);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleViewPressao);

        popularListaPressao();

        return view;

    }

    private void popularListaPressao(){
        mRecycleView.setHasFixedSize(true);
        mLinearLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(mLinearLayout);

        apontamentosP = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Monitoramento").orderByChild("dataMedicao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apontamentosP.clear();
                for (DataSnapshot apontamentosSnapshot: dataSnapshot.getChildren()){
                    apontamento = apontamentosSnapshot.getValue(Apontamento.class);
                    apontamentosP.add(apontamento);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ApontamentoPressaoAdapter(apontamentosP,getContext());
        mRecycleView.setAdapter(adapter);

    }

}
