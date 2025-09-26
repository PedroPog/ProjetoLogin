package br.codehive.projetologin.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

    public static boolean verificarOrientacao(Context context){
        int orientacao = context.getResources().getConfiguration().orientation;
        return orientacao == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Paramentro de configuração hash
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    // Tamanho do "sal" em bytes. 16 bytes (128 bits) é um bom tamanho.
    private static final int SALT_SIZE = 16;
    // Número de iterações. Aumenta a dificuldade de ataques de força bruta.
    private static final int ITERATIONS = 10000;
    // Tamanho da chave (hash final) em bits.
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(String password){
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_SIZE];
            random.nextBytes(salt);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String saltB64 = Base64.encodeToString(salt, Base64.DEFAULT);
            String hashB64 = Base64.encodeToString(hash, Base64.DEFAULT);
            return saltB64 + ":" + hashB64;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            byte[] salt = Base64.decode(parts[0], Base64.DEFAULT);
            byte[] hash = Base64.decode(parts[1], Base64.DEFAULT);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] testHash = factory.generateSecret(spec).getEncoded();
            return Arrays.equals(hash, testHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IllegalArgumentException e) {
            return false;
        }
    }

}
