package br.com.adfm.acoesfacil.view.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.BDAcoesFacilHelper;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;
import br.com.adfm.acoesfacil.service.ServicoConsultInterface;
import br.com.adfm.acoesfacil.service.ServicoConsultaAcao;
import de.greenrobot.event.EventBus;

import static android.app.PendingIntent.getActivity;
import static android.widget.AdapterView.OnItemClickListener;

/**
 * Classe para controlar os favoritos.
 * Created by akio on 03/04/15.
 */
public class AtivosFavoritos extends ActionBarActivity implements ServicoConsultInterface {

    private SimpleAdapter dataAdapter;
    private AtivoDAO ativoDAO;
    private View thisView;
    private ListView listView;
    private EditText et;//
    private List<Map<String, String>> listAtivoFavorito;
    private ArrayList<String> listAtivosFavoritos_Encontrados = new ArrayList<String>();
    private Double valorTot =0d;
    private Float cor = 0f;
    private Float emo = 0f;
    private Float cus = 0f;
    private Float ir = 0f;
    private String[] codigoAtivos;
    private String[] from = new String[] {
             BDAcoesFacilHelper.COL_ID_FAV,
             BDAcoesFacilHelper.COL_QTD_COMPRA_FAV,
             BDAcoesFacilHelper.COL_VLR_COMPRA_FAV,
            "IMAGEM",
            "OSCILACAO",
            "VALOR_ATUAL",
            "FECHA_ANT",
            "ABERTURA",
            "MAXIMA",
            "MINIMA",
            "DATA_ATUA"
    };//

    private int[] to = new int[] {
            R.id.textAtivo,
            R.id.textQtdeCompra,
            R.id.textVlrCompra,
            R.id.imgUp,
            R.id.oscilacao,
            R.id.textValorRealPrimeiraLinha,
            R.id.fechaAnt,
            R.id.abertura,
            R.id.maxima,
            R.id.minima,
            R.id.dataAtua
    };



     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos_favoritos);
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart(){
        buscarConfiguracao();
        valorTot = 0d;
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
        super.onStart();


        ServicoConsultaAcao serv = new ServicoConsultaAcao(this);
        serv.execute(codigoAtivos);

    }

    private void buscarConfiguracao(){
        SharedPreferences sharedPref = getSharedPreferences("activity_configuracao", Context.MODE_PRIVATE);
        cor = sharedPref.getFloat("valorCorretagem", 0);
        emo = sharedPref.getFloat("valorEmolumento", 0);
        cus = sharedPref.getFloat("valorCustodia", 0);
        ir = sharedPref.getFloat("valorImpRenda", 0);
    }

     /**
      * Atualiza a consulta com os valores das ações da Web
      * @param ativo
      */
    public void atualizarConsulta(List<Ativo> ativo){
        //ListView lista = (ListView) findViewById(R.id.ativosList);
        //lista.setVisibility(View.VISIBLE);
        //Log.d("QTD", String.valueOf(ativo.size()));
        for(Map<String,String> ativs : listAtivoFavorito){
            //ativo.get(ativo.indexOf(new Ativo(ativs.get(BDAcoesFacilHelper.COL_ID_FAV),null,null)))
            String codigo = ativs.get(BDAcoesFacilHelper.COL_ID_FAV);
            Ativo at = ativo.get(ativo.indexOf(new Ativo(codigo.toUpperCase(),null,null)));
            Double vlAtual = at.getUltimo();
            ativs.put("IMAGEM","");
            ativs.put("OSCILACAO",at.getOscilacao().toString());
            ativs.put("VALOR_ATUAL", vlAtual.toString());
            ativs.put("FECHA_ANT",at.getMedio().toString());
            ativs.put("ABERTURA",at.getAbertura().toString());
            ativs.put("MAXIMA",at.getMaximo().toString());
            ativs.put("MINIMA",at.getMinimo().toString());
            ativs.put("DATA_ATUA",at.getData()==null?"":at.getData().toString());
            setarValorTotal(Double.valueOf(ativs.get(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV)),vlAtual);
        }
        dataAdapter = new SimpleAdapter(this, listAtivoFavorito, R.layout.row_ativo_favorito, from, to);
        listView = (ListView) findViewById(R.id.ativosList);
        listView.setAdapter(dataAdapter);

        TextView te = (TextView)findViewById(R.id.valorTotalLista);
        NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        //DecimalFormat df = new DecimalFormat("#,##0.00");
        //df.setDecimalFormatSymbols( DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));
        te.setText(" "+df.format(valorTot));
        te.setVisibility(View.VISIBLE);
    }

     /**
      * Carregar todos os favoritos na ListView.
      * @since 04/04/2015.
      */
     private void carregarTodosFavoritosNaListView() {
         listAtivoFavorito = this.getFavoritosList();

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
        codigoAtivos = new String[listAtivoFavorito.size()];
        List<Map<String, String>> lista = new ArrayList<Map<String, String>>();
        for (int i = 0; i < listAtivoFavorito.size(); i++) {

            Map<String, String> m = new HashMap<String, String>();
            codigoAtivos[i] = listAtivoFavorito.get(i).getCodigo();
            m.put(BDAcoesFacilHelper.COL_ID_FAV, listAtivoFavorito.get(i).getCodigo());
            m.put(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, listAtivoFavorito.get(i).getQuantidadeCompra().toString());
            m.put(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, listAtivoFavorito.get(i).getPrecoCompra().toString());
            m.put("IMAGEM","");
            m.put("OSCILACAO","");
            m.put("VALOR_ATUAL","0");
            m.put("FECHA_ANT","0");
            m.put("ABERTURA","0");
            m.put("MAXIMA","0");
            m.put("MINIMA","0");
            m.put("DATA_ATUA","0");

            lista.add(m);
            setarValorTotal(listAtivoFavorito.get(i).getQuantidadeCompra(), 1d);
        }
        TextView te = (TextView)findViewById(R.id.valorTotalLista);
        NumberFormat df = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        //DecimalFormat df = new DecimalFormat("#,##0.00");
        //df.setDecimalFormatSymbols( DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));
        te.setText(" "+df.format(valorTot));
        return lista;
    }

    private void setarValorTotal(Double qtd, Double vlAtual){
        valorTot = valorTot+ qtd * vlAtual; //valor atual
        valorTot = valorTot-emo;
        if(ir!=0){
            valorTot = valorTot- ((valorTot *ir)/100);
        }
        valorTot= valorTot-cus;
    }


}