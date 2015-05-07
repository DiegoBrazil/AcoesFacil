package br.com.adfm.acoesfacil.view.main;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import br.com.adfm.acoesfacil.R;


public class HomeActivity extends TabActivity implements TabHost.OnTabChangeListener{
    // Get TabHost Refference
    TabHost tabHost;
    TabHost.TabSpec tabSpecAcoes;
    TabHost.TabSpec tabSpecAtivosFavoritos;
    TabHost.TabSpec tabSpecConfig;
    Intent intentAcoes;
    Intent intentFavoritos;
    Intent intentConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        // Get TabHost Refference
        tabHost = getTabHost();
        tabHost.setOnTabChangedListener(this);
        tabHost.setBackgroundColor(Color.parseColor("#1E90FF"));

        tabSpecAcoes = tabHost.newTabSpec("Ações");
        tabSpecAcoes.setIndicator("Ações");
        intentAcoes = new Intent(this, AcoesActivity.class);
        tabSpecAcoes.setContent(intentAcoes);

        tabSpecAtivosFavoritos = tabHost.newTabSpec("Favoritos");
        tabSpecAtivosFavoritos.setIndicator("Favoritos");
        intentFavoritos = new Intent(this, AtivosFavoritos.class);
        tabSpecAtivosFavoritos.setContent(intentFavoritos);

        tabSpecConfig = tabHost.newTabSpec("Config.");
        tabSpecConfig.setIndicator("Config.");
        intentConfig = new Intent(this, ConfiguracaoActivity.class);
        tabSpecConfig.setContent(intentConfig);

        tabHost.addTab(tabSpecAcoes);
        tabHost.addTab(tabSpecAtivosFavoritos);
        tabHost.addTab(tabSpecConfig);

        tabHost.setCurrentTab(0);
    }

    public void mostrarConf(View view ){
/*
        Intent intent = new Intent(this, ConfiguracaoActivity.class);
        startActivity(intent);
*/
/*
        tabSpecConfig.setIndicator("Configurações");
        intentConfig = new Intent(this, ConfiguracaoActivity.class);
        tabSpecConfig.setContent(intentConfig);
        tabHost.setCurrentTab(2);
        tabHost.bringToFront();
*/
    }

    public void mostrarFavoritos(View view){
/*
        Intent intent = new Intent(this, AtivosFavoritos.class);
        startActivity(intent);
*/
/*
        tabSpecAtivosFavoritos.setIndicator("Favoritos");
        intentFavoritos = new Intent(this, AtivosFavoritos.class);
        tabSpecAtivosFavoritos.setContent(intentFavoritos);
        tabHost.setCurrentTab(1);
        tabHost.bringToFront();
*/
    }

    public void mostrarAcao(View view){
/*
        Intent intent = new Intent(this, AcoesActivity.class);
        startActivity(intent);
*/
/*
        tabSpecAcoes.setIndicator("Ações");
        intentAcoes = new Intent(this, AcoesActivity.class);
        tabSpecAcoes.setContent(intentAcoes);
        tabHost.setCurrentTab(0);
        tabHost.bringToFront();
*/
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabHost.getCurrentTab() == 0) {
            this.mostrarAcao(getCurrentFocus());
        }
        if (tabHost.getCurrentTab() == 1) {
            this.mostrarFavoritos(getCurrentFocus());
        }
        if (tabHost.getCurrentTab() == 2) {
            this.mostrarConf(getCurrentFocus());
        }
    }
}
