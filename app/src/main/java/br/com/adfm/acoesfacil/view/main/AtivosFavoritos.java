package br.com.adfm.acoesfacil.view.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.BDAcoesFacilHelper;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;
import de.greenrobot.event.EventBus;

import static android.app.PendingIntent.getActivity;
import static android.widget.AdapterView.OnItemClickListener;

/**
 * Classe para controlar os favoritos.
 * Created by akio on 03/04/15.
 */
public class AtivosFavoritos extends ActionBarActivity {

    private SimpleAdapter dataAdapter;
    private AtivoDAO ativoDAO;
    private View thisView;
    private ListView listView;
    private EditText et;//
    private List<Map<String, String>> listAtivoFavorito;
    private ArrayList<String> listAtivosFavoritos_Encontrados = new ArrayList<String>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos_favoritos);

        this.carregarTodosFavoritosNaListView();

        /**
         * Chamada para a tela de edição do favorito selecionado (clicado) na ListView
         * Diego vc pode usar esta parte aqui.
         */
        listView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long index) {
                    //Pegar o item clicado e suas informações para passar para a próxima intent
                    HashMap<String,String> o = (HashMap) listView.getItemAtPosition(position);
                   // EventBus.getDefault().postSticky(new Ativo(o.get("COL_ID_FAV"),
                   //                                             Double.valueOf(o.get("COL_VLR_COMPRA_FAV")),
                   //                                             Double.valueOf(o.get("COL_QTD_COMPRA_FAV"))));


                    String ativoFavorito = o.get(BDAcoesFacilHelper.COL_ID_FAV);
                    Double quantidade =  Double.valueOf(o.get(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV));
                    Double valor = Double.valueOf(o.get(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV));

                    Intent intent = new Intent(getApplicationContext(), EdicaoAtivoActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString(BDAcoesFacilHelper.COL_ID_FAV, ativoFavorito);
                    bundle.putDouble(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, quantidade);
                    bundle.putDouble(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, valor);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        );
    }


    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    /**
     * Carregar todos os favoritos na ListView.
     * @since 04/04/2015.
     */
    private void carregarTodosFavoritosNaListView() {
        listAtivoFavorito = this.getFavoritosList();

        String[] from = new String[] {
                BDAcoesFacilHelper.COL_ID_FAV,
                BDAcoesFacilHelper.COL_QTD_COMPRA_FAV,
                BDAcoesFacilHelper.COL_VLR_COMPRA_FAV
        };

        int[] to = new int[] {
                R.id.textAtivo,
                R.id.textQtdeCompra,
                R.id.textVlrCompra
        };

        dataAdapter = new SimpleAdapter(this, listAtivoFavorito, R.layout.row_ativo_favorito, from, to);
        listView = (ListView) findViewById(R.id.ativosList);
        listView.setAdapter(dataAdapter);
    }


    /**
     * Carrega a lista objectos.
     * @return List<String>
     */
    private List<Map<String, String>> getFavoritosList() {
        ativoDAO = new AtivoDAOImpl(this);
        List<Ativo> listAtivoFavorito = ativoDAO.listarFavoritos();

        List<Map<String, String>> lista = new ArrayList<Map<String, String>>();
        for (int i = 0; i < listAtivoFavorito.size(); i++) {

            Map<String, String> m = new HashMap<String, String>();
            m.put(BDAcoesFacilHelper.COL_ID_FAV, listAtivoFavorito.get(i).getCodigo());
            m.put(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, listAtivoFavorito.get(i).getQuantidadeCompra().toString());
            m.put(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, listAtivoFavorito.get(i).getPrecoCompra().toString());

            lista.add(m);
        }
        return lista;
    }

}