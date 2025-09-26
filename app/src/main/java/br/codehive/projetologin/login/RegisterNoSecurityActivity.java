package br.codehive.projetologin.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import br.codehive.projetologin.R;
import br.codehive.projetologin.database.UsuarioDatabase;
import br.codehive.projetologin.model.PerfilModel;
import br.codehive.projetologin.model.ValidationType;
import br.codehive.projetologin.perfil.PerfilActivity;
import br.codehive.projetologin.services.UsuarioService;
import br.codehive.projetologin.shared.UtilidadeGerais;

public class RegisterNoSecurityActivity extends AppCompatActivity {

    private TextInputLayout editEmail,editPassword,editName;
    private Button btnEnviar;
    private UtilidadeGerais gerais;
    private UsuarioService service;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        gerais = new UtilidadeGerais(this);
        service = new UsuarioService(this);
        gerais.limparTudo();
        init();
    }

    private void init(){
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        editName = findViewById(R.id.edit_nome);
        btnEnviar = findViewById(R.id.btn_enviar);

        editEmail.getEditText().setText("pedro@gmail.com");
        editPassword.getEditText().setText("123456");
        editName.getEditText().setText("pedro");

        btnEnviar.setOnClickListener(v->{
            boolean isEmailValid = gerais.valid(editEmail, ValidationType.EMAIL);
            boolean isPasswordValid = gerais.valid(editPassword, ValidationType.PASSWORD);
            boolean isNameValid = gerais.valid(editName, ValidationType.GENERIC_TEXT);
            if (isEmailValid && isPasswordValid && isNameValid) {
                String email = editEmail.getEditText().getText().toString();
                String password = editPassword.getEditText().getText().toString();
                String nome = editName.getEditText().getText().toString();

                PerfilModel model = new PerfilModel();
                model.setEmail(email);
                model.setPassword(password);
                model.setName(nome);
                model.setImagemPerfil("");
                boolean rt = service.inSign(model);
                if(!rt){
                    Log.e("RegisterFail","Falha ao register!");
                    Toast.makeText(v.getContext(), "Falha no register!", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(v.getContext(), "Register Válido!\nEmail: " + email, Toast.LENGTH_LONG).show();
                startActivity(new Intent(v.getContext(), LoginNoSecurityActivity.class));
                finish();
            }
        });
    }

    public void onLogin(View view){
        Intent i = new Intent(view.getContext(), LoginNoSecurityActivity.class);
        startActivity(i);
        //finish();
    }
}
