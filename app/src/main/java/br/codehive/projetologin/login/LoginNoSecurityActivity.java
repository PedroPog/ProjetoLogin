package br.codehive.projetologin.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import br.codehive.projetologin.R;
import br.codehive.projetologin.model.PerfilModel;
import br.codehive.projetologin.model.ValidationType;
import br.codehive.projetologin.perfil.PerfilActivity;
import br.codehive.projetologin.services.UsuarioService;
import br.codehive.projetologin.shared.UtilidadeGerais;

public class LoginNoSecurityActivity extends AppCompatActivity {

    private TextInputLayout editEmail,editPassword;
    private Button btnEnviar;
    private UtilidadeGerais gerais;
    private UsuarioService service;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gerais = new UtilidadeGerais(this);
        service = new UsuarioService(this);
        init();
    }

    private void init(){
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnEnviar = findViewById(R.id.btn_enviar);

        editEmail.getEditText().setText("pedro@gmail.com");
        editPassword.getEditText().setText("123456");

        btnEnviar.setOnClickListener(v->{
            boolean isEmailValid = gerais.valid(editEmail, ValidationType.EMAIL);
            boolean isPasswordValid = gerais.valid(editPassword, ValidationType.PASSWORD);
            if (isEmailValid && isPasswordValid) {
                String email = editEmail.getEditText().getText().toString();
                String password = editPassword.getEditText().getText().toString();

                PerfilModel modelParaLogar = new PerfilModel();
                modelParaLogar.setEmail(email);
                modelParaLogar.setPassword(password);

                PerfilModel usuarioLogado = service.inlogin(modelParaLogar);

                if (usuarioLogado == null) {
                    Log.e("LoginFail","Falha ao login!");
                    Toast.makeText(v.getContext(), "Email ou senha inválidos!", Toast.LENGTH_LONG).show();
                    return;
                }
                gerais.salvarPerfil(usuarioLogado);

                Toast.makeText(v.getContext(), "Login Válido! Bem-vindo, " + usuarioLogado.getName(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(v.getContext(), PerfilActivity.class));
                finish();
            }
        });
    }



    public void onRegister(View view){
        Intent i = new Intent(view.getContext(), RegisterNoSecurityActivity.class);
        startActivity(i);
        finish();
    }
}
