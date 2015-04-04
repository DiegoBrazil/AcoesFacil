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

import br.com.adfm.acoesfacil.R;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ConfiguracaoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Float cor = sharedPref.getFloat("valorCorretagem", 0);
        Float emo = sharedPref.getFloat("valorEmolumento", 0);
        Float cus = sharedPref.getFloat("valorCustodia", 0);
        Float ir = sharedPref.getFloat("valorImpRenda", 0);
        //Crouton.makeText(this, "Valor gravado: "+highScore, Style.CONFIRM).show();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.acao:
                //TODO menu ação
                return true;
            case R.id.favoritos:
                setContentView(R.layout.activity_ativos_favoritos);
                return true;
            case R.id.configuracao:
                Intent conf = new Intent(this,ConfiguracaoActivity.class);
                startActivity(conf);
                //setContentView(R.layout.activity_configuracao);
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

        SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putFloat("valorCorretagem", Float.valueOf(corr.getText().toString()) );
        editor.putFloat("valorEmolumento", Float.valueOf(cust.getText().toString()) );
        editor.putFloat("valorCustodia", Float.valueOf(emol.getText().toString()) );
        editor.putFloat("valorImpRenda", Float.valueOf(impr.getText().toString()) );
        editor.commit();

        Crouton.makeText(this,"Registro gravado com sucesso.", Style.CONFIRM ).show();
    }
}
