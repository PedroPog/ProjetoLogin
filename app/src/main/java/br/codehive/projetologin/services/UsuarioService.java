package br.codehive.projetologin.services;

import static br.codehive.projetologin.shared.UtilidadeGerais.hashPassword;
import static br.codehive.projetologin.shared.UtilidadeGerais.verifyPassword;

import android.content.Context;

import br.codehive.projetologin.database.UsuarioDatabase;
import br.codehive.projetologin.model.PerfilModel;

public class UsuarioService {

    private UsuarioDatabase database;
    private Context context;


    public UsuarioService(Context context) {
        this.context = context;
        database = new UsuarioDatabase(context);
    }

    public PerfilModel inlogin(PerfilModel model){
        PerfilModel usuarioDoBanco = database.loginUsuario(model);
        if (usuarioDoBanco == null || !usuarioDoBanco.getPassword().equals(model.getPassword())) {
            return null;
        }
        return usuarioDoBanco;
    }
    public PerfilModel inloginHash(PerfilModel model){
        PerfilModel usuarioDoBanco = database.loginUsuario(model);
        if (usuarioDoBanco == null){
            return null;
        }
        if(!verifyPassword(model.getPassword(),usuarioDoBanco.getPassword())){
            return null;
        }
        return usuarioDoBanco;
    }

    public boolean inSign(PerfilModel model){
       long rt = database.inserirUsuario(model);
       return rt != -1;
    }
    public boolean inSignHash(PerfilModel model){
        model.setPassword(hashPassword(model.getPassword()));
        long rt = database.inserirUsuario(model);
        return rt != -1;
    }

    public boolean updateUsuario(PerfilModel model){
        long rt = database.updateUsuario(model);
        return rt != 0;
    }

    public boolean deleteUsuario(long id){
        long rt = database.deleteUsuario(id);
        return rt != 0;
    }
}
