package br.com.adfm.acoesfacil.view.main;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.BDAcoesFacilHelper;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;

public class EdicaoAtivoActivity extends ActionBarActivity {

    TextView nomeAtivo;
    TextView precoCompra;
    TextView quantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_ativo);

        Intent i = getIntent();
        String codigoAtivo = i.getStringExtra(BDAcoesFacilHelper.COL_ID_FAV);
        Double qtdeCompra = i.getDoubleExtra(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, 0.00);
        Double precoCompra = i.getDoubleExtra(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, 0.00);

        nomeAtivo = (TextView) findViewById(R.id.idNomeAtivo);
        nomeAtivo.setText(codigoAtivo);
        this.precoCompra = (TextView) findViewById(R.id.idPrecoCompra);
        this.precoCompra.setText(precoCompra.toString());
        quantidade = (TextView) findViewById(R.id.idQuantidade);
        quantidade.setText(qtdeCompra.toString());
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

    /**
     * Método responsável por fechar a EdicaoAtivoActivity e voltar para a AtivosFavoritos
     */
    public void cancelar(View v) {
        finish();
    }

    public void gravarAlteracao(View v) {
        AtivoDAO dao = new AtivoDAOImpl(this);
        Ativo ativo = new Ativo(nomeAtivo.getText().toString(),
                                Double.valueOf(precoCompra.getText().toString()),
                                Double.valueOf(quantidade.getText().toString()));
        dao.alterar(ativo);
        finish();
    }
}
