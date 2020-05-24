package com.example.jhonatandantas.fatec_newsv1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jhonatandantas.fatec_newsv1.Principal;
import com.example.jhonatandantas.fatec_newsv1.R;
import com.example.jhonatandantas.fatec_newsv1.modelo.noticia;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapter_Principal extends BaseAdapter {
    private List<noticia> Lista_Noticias;
    private android.content.Context context;

    public adapter_Principal(Context context, List<noticia> lista_Noticias) {
        this.context = context;
        this.Lista_Noticias = lista_Noticias;
    }

    public adapter_Principal(Principal principal) {
    }

    @Override
    public int getCount() {
        return Lista_Noticias.size();
    }

    @Override
    public Object getItem(int position) {
        return Lista_Noticias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    public View getView(int position, View conveView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.adapter_principal_, null);

        ImageView Imagem_Carregada = v.findViewById(R.id.Imagem_Carregada);
        TextView txtTitulo = v.findViewById(R.id.txtTitulo);
        TextView txtPublicacao = v.findViewById(R.id.txtPublicacao);
        TextView txtCategoria = v.findViewById(R.id.txtCategoria);
        TextView txtCurso = v.findViewById(R.id.txtCurso);

        String url = Lista_Noticias.get(position).getUrl();

        txtTitulo.setText(Lista_Noticias.get(position).getTitulo());
        txtPublicacao.setText("Publicado em " + Lista_Noticias.get(position).getData_Publicacao());

        String categoria = Lista_Noticias.get(position).getCategoria_noticia();
        String curso = Lista_Noticias.get(position).getCurso_noticia();

        if (categoria.equals("(Todos)") || categoria.equals("") || categoria.equals(null)) {
            txtCategoria.setVisibility(View.INVISIBLE);
        } else {
            txtCategoria.setText(categoria);
        }

        if (curso.equals("(Todos)") || curso.equals("") || curso.equals(null)) {
            txtCurso.setVisibility(View.INVISIBLE);
        } else {
            if (categoria.equals("(Todos)") || categoria.equals("") || categoria.equals(null)) {
                txtCategoria.setBackground(null);
                txtCategoria.setWidth(0);
            }
            txtCurso.setText(curso);
        }

        if (url.equals("") || url == null || url.trim().length() == 0) {
            Picasso.get().load(R.drawable.fatec3).placeholder(R.drawable.carregando).into(Imagem_Carregada);
        } else {
            Picasso.get().load(url).placeholder(R.drawable.carregando).into(Imagem_Carregada);
        }

        v.setTag(Lista_Noticias.get(position).getId());

        return v;
    }
}
