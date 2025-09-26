package br.codehive.projetologin.model;

public class ListaLoginModel {

    private String descricao;
    private Class<?> cls;

    public ListaLoginModel() {
    }

    public ListaLoginModel(String descricao, Class<?> cls) {
        this.descricao = descricao;
        this.cls = cls;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
