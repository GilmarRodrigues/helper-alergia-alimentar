package br.com.eventoseartigos.sefd.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.utils.AndroidUtils;

public class ConfiguracaoActivity extends BaseActivity {

    private static final String APP_PACKAGER_NAME = "br.com.eventoseartigos.sefd";
    private static final String URL_SUPPORT = "http://sefd.ifpicos.com.br/";
    private static final String URL_TERMOS_DE_USO = "http://sefd.ifpicos.com.br/";
    private static final String URL_POLITICA_PRIVACIDADE = "http://sefd.ifpicos.com.br/";
    private static final String URL_FACEBOOK = "http://sefd.ifpicos.com.br/";
    private static final String URL_TWITTER = "http://sefd.ifpicos.com.br/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.tv_contate_suport).setOnClickListener(onClickSuport());
        findViewById(R.id.tv_termos_uso).setOnClickListener(onClickTermosDeUso());
        findViewById(R.id.tv_politica_privacidade).setOnClickListener(onClickPoliticaPrivacidade());
        findViewById(R.id.rl_facebook).setOnClickListener(onClickFacebook());
        findViewById(R.id.rl_twitter).setOnClickListener(onClickTwitter());
        findViewById(R.id.rl_convide_amigos).setOnClickListener(onClickConvideAmigos());
        findViewById(R.id.rl_avaliar).setOnClickListener(onClickAvaliar());
        findViewById(R.id.tv_sair).setOnClickListener(onClickSair());

        TextView tv_numro_versao = (TextView) findViewById(R.id.tv_numero_versao);
        tv_numro_versao.setText(AndroidUtils.getVersionName(this));
    }


    private View.OnClickListener onClickSuport() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browser(URL_SUPPORT);
            }
        };
    }

    private View.OnClickListener onClickTermosDeUso() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browser(URL_TERMOS_DE_USO);
            }
        };
    }

    private View.OnClickListener onClickPoliticaPrivacidade() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browser(URL_POLITICA_PRIVACIDADE);
            }
        };
    }

    private View.OnClickListener onClickFacebook() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browser(URL_FACEBOOK);
            }
        };
    }

    private View.OnClickListener onClickTwitter() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browser(URL_TWITTER);
            }
        };
    }


    private View.OnClickListener onClickConvideAmigos() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        };
    }

    private View.OnClickListener onClickAvaliar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googlePlay();
            }
        };
    }

    private View.OnClickListener onClickSair() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.setString(ConfiguracaoActivity.this, Login.TOKEN, null);
                Prefs.setString(ConfiguracaoActivity.this, Login.EMAIL, null);
                Intent intent = new Intent(ConfiguracaoActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        };
    }
    
    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + ": " + "http://play.google.com/store/apps/details?id=" + APP_PACKAGER_NAME);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, getString(R.string.msg_compartilhar)));
    }

    private void googlePlay() {
        Intent irGooglePlay;
        try {
            irGooglePlay = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PACKAGER_NAME));
        } catch (android.content.ActivityNotFoundException anfe) {
            irGooglePlay = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + APP_PACKAGER_NAME));
        }
        startActivity(irGooglePlay);
    }

    private void browser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
