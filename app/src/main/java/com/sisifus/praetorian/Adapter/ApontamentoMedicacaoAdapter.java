package com.sisifus.praetorian.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sisifus.praetorian.Classes.Apontamento;
import com.sisifus.praetorian.R;

import java.util.List;

public class ApontamentoMedicacaoAdapter extends RecyclerView.Adapter<ApontamentoMedicacaoAdapter.ViewHolder> {

    private List<Apontamento> mApontamentosList;
    private Context context;

    public ApontamentoMedicacaoAdapter(List<Apontamento> L, Context c){
        context = c;
        mApontamentosList = L;
    }


    @NonNull
    @Override
    public ApontamentoMedicacaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicacaocheck_apontamentos, parent,false);
        return new ApontamentoMedicacaoAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ApontamentoMedicacaoAdapter.ViewHolder holder, int position) {

        Apontamento item = mApontamentosList.get(position);

        holder.txtDate.setText(item.getDataMedicao());
        holder.txtHora.setText(item.getHoraApontamento());
        holder.txtMedicacao.setText(item.getCheckMedicamento());
//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,
//                        "short Click",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(context,
//                        "Long click",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });




    }

    @Override
    public int getItemCount() {
        return  mApontamentosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected TextView txtDate;
        protected TextView txtHora;
        protected TextView txtMedicacao;
        protected LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            txtDate = (TextView) itemView.findViewById(R.id.dataApontamentoView);
            txtHora = (TextView) itemView.findViewById(R.id.horaApontamentoView);
            txtMedicacao = (TextView) itemView.findViewById(R.id.CheckMedicacaoApontamentoView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutM);


        }
    }
}
