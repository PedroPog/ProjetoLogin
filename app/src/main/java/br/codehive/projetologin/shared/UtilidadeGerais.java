package br.codehive.projetologin.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import br.codehive.projetologin.R;
import br.codehive.projetologin.model.PerfilModel;
import br.codehive.projetologin.model.ValidationType;

public class UtilidadeGerais {

    private SharedPreferences sharedPref;
    private Gson gson;
    private static final String KEY_PERFIL = "perfil_usuario";

    public UtilidadeGerais(Context context) {
        this.sharedPref = context.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.gson = new Gson();
    }
    public void salvarPerfil(PerfilModel model) {
        SharedPreferences.Editor editor = sharedPref.edit();
        String perfilJson = gson.toJson(model);
        editor.putString(KEY_PERFIL, perfilJson);
        editor.apply();
    }
    public PerfilModel getPerfil() {
        String perfilJson = sharedPref.getString(KEY_PERFIL, null);
        if (perfilJson == null || perfilJson.isEmpty()) {
            return null;
        }
        return gson.fromJson(perfilJson, PerfilModel.class);
    }
    public void deletarPerfil() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(KEY_PERFIL);
        editor.apply();
    }
    public void limparTudo() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public boolean valid(TextInputLayout inputLayout, ValidationType type){
        String text = inputLayout.getEditText().getText().toString().trim();

        // 1. Validação de campo vazio (serve para todos os tipos)
        if (TextUtils.isEmpty(text)) {
            inputLayout.setError("Este campo não pode ser vazio");
            return false;
        }
        switch (type){
            case EMAIL:
                if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    inputLayout.setError("Formato de e-mail inválido");
                    return false;
                }
                break;
            case PASSWORD:
                if (text.length() < 6) {
                    inputLayout.setError("A senha deve ter no mínimo 6 caracteres");
                    return false;
                }
                break;
            case GENERIC_TEXT:
                break;
        }
        inputLayout.setError(null);
        inputLayout.setErrorEnabled(false);
        return true;
    }

    public boolean valid(EditText editText, ValidationType type){
        String text = editText.getText().toString().trim();

        // 1. Validação de campo vazio (serve para todos os tipos)
        if (TextUtils.isEmpty(text)) {
            editText.setError("Este campo não pode ser vazio");
            return false;
        }
        switch (type){
            case EMAIL:
                if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    editText.setError("Formato de e-mail inválido");
                    return false;
                }
                break;
            case PASSWORD:
                if (text.length() < 6) {
                    editText.setError("A senha deve ter no mínimo 6 caracteres");
                    return false;
                }
                break;
            case GENERIC_TEXT:
                break;
        }
        editText.setError(null);
        return true;
    }

}
