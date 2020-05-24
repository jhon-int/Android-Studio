package com.example.jhonatandantas.fatec_newsv1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jhonatandantas.fatec_newsv1.adapter.adapter_Principal;
import com.example.jhonatandantas.fatec_newsv1.modelo.noticia;
import com.example.jhonatandantas.fatec_newsv1.modelo.usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.widget.SearchView.OnQueryTextListener;
import static com.google.common.base.Ascii.toUpperCase;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    static boolean google = false;
    static boolean UsuarioCadastrado = true;
    FirebaseDatabase firebaseDatabse;
    DatabaseReference databaseReference;
    private ListView Lista_Dados;
    private String email = null, senha, url, data, Cursos, PalavraCurso = null, PalavraCategoria = null;
    private Switch swt_Curso, swt_Mes;
    private SearchView mSearchView;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private List<noticia> Lista_Noticias = new ArrayList<noticia>();
    private adapter_Principal arrayAdapterNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent params = getIntent();
        email = params.getStringExtra("email");
        google = params.getBooleanExtra("google", false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Lista Componentes
        inicializarFirebase();
        inicializarComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        swt_Curso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.onActionViewCollapsed();
                pesquisarPalavra("");
            }
        });
        swt_Mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.onActionViewCollapsed();
                pesquisarPalavra("");
            }
        });
    }

    private void inicializarComponentes() {
        Lista_Dados = findViewById(R.id.ltvNoticias); //ListView
        Lista_Dados.setOnItemClickListener(this);
        swt_Curso = findViewById(R.id.swt_Curso);
        swt_Mes = findViewById(R.id.swt_Mes);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Principal.this);
        firebaseDatabse = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabse.getReference();
        databaseReference.keepSynced(true);
    }

    private void verificarUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);

            TextView edtEmail = headerView.findViewById(R.id.txtEmail_Menu);
            edtEmail.setText(user.getEmail());
            email = user.getEmail();

            ImageView nav = headerView.findViewById(R.id.imagem_nav);
            Picasso.get().load(R.drawable.fatec4).into(nav);
        }
    }

    private void cadatrarUsuarioGoogle() {
        usuario USU = new usuario();
        USU.setId(user.getUid());
        USU.setNome(user.getDisplayName());
        USU.setEmail(user.getEmail());
        USU.setCurso_usuario("(Todos)");
        databaseReference.child("Usuarios").child(USU.getId()).setValue(USU);
        google = false;
    }

    private void verificarPalavra(final String palavra) {
        PalavraCurso = null;
        PalavraCategoria = null;

        if (!palavra.equals("")) {
            databaseReference.child("Cursos").orderByChild("nome").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        String DADOS = areaSnapshot.child("nome").getValue(String.class);
                        if (!DADOS.equals("(Todos)")) {
                            if (toUpperCase(DADOS).equals(toUpperCase(palavra))) {
                                PalavraCurso = DADOS;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            databaseReference.child("Categorias").orderByChild("nome").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                        String DADOS = areaSnapshot.child("nome").getValue(String.class);
                        if (!DADOS.equals("(Todos)")) {
                            if (toUpperCase(DADOS).equals(toUpperCase(palavra))) {
                                PalavraCategoria = DADOS;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void pesquisarPalavra(final String palavra) {
        UsuarioCadastrado = true; //Lista de Usuarios
        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    if (objSnapshot.child("email").getValue(String.class).equals(user.getEmail())) {
                        Cursos = objSnapshot.child("curso_usuario").getValue(String.class);

                        if (Cursos == null || Cursos.equals("") || Cursos.trim().length() <= 0) {
                            Cursos = "(Todos)";
                        }

                        if (Cursos.equals("(Todos)")) {
                            swt_Curso.setVisibility(View.INVISIBLE);
                        } else {
                            swt_Curso.setVisibility(View.VISIBLE);
                        }

                        if (google) { //Atualizar cadastro de usuario google
                            usuario USU = new usuario();
                            USU.setId(user.getUid());
                            USU.setNome(user.getDisplayName());
                            USU.setEmail(user.getEmail());
                            USU.setCurso_usuario(objSnapshot.child("curso_usuario").getValue(String.class));
                            databaseReference.child("Usuarios").child(USU.getId()).setValue(USU);
                            UsuarioCadastrado = false;
                            google = false;
                        }
                    } else {
                        UsuarioCadastrado = true;
                    }
                }

                if (google)
                    if (UsuarioCadastrado)
                        cadatrarUsuarioGoogle();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        verificarPalavra(palavra);

        Query query;
        if (palavra.equals("")) {
            query = databaseReference.child("Noticias").orderByChild("data_Publicacao");
        } else {
            if (PalavraCurso != null) {
                query = databaseReference.child("Noticias").orderByChild("data_Publicacao");
            } else {
                query = databaseReference.child("Noticias").orderByChild("data_Publicacao");
            }
        }

        Lista_Noticias.clear();

        query.addValueEventListener(new ValueEventListener() { //Lista de Noticias

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lista_Noticias.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    noticia DADOS = objSnapshot.getValue(noticia.class);
                    String titulo_noticia = objSnapshot.child("titulo").getValue(String.class);
                    String curso_flitro = objSnapshot.child("curso_noticia").getValue(String.class);
                    String categoria_flitro = objSnapshot.child("categoria_noticia").getValue(String.class);
                    String aprovado = objSnapshot.child("aprovado").getValue(String.class);

                    if (palavra.equals("")) {

                        if (swt_Mes.isChecked()) {
                            Date dataHoraAtual = new Date(); //pegar data string e converte para data date
                            data = objSnapshot.child("data_Publicacao").getValue(String.class);
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");

                            Date date = null;
                            try {
                                date = formato.parse(data);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            calendar.add(Calendar.DAY_OF_MONTH, 30);

                            if (calendar.getTime().after(dataHoraAtual)) {
                                if (swt_Curso.isChecked()) {
                                    if (curso_flitro.equals(Cursos))
                                        if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                                } else {
                                    if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                                }
                            }
                        } else {
                            if (swt_Curso.isChecked()) {
                                if (curso_flitro.equals(Cursos))
                                    if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            } else {
                                if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            }
                        }
                    } else if (PalavraCurso != null) {
                        if (toUpperCase(PalavraCurso).equals(toUpperCase(curso_flitro))) {
                            if (swt_Curso.isChecked()) {
                                if (curso_flitro.equals(Cursos))
                                    if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            } else {
                                if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            }
                        }
                    } else if (PalavraCategoria != null) {
                        if (toUpperCase(PalavraCategoria).equals(toUpperCase(categoria_flitro))) {
                            if (swt_Curso.isChecked()) {
                                if (curso_flitro.equals(Cursos))
                                    if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            } else {
                                if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            }
                        }
                    } else {
                        if (toUpperCase(titulo_noticia).startsWith(toUpperCase(palavra))) {
                            if (swt_Curso.isChecked()) {
                                if (curso_flitro.equals(Cursos))
                                    if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            } else {
                                if (aprovado.equals("S")) Lista_Noticias.add(DADOS);
                            }
                        }
                    }
                }
                Collections.reverse(Lista_Noticias);
                arrayAdapterNoticias = new adapter_Principal(getApplicationContext(), Lista_Noticias);
                Lista_Dados.setAdapter(arrayAdapterNoticias);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = conexao.getFirebaseAuth();
        user = conexao.getFirebaseUser();
        verificarUser();
        pesquisarPalavra("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater(); //Carrega o arquivo de menu.
        inflater.inflate(R.menu.menu_pesquisa, menu);

        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView(); //Pega o Componente pesquisa
        mSearchView.setQueryHint("Pesquisar notícias, cursos, outros...");
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pesquisarPalavra(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pesquisarPalavra(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            mSearchView.onActionViewExpanded();
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_enviar) {
            new AlertDialog.Builder(this).setTitle("Enviar Notícia").setMessage("Gostaria de enviar uma notícia?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Principal.this, Enviar.class);
                    startActivity(intent);
                }
            }).setNegativeButton("Não", null).show();
        } else if (id == R.id.nav_cursos) {
            Intent intent = new Intent(Principal.this, Curso.class);
            startActivity(intent);
        } else if (id == R.id.nav_categoria) {
            Intent intent = new Intent(Principal.this, Categoria.class);
            startActivity(intent);
        } else if (id == R.id.nav_home) {
        } else if (id == R.id.nav_minhas_noticias) {
            Intent intent = new Intent(Principal.this, Minhas_Noticias.class);
            startActivity(intent);
        } else if (id == R.id.nav_perfil) {
            Intent intent = new Intent(Principal.this, Perfil.class);
            startActivity(intent);
        } else if (id == R.id.nav_aprovar) {
            Intent intent = new Intent(Principal.this, Aprovar.class);
            startActivity(intent);
        } else if (id == R.id.nav_email) {
            new AlertDialog.Builder(this).setTitle("Enviar E-mail").setMessage("Gostaria de enviar um e-mail?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Principal.this, Email.class);
                    startActivity(intent);
                }
            }).setNegativeButton("Não", null).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        url = null;
        url = Lista_Noticias.get(position).getUrl();

        Intent intent = new Intent(this, Principal_Noticia.class);
        intent.putExtra("id", Lista_Noticias.get(position).getId());
        intent.putExtra("email", Lista_Noticias.get(position).getEmail());
        intent.putExtra("titulo", Lista_Noticias.get(position).getTitulo());
        intent.putExtra("data", Lista_Noticias.get(position).getData_Publicacao());
        intent.putExtra("descricao", Lista_Noticias.get(position).getDescricao());
        intent.putExtra("url", Lista_Noticias.get(position).getUrl());
        intent.putExtra("categoria", Lista_Noticias.get(position).getCategoria_noticia());
        intent.putExtra("curso", Lista_Noticias.get(position).getCurso_noticia());

        startActivity(intent);
    }
}
