package br.codehive.projetologin.perfil;

import static br.codehive.projetologin.shared.UtilidadeGerais.hashPassword;
import static br.codehive.projetologin.shared.UtilidadeGerais.verifyPassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import br.codehive.projetologin.model.TypesLogin;
import br.codehive.projetologin.model.ValidationType;
import br.codehive.projetologin.services.UsuarioService;
import br.codehive.projetologin.shared.UtilidadeGerais;

public class PerfilActivity extends AppCompatActivity {

    private PerfilModel model = new PerfilModel();
    private UtilidadeGerais gerais;
    private EditText editNome,editSenha,editEmail;
    private Button btnAlterar,btnDeletar;
    private UsuarioService service;
    private TypesLogin typesLogin;
    private boolean isEditandoSenha = false;

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
        Intent intent = getIntent();
        typesLogin = (TypesLogin) intent.getSerializableExtra("01");

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

        init();

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
                if(typesLogin.equals(TypesLogin.SecurityHash)){
                    if(!password.equalsIgnoreCase(model.getPassword())){
                        model.setPassword(hashPassword(password));
                    }
                }
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
    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        editSenha.setOnTouchListener((view, event) -> {
            final int DRAWABLE_RIGHT = 2;
            if(event.getAction() == MotionEvent.ACTION_UP){
                if(event.getRawX() >= (editSenha.getRight() - editSenha.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                    if (isEditandoSenha) {
                        // Se já está editando, o clique significa "CANCELAR"
                        cancelarEdicaoSenha();
                    } else {
                        // Se não está editando, o clique significa "QUERO EDITAR"
                        mostrarDialogConfirmacaoSenha();
                    }
                    return true;
                }
            }
            return false;
        });
    }
    private void mostrarDialogConfirmacaoSenha() {
        new AlertDialog.Builder(this)
                .setTitle("Alterar Senha")
                .setMessage("Tem certeza que deseja alterar sua senha?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    habilitarEdicaoSenha();
                })
                .setNegativeButton("Não", null)
                .show();
    }
    private void habilitarEdicaoSenha() {
        isEditandoSenha = true;
        editSenha.setEnabled(true);
        editSenha.setFocusableInTouchMode(true); // Permite foco novamente
        editSenha.setText("");
        editSenha.requestFocus();
        // Troca o ícone para "cancelar" via código
        editSenha.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editSenha, InputMethodManager.SHOW_IMPLICIT);
    }
    private void cancelarEdicaoSenha() {
        isEditandoSenha = false;
        editSenha.setText(model.getPassword());
        editSenha.setEnabled(false);
        editSenha.setFocusableInTouchMode(false); // Remove o foco
        // Troca o ícone de volta para "editar"
        editSenha.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit, 0);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editSenha.getWindowToken(), 0);
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