package br.com.adfm.acoesfacil.view.main;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;
import br.com.adfm.acoesfacil.service.ConsultaAcoes;
import br.com.adfm.acoesfacil.service.ConsultaAcoesXML;

public class AcoesActivity extends ActionBarActivity {

    private Ativo ativo;
    private ImageButton btnBuscar;
    private ImageView imgFavorito;
    private ImageView imgOscilacao;
    private EditText txtAtivo;
    private AtivoDAO dao = null;
    private ConsultaAcoes consultaAcoes;
    private TextView lblAtivo;
    private TextView lblUltimoPreco;
    private TextView lblPercentualOscilacao;
    private TextView lblAbertura;
    private TextView lblMedio;
    private TextView lblMinima;
    private TextView lblMaxima;
    private TextView lblData;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes);

        this.btnBuscar = (ImageButton) findViewById(R.id.btnBuscar);
        this.imgFavorito = (ImageView) findViewById(R.id.imgFavorito);
        this.imgOscilacao = (ImageView) findViewById(R.id.imgOscilacao);
        this.txtAtivo = (EditText) findViewById(R.id.txtAtivo);

        this.lblAtivo = (TextView) findViewById(R.id.lblAtivo);
        this.lblUltimoPreco = (TextView) findViewById(R.id.lblUltimoPreco);
        this.lblPercentualOscilacao = (TextView) findViewById(R.id.lblPercentualOscilacao);
        this.lblAbertura = (TextView) findViewById(R.id.lblAbertura);
        this.lblMedio = (TextView) findViewById(R.id.lblMedio);
        this.lblMinima = (TextView) findViewById(R.id.lblMinima);
        this.lblMaxima= (TextView) findViewById(R.id.lblMaxima);
        this.lblData= (TextView) findViewById(R.id.lblData);
        this.progress = new ProgressDialog(this);

        limparTela();

        this.dao = new AtivoDAOImpl(getApplicationContext());

        addListener();
    }

    private void limparTela() {
        AcoesActivity.this.imgFavorito.setImageResource(R.drawable.ic_acao_nao_favorita);
        this.imgFavorito.setVisibility(View.INVISIBLE);
        this.imgOscilacao.setVisibility(View.INVISIBLE);

        txtAtivo.setText("");
        this.lblAtivo.setText("");
        this.lblUltimoPreco.setText("");
        this.lblPercentualOscilacao.setText("");
        this.lblAbertura.setText("");
        this.lblMedio.setText("");
        this.lblMinima.setText("");
        this.lblMaxima.setText("");
        this.lblData.setText("  ");
    }

    private void addListener() {
        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AcoesActivity.this.txtAtivo.getText() == null || AcoesActivity.this.txtAtivo.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_vazio , Toast.LENGTH_SHORT).show();
                    AcoesActivity.this.txtAtivo.findFocus();
                } else {
                    String strAtivo = AcoesActivity.this.txtAtivo.getText().toString();

                    String link= "http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel=" + strAtivo.toUpperCase();
                    AcoesActivity.this.consultaAcoes = new ConsultaAcoesXML(link );
                    progress = new ProgressDialog(AcoesActivity.this);
                    progress.setMessage("Por favor aguarde");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();
                    new ConsultarAcoesAsynckTask().execute(strAtivo.toUpperCase());

                }
            }
        });
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    private class ConsultarAcoesAsynckTask extends AsyncTask<String, Void, Ativo>{
        String link= "http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel=";
        private String strUrl = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Ativo doInBackground(String... params) {
            ConsultaAcoes consultaAcoes = new ConsultaAcoesXML(link + params[0].toUpperCase());

            return consultaAcoes.consultar();
        }

        @Override
        protected void onPostExecute(Ativo ativo) {

            if (ativo == null || ativo.getCodigo() == null ) {
                Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_nao_encontrado , Toast.LENGTH_SHORT).show();
            } else {

                AcoesActivity.this.lblAtivo.setText(ativo.getCodigo());

                AcoesActivity.this.lblUltimoPreco.setText("R$ " + ativo.getUltimo());
                AcoesActivity.this.lblAbertura.setText("Abert R$ " + ativo.getAbertura());
                AcoesActivity.this.lblMedio.setText("Médio R$ " + ativo.getMedio());
                AcoesActivity.this.lblMinima.setText("Máx. R$ " + ativo.getMinimo());
                AcoesActivity.this.lblMaxima.setText("Min R$ " + ativo.getMaximo());

                AcoesActivity.this.imgOscilacao.setVisibility(View.VISIBLE);
                AcoesActivity.this.imgFavorito.setVisibility(View.VISIBLE);

                Ativo ativoTemp = AcoesActivity.this.dao.consultarPeloAtivo(ativo.getCodigo());

                if (ativoTemp != null) {
                    AcoesActivity.this.imgFavorito.setImageResource(R.drawable.ic_acao_favorito);
                } else {
                    AcoesActivity.this.imgFavorito.setImageResource(R.drawable.ic_acao_nao_favorita);
                }

                if (ativo.getOscilacao() > 0){
                    AcoesActivity.this.imgOscilacao.setImageResource(R.drawable.ic_up);
                } else {
                    AcoesActivity.this.imgOscilacao.setImageResource(R.drawable.ic_down);
                }
                Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_encontrado , Toast.LENGTH_SHORT).show();
            }
            AcoesActivity.this.progress.setProgress(100);
            AcoesActivity.this.progress.dismiss();
            Log.d("ASYNC Retorno", "");
        }
    }

    public void favoritarAtivo(View view) {
        if (this.lblAtivo.getText() == null || this.lblAtivo.getText().length() == 0 ){
            Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_vazio , Toast.LENGTH_SHORT).show();
        } else {
            String codigo = txtAtivo.getText().toString().toUpperCase();
            Ativo ativoTemp = this.dao.consultarPeloAtivo(codigo);

            if (ativoTemp != null){
                this.dao.remover(ativoTemp);
                this.imgFavorito.setImageResource(R.drawable.ic_acao_nao_favorita);
                Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_removido , Toast.LENGTH_SHORT).show();
            } else{
                this.dao.adicionar(new Ativo(codigo, 0d,0d));
                this.imgFavorito.setImageResource(R.drawable.ic_acao_favorito);
                Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_favorito , Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_novo) {
            limparTela();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
