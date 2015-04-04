package br.com.adfm.acoesfacil.view.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.adfm.acoesfacil.R;
import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.BDAcoesFacilHelper;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Classe para controlar os favoritos.
 * Created by akio on 03/04/15.
 */
public class AtivosFavoritos extends Activity {

    private SimpleAdapter dataAdapter;
    private AtivoDAO ativoDAO; //

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativos_favoritos);

        List<Map<String, String>> listAtivoFavorito = this.getFavoritosList();

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

        ListView listView = (ListView) findViewById(R.id.ativosList);
        listView.setAdapter(dataAdapter);
    }

    /**
     * Fill a list object
     *
     * @return List<String>
     */
    private List<Map<String, String>> getFavoritosList() {

        ativoDAO = new AtivoDAOImpl(this);
        List<Ativo> listAtivoFavorito = ativoDAO.listarFavoritos();

        List<Map<String, String>> l = new ArrayList<Map<String, String>>();
        for (int i = 0; i < listAtivoFavorito.size(); i++) {

            Map<String, String> m = new HashMap<String, String>();
            m.put(BDAcoesFacilHelper.COL_ID_FAV, listAtivoFavorito.get(i).getCodigo());
            m.put(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, listAtivoFavorito.get(i).getQuantidadeCompra().toString());
            m.put(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, listAtivoFavorito.get(i).getPrecoCompra().toString());

            l.add(m);
        }

        return l;
    }

}
