package com.example.jhonatandantas.fatec_newsv1.modelo;

public class noticia {

    private String id, titulo, descricao, email, Data_Publicacao, url, curso_noticia, categoria_noticia, aprovado, visualizado;

    public noticia() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_Publicacao() {
        return Data_Publicacao;
    }

    public void setData_Publicacao(String data_Publicacao) {
        Data_Publicacao = data_Publicacao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurso_noticia() {
        return curso_noticia;
    }

    public void setCurso_noticia(String curso_noticia) {
        this.curso_noticia = curso_noticia;
    }

    public String getCategoria_noticia() {
        return categoria_noticia;
    }

    public void setCategoria_noticia(String categoria_noticia) {
        this.categoria_noticia = categoria_noticia;
    }

    public String getAprovado() {
        return aprovado;
    }

    public void setAprovado(String aprovado) {
        this.aprovado = aprovado;
    }

    public String getVisualizado() {
        return visualizado;
    }

    public void setVisualizado(String visualizado) {
        this.visualizado = visualizado;
    }
}
