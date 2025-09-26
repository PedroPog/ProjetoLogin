package br.codehive.projetologin;

import static br.codehive.projetologin.shared.UtilidadeGerais.verificarOrientacao;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.codehive.projetologin.adapter.ListaLoginAdapter;
import br.codehive.projetologin.database.DBHandler;
import br.codehive.projetologin.login.LoginNoSecurityActivity;
import br.codehive.projetologin.login.LoginSecurityHashActivity;
import br.codehive.projetologin.model.ListaLoginModel;
import br.codehive.projetologin.model.TypesLogin;
import br.codehive.projetologin.shared.UtilidadeGerais;

public class InterfaceActivity extends AppCompatActivity {

    private DBHandler dbHandler;
    private UtilidadeGerais gerais;
    private ListaLoginAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //! Inicialização banco
        dbHandler = new DBHandler(this);
        gerais = new UtilidadeGerais(this);

        recyclerView = findViewById(R.id.list_itens_login);

        gerais.limparTudo();
        List<ListaLoginModel> list = new ArrayList<>();
        //Teste para verificar grid
        /*for(int i = 0;i<28;i++){
            list.add(new ListaLoginModel("Login Sem Seguranção",LoginNoSecurityActivity.class));
            list.add(new ListaLoginModel("Login Seguranção Hash", LoginSecurityHashActivity.class));
        }*/
        list.add(new ListaLoginModel("Login Sem Seguranção",LoginNoSecurityActivity.class));
        list.add(new ListaLoginModel("Login Seguranção Hash", LoginSecurityHashActivity.class));
        adapter = new ListaLoginAdapter(list);
        if(verificarOrientacao(this)){
            recyclerView.setLayoutManager(new GridLayoutManager(this,8));
        }else{
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setAdapter(adapter);

    }
}