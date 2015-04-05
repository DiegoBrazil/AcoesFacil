package br.com.adfm.acoesfacil.view.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.adfm.acoesfacil.R;

public class ConfiguracaoActivity extends ActionBarActivity {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        sharedPref = getSharedPreferences("activity_configuracao",Context.MODE_PRIVATE);
        Float cor = sharedPref.getFloat("valorCorretagem", 0);
        Float emo = sharedPref.getFloat("valorEmolumento", 0);
        Float cus = sharedPref.getFloat("valorCustodia", 0);
        Float ir = sharedPref.getFloat("valorImpRenda", 0);

        EditText corr = (EditText)findViewById(R.id.valorCorretagem);
        corr.setText(cor.toString());
        EditText cust = (EditText)findViewById(R.id.valorCustodia);
        cust.setText(cus.toString());
        EditText emol = (EditText)findViewById(R.id.valorEmolumento);
        emol.setText(emo.toString());
        EditText impr = (EditText)findViewById(R.id.valorImpRenda);
        impr.setText(ir.toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void salvarConfig(View view){
        EditText corr = (EditText)findViewById(R.id.valorCorretagem);
        EditText cust = (EditText)findViewById(R.id.valorCustodia);
        EditText emol = (EditText)findViewById(R.id.valorEmolumento);
        EditText impr = (EditText)findViewById(R.id.valorImpRenda);

        //SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("valorCorretagem", Float.valueOf(corr.getText().toString()) );
        editor.putFloat("valorEmolumento", Float.valueOf(cust.getText().toString()) );
        editor.putFloat("valorCustodia", Float.valueOf(emol.getText().toString()) );
        editor.putFloat("valorImpRenda", Float.valueOf(impr.getText().toString()) );
        editor.commit();

        Toast.makeText(getApplicationContext(), R.string.registro_gravado_sucesso, Toast.LENGTH_SHORT).show();
    }
}
