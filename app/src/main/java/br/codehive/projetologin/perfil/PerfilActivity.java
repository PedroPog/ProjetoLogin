package br.codehive.projetologin.perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.codehive.projetologin.InterfaceActivity;
import br.codehive.projetologin.R;
import br.codehive.projetologin.login.LoginNoSecurityActivity;
import br.codehive.projetologin.model.PerfilModel;
import br.codehive.projetologin.model.ValidationType;
import br.codehive.projetologin.services.UsuarioService;
import br.codehive.projetologin.shared.UtilidadeGerais;

public class PerfilActivity extends AppCompatActivity {

    private PerfilModel model = new PerfilModel();
    private UtilidadeGerais gerais;
    private EditText editNome,editSenha,editEmail;
    private Button btnAlterar,btnDeletar;
    private UsuarioService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNome = findViewById(R.id.edit_nome_perfil);
        editEmail = findViewById(R.id.edit_email_perfil);
        editSenha = findViewById(R.id.edit_senha_perfil);
        btnAlterar = findViewById(R.id.btn_alterar_perfil);
        btnDeletar = findViewById(R.id.btn_deletar_perfil);

        gerais = new UtilidadeGerais(this);
        service = new UsuarioService(this);
        model = gerais.getPerfil();
        onBackNow();

        editNome.setText(model.getName());
        editEmail.setText(model.getEmail());
        editSenha.setText(model.getPassword());

        btnDeletar.setOnClickListener(v->{
            boolean rt = service.deleteUsuario(model.getId());
            if(!rt){
                Toast.makeText(v.getContext(),"Falha na tentantiva de apagar usuario!",Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(v.getContext(),"Sucesso na tentantiva de apagar usuario!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(v.getContext(), InterfaceActivity.class));
            finish();
        });
        btnAlterar.setOnClickListener(v->{
            boolean isEmailValid = gerais.valid(editEmail, ValidationType.EMAIL);
            boolean isPasswordValid = gerais.valid(editSenha, ValidationType.PASSWORD);
            boolean isNameValid = gerais.valid(editNome, ValidationType.GENERIC_TEXT);
            if(isEmailValid & isPasswordValid & isNameValid){
                String email = editEmail.getText().toString();
                String password = editSenha.getText().toString();
                String nome = editNome.getText().toString();

                PerfilModel model = new PerfilModel();
                model.setId(this.model.getId());
                model.setEmail(email);
                model.setPassword(password);
                model.setName(nome);
                model.setImagemPerfil("");

                boolean rt = service.updateUsuario(model);
                if(!rt){
                    Log.e("UpdatePerfilFail","Falha ao update perfil!");
                    Toast.makeText(v.getContext(), "Falha no update perfil!", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(v.getContext(), "Update Válido!\nEmail: " + email, Toast.LENGTH_LONG).show();
                gerais.salvarPerfil(model);
            }
        });

    }

    private void onBackNow(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mostrarDialogExit();
            }
        };
        getOnBackPressedDispatcher().addCallback(this,callback);
    }

    private void mostrarDialogExit() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Saida")
                .setMessage("Tem certeza que deseja sair do aplicativo?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNeutralButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}