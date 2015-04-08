package br.com.adfm.acoesfacil.view.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.adfm.acoesfacil.R;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.acao:
                intent = new Intent(this, AcoesActivity.class);
                startActivity(intent);
                return true;
            case R.id.favoritos:
                intent = new Intent(this, AtivosFavoritos.class);
                startActivity(intent);
                return true;
            case R.id.configuracao:
                intent = new Intent(this, ConfiguracaoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostrarConf(View view ){
        Intent intent = new Intent(this, ConfiguracaoActivity.class);
        startActivity(intent);
    }

    public void mostrarFavoritos(View view){
        Intent intent = new Intent(this, AtivosFavoritos.class);
        startActivity(intent);
    }

    public void mostrarAcao(View view){
        Intent intent = new Intent(this, AcoesActivity.class);
        startActivity(intent);
    }
}
