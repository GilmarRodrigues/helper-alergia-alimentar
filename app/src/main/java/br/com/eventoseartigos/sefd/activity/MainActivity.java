package br.com.eventoseartigos.sefd.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.fragment.CertificadosFragment;
import br.com.eventoseartigos.sefd.fragment.EventosFragment;
import br.com.eventoseartigos.sefd.fragment.InscricoesFragment;
import br.com.eventoseartigos.sefd.model.ListUsuario;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.model.Usuario;
import br.com.eventoseartigos.sefd.service.UsuarioService;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, Transacao {
    private TextView campo_email;
    private TextView campo_nome;
    private String token;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        Login login = (Login) getIntent().getParcelableExtra(Login.KEY);
        if (login != null) {
            Prefs.setString(this, Login.EMAIL, login.getUserName());
        }

        token = Prefs.getString(this, "token");

        campo_email = (TextView) headerView.findViewById(R.id.tv_email);
        campo_nome = (TextView) headerView.findViewById(R.id.tv_username);

        setFirstItemNavigationView(navigationView);

        startTrasacaoSemProgress(this);

    }

    private void setUser() {
        String email = Prefs.getString(this, Login.EMAIL);
        if (email != "") {
            campo_email.setText(email);
        }
        campo_nome.setText(usuario.getFirstName()+" "+ usuario.getLastName());
    }

    private void setFirstItemNavigationView(NavigationView navigationView) {
        //replaceFragment(new EventosFragment());
        navigationView.setCheckedItem(R.id.nav_eventos);
        navigationView.getMenu().performIdentifierAction(R.id.nav_eventos, 0);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Prefs.setString(this, Login.TOKEN, null);
            Prefs.setString(this, Login.EMAIL, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_eventos) {
            replaceFragment(new EventosFragment());
        } else if (id == R.id.nav_inscricoes) {
            replaceFragment(new InscricoesFragment());
        } else if (id == R.id.nav_certificados) {
            replaceFragment(new CertificadosFragment());
        } else if(id == R.id.nav_peril){
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra(Usuario.KEY, usuario);
            startActivity(intent);
        }else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment frag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_drawer_cotainer, frag, "TAG").commit();
    }

    @Override
    public void executar() throws Exception {
        usuario = UsuarioService.getUsuario(token);
    }

    @Override
    public void atualizarView() {
        setUser();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
