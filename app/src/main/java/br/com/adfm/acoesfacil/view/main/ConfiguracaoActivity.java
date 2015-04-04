package br.com.adfm.acoesfacil.view.main;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.adfm.acoesfacil.R;

public class ConfiguracaoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    public void salvarConfig(View view){
        EditText corr = (EditText)findViewById(R.id.valorCorretagem);
        EditText cust = (EditText)findViewById(R.id.valorCustodia);
        EditText emol = (EditText)findViewById(R.id.valorEmolumento);
        EditText impr = (EditText)findViewById(R.id.valorImpRenda);
    }
}
