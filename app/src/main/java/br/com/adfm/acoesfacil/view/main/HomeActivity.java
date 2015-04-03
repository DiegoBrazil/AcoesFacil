package br.com.adfm.acoesfacil.view.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        switch (item.getItemId()) {
            case R.id.acao:
                //TODO menu ação
                return true;
            case R.id.favoritos:
                setContentView(R.layout.ativos_favoritos);
                return true;
            case R.id.configuracao:
                //TODO menu conf
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
