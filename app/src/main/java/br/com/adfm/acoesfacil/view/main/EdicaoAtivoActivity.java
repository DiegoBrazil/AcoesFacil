package br.com.adfm.acoesfacil.view.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.BDAcoesFacilHelper;
import br.com.adfm.acoesfacil.model.Ativo;
import de.greenrobot.event.EventBus;

public class EdicaoAtivoActivity extends ActionBarActivity {



    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_ativo);

      // Ativo myPOJO = (Ativo) EventBus.getDefault().removeStickyEvent(Ativo.class);

        Intent i = getIntent();
        String ativo = i.getStringExtra(BDAcoesFacilHelper.COL_ID_FAV);
        Double qtdeCompra = i.getDoubleExtra(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, 0.00);
        Double vlrCompra   = i.getDoubleExtra(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, 0.00);

        setContentView(R.layout.activity_edicao_ativo);

        TextView nomeAtivo = (TextView) findViewById(R.id.idNomeAtivo);
        nomeAtivo.setText(ativo);
        TextView precoCompra = (TextView) findViewById(R.id.idPrecoCompra);
        precoCompra.setText(vlrCompra.toString());
        TextView quantidade = (TextView) findViewById(R.id.idQuantidade);
        quantidade.setText(qtdeCompra.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edicao_ativo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
