package com.sisifus.praetorian.Acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sisifus.praetorian.Classes.Apontamento;
import com.sisifus.praetorian.Classes.ApontamentoMedicacao;
import com.sisifus.praetorian.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ApontamentoActivity extends AppCompatActivity {

    private TextView dataatual;
    private TextView horaAtual;
    private TextView UsuarioApont;
    private TextView checkMedicaoView;
    private EditText insulina;
    private EditText pressaoDis;
    private EditText batimento;
    private EditText observacao;
    private Button ApontarMedic;


    private Button salvarApont;
    private Button cancelarApont;
    private Button medicacaoApont;

    private BootstrapButton btnCancelarAlert;
    private BootstrapButton btnGravarMedic;
    private CheckBox CarbamazepinaM;
    private CheckBox RisperidonaM;
    private CheckBox BrilintaM;
    private CheckBox LosrtanaM;
    private CheckBox ConcorM;
    private CheckBox ZimpassM;


    private BootstrapButton btnCancelarAlertT;
    private BootstrapButton btnGravarMedicT;
    private CheckBox StranglitT;
    private CheckBox AAS;


    private BootstrapButton btnCancelarAlertN;
    private BootstrapButton btnGravarMedicN;
    private CheckBox CarbamazepinaN;
    private CheckBox RisperidonaN;
    private CheckBox BrilintaN;
    private CheckBox LosrtanaN ;

    private BootstrapButton btnCancelarAlertall;
    private BootstrapButton btnGravarMedicall;
    private CheckBox Carbamazepinaall;
    private CheckBox Risperidonaall;
    private CheckBox Brilintaall;
    private CheckBox Losrtanaall;
    private CheckBox Concorall;
    private CheckBox Zimpassall;
    private CheckBox Stranglitall;
    private CheckBox AASall;

    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference myRef;

    private Dialog dialog;

    private Apontamento apontamento;
    private ApontamentoMedicacao apontamentoMedicacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apontamento);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //usuario

        checkMedicaoView = (TextView) findViewById(R.id.checkMedicacaoTV);

        UsuarioApont = (TextView) findViewById(R.id.txtUsuario);


        if (user != null) {
            // Name, email address, and profile photo Url
            String email = user.getEmail();
            UsuarioApont.setText(email);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        /////////////////////////////////////////////////////////////////////////////////////////////



        observacao = (EditText) findViewById(R.id.edtobservação);

        medicacaoApont = (Button) findViewById(R.id.btnsalvarMedicacao);

        dataatual = (TextView) findViewById(R.id.adataApont);
        horaAtual = (TextView) findViewById(R.id.aHoraApont);
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");

        Date data1 = new Date();
        Date data2 = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data2);
        Date hora_atual = cal.getTime();

        String dataFormatada = formataData.format(data1);
        String horaFormatada = formatHora.format(hora_atual);

        dataatual.setText(dataFormatada);
        horaAtual.setText(horaFormatada);

        horaAtual.getText().toString();

        ApontarMedic = (Button) findViewById(R.id.btnsalvarMedicacao);

        ApontarMedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                abrirDialogmedicacaotodos();
                try {
                    selecionadorDeDialog();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////

        insulina = (EditText) findViewById(R.id.edtinsulinaid);
        SimpleMaskFormatter simpleMaskInsulina = new SimpleMaskFormatter("NNN");
        MaskTextWatcher maskInsulina = new MaskTextWatcher(insulina, simpleMaskInsulina);
        insulina.addTextChangedListener(maskInsulina);

        pressaoDis = (EditText) findViewById(R.id.edtpressao1id);
        SimpleMaskFormatter simpleMaskPressao1 = new SimpleMaskFormatter("NN/N");
        MaskTextWatcher maskPressao1 = new MaskTextWatcher(pressaoDis, simpleMaskPressao1);
        pressaoDis.addTextChangedListener(maskPressao1);

        batimento = (EditText) findViewById(R.id.edtbatimento);
        SimpleMaskFormatter simpleMaskBatimento = new SimpleMaskFormatter("NNN");
        MaskTextWatcher maskBatimento = new MaskTextWatcher(batimento, simpleMaskBatimento);
        batimento.addTextChangedListener(maskBatimento);

        salvarApont = (Button) findViewById(R.id.btnsalcarapont);
        cancelarApont = (Button) findViewById(R.id.btncacnelarApont);

        insulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        salvarApont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (insulina.getText().toString().equals("")) {
                    Toast.makeText(ApontamentoActivity.this, "Preencha as informações de glicose", Toast.LENGTH_LONG).show();
                } else if (pressaoDis.getText().toString().equals("")) {
                    Toast.makeText(ApontamentoActivity.this, "Preencha as informações de pressão arterial", Toast.LENGTH_LONG).show();
                } else if (batimento.getText().toString().equals("")) {
                    Toast.makeText(ApontamentoActivity.this, "Preencha as informações do batimento cardíaco", Toast.LENGTH_LONG).show();
                } else if (checkMedicaoView.getText().toString().equals("")) {
                    Toast.makeText(ApontamentoActivity.this, "Preencha as informações do medicamento", Toast.LENGTH_LONG).show();
                } else {
                    apontamento = new Apontamento();
                    apontamento.setDataMedicao(dataatual.getText().toString());
                    apontamento.setGlicemia(insulina.getText().toString());
                    apontamento.setPressaoArterial(pressaoDis.getText().toString());
                    apontamento.setBatimento(batimento.getText().toString());
                    apontamento.setHoraApontamento(horaAtual.getText().toString());
                    apontamento.setUsuarioApontamento(UsuarioApont.getText().toString());
                    apontamento.setObservacao(observacao.getText().toString());
                    apontamento.setCheckMedicamento(checkMedicaoView.getText().toString());
                    inserirApontamentoDatabase(apontamento);
                }
            }
        });

        cancelarApont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApontamentoActivity.this, PainelInicial.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void inserirApontamentoDatabase(Apontamento apontamento) {
        myRef = database.getReference("Monitoramento");
        String key = myRef.child("apontamento").push().getKey();
        apontamento.setKeyApontamento(key);
        myRef.child(key).setValue(apontamento);
        Toast.makeText(ApontamentoActivity.this,
                "Medições e medicações feitas", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ApontamentoActivity.this, PainelInicial.class);
        startActivity(intent);
        finish();
    }

    private void inserirApontamentoMedicacaoDatabase(ApontamentoMedicacao apontamentoMedicacao) {
        myRef = database.getReference("Monitoramento");
        String key = myRef.child("apontamento").push().getKey();
        apontamento.setKeyApontamento(key);
        myRef.child(key).setValue(apontamentoMedicacao);
        Toast.makeText(ApontamentoActivity.this,
                "Medicamentos ok", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ApontamentoActivity.this, PainelInicial.class);
        startActivity(intent);
        finish();
    }

    private void selecionadorDeDialog() throws ParseException {

        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date hora_atual = cal.getTime();


        Date dataManha1 = formatHora.parse("06:00");
        Date dataTarde1 = formatHora.parse("12:01");
        Date dataNoite1 = formatHora.parse("17:01");

        if (hora_atual.after(dataNoite1)){
            abrirDialogManha();
        } else if (hora_atual.after(dataTarde1)) {
            abrirDialogmedicacaotodos();
        } else if (hora_atual.after(dataManha1)) {
            abrirDialogmedicacaotodos();
        } else {
            Toast.makeText(ApontamentoActivity.this,
                    "Erro na seleção de periodo",
                    Toast.LENGTH_LONG).show();
        }
    }


    private void abrirDialogManha() {
        dialog = new Dialog(ApontamentoActivity.this);
        dialog.setContentView(R.layout.medicamento_apont_manha);

        btnCancelarAlert = (BootstrapButton) dialog.findViewById(R.id.btncancelarrecup);
        btnGravarMedic = (BootstrapButton) dialog.findViewById(R.id.btngravarmedM);

        CarbamazepinaM = (CheckBox) dialog.findViewById(R.id.chkcarbamamnh);
        RisperidonaM = (CheckBox) dialog.findViewById(R.id.chkrisperidonamhn);
        BrilintaM = (CheckBox) dialog.findViewById(R.id.chkBrilintamhn);
        LosrtanaM = (CheckBox) dialog.findViewById(R.id.chkBLosartanamhn);
        ConcorM = (CheckBox) dialog.findViewById(R.id.chkConcormhn);
        ZimpassM = (CheckBox) dialog.findViewById(R.id.chkZimpasmhn);


        btnGravarMedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    checkMedicaoView.setText("Medicação confirmada!");
                    Toast.makeText(ApontamentoActivity.this,
                            "Todas as medicações foram dadas!",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }
        });


        btnCancelarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void abrirDialogTarde() {
        dialog = new Dialog(ApontamentoActivity.this);
        dialog.setContentView(R.layout.medicamento_apont_tarde);

        btnCancelarAlertT = (BootstrapButton) dialog.findViewById(R.id.btncancelarrecupT);
        btnGravarMedicT = (BootstrapButton) dialog.findViewById(R.id.btngravarmedT);

        StranglitT = (CheckBox) dialog.findViewById(R.id.checkStranglitT);
        AAS = (CheckBox) dialog.findViewById(R.id.chkAAST);

        btnGravarMedicT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StranglitT.isChecked() &&
                        AAS.isChecked()) {
                    checkMedicaoView.setText("Medicação confirmada!");
                    Toast.makeText(ApontamentoActivity.this,
                            "Todas as medicações foram dadas!",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ApontamentoActivity.this,
                            "Todas as medicações não foram dadas!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCancelarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void abrirDialogNoite() {
        dialog = new Dialog(ApontamentoActivity.this);
        dialog.setContentView(R.layout.medicamento_apont_noite);

        btnCancelarAlert = (BootstrapButton) dialog.findViewById(R.id.btncancelarrecupn);
        btnGravarMedic = (BootstrapButton) dialog.findViewById(R.id.btngravarmedn);

        CarbamazepinaM = (CheckBox) dialog.findViewById(R.id.chkcarbamanoi);
        RisperidonaM = (CheckBox) dialog.findViewById(R.id.chkrisperidonanoi);
        BrilintaM = (CheckBox) dialog.findViewById(R.id.chkBrilintanoi);
        LosrtanaM = (CheckBox) dialog.findViewById(R.id.chkBLosartananoi);


        btnGravarMedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CarbamazepinaM.isChecked() &&
                        RisperidonaM.isChecked() &&
                        BrilintaM.isChecked() &&
                        LosrtanaM.isChecked()) {
                    checkMedicaoView.setText("Medicação confirmada!");
                    Toast.makeText(ApontamentoActivity.this,
                            "Todas as medicações foram dadas!",
                            Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ApontamentoActivity.this,
                            "Todas as medicações não foram dadas!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCancelarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

private void abrirDialogmedicacaotodos(){
        dialog = new Dialog(ApontamentoActivity.this);
        dialog.setContentView(R.layout.medicamento_apont_noite);




}






}



