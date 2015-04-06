package br.com.adfm.acoesfacil.view.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;
import br.com.adfm.acoesfacil.service.ConsultaAcoes;
import br.com.adfm.acoesfacil.service.ConsultaAcoesXML;

public class AcoesActivity extends ActionBarActivity {

    private ImageButton btnBuscar;
    private ImageView imgFavorito;
    private EditText txtAtivo;
    private AtivoDAO dao = null;
    private ConsultaAcoes consultaAcoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes);

        this.btnBuscar = (ImageButton) findViewById(R.id.btnBuscar);
        this.imgFavorito = (ImageView) findViewById(R.id.imgFavorito);
        this.imgFavorito.setVisibility(View.INVISIBLE);
        this.txtAtivo = (EditText) findViewById(R.id.txtAtivo);

        this.dao = new AtivoDAOImpl(getApplicationContext());

        addListener();
    }

    private void addListener() {
        this.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AcoesActivity.this.txtAtivo.getText() == null || AcoesActivity.this.txtAtivo.getText().toString() == null) {
                    Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_vazio , Toast.LENGTH_SHORT).show();
                    AcoesActivity.this.txtAtivo.findFocus();
                } else {
                    String strAtivo = AcoesActivity.this.txtAtivo.getText().toString();

                    String link= "http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel=" + strAtivo;
                    AcoesActivity.this.consultaAcoes = new ConsultaAcoesXML(link );
                    AcoesActivity.this.consultaAcoes.consultar();

                    //Ativo ativo = AcoesActivity.this.dao.consultarPeloAtivo(strAtivo.toUpperCase());

                    /*AcoesActivity.this.imgFavorito.setVisibility(View.VISIBLE);


                    if (ativo == null){
                        AcoesActivity.this.imgFavorito.setImageResource(R.drawable.ic_acao_nao_favorita);
                        Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_nao_encontrado , Toast.LENGTH_SHORT).show();
                    } else{
                        AcoesActivity.this.imgFavorito.setImageResource(R.drawable.ic_acao_favorito);
                        Toast.makeText(getApplicationContext(),R.string.acao_msg_ativo_encontrado , Toast.LENGTH_SHORT).show();
                    }*/

                }
            }
        });
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
            this.imgFavorito.setVisibility(View.INVISIBLE);
            this.txtAtivo.setText("");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
