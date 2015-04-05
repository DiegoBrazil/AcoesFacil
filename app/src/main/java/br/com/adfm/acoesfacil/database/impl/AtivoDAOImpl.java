package br.com.adfm.acoesfacil.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.BDAcoesFacilHelper;
import br.com.adfm.acoesfacil.model.Ativo;

/**
 * Created by alexandre-ms on 02/04/15.
 */
public class AtivoDAOImpl implements AtivoDAO {

    private SQLiteDatabase db;

    private Ativo criarEntidade(Cursor cursor) {
        Ativo ativo = null;

        if (cursor == null) {
            throw new IllegalArgumentException("Registro não encontrado.");
        }

        if (cursor.getCount() > 0) {
            String codigo = cursor.getString(cursor.getColumnIndex(BDAcoesFacilHelper.COL_ID_FAV));
            Double qtd = cursor.getDouble(cursor.getColumnIndex(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV));
            Double vlr = cursor.getDouble(cursor.getColumnIndex(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV));

            ativo = new Ativo(codigo, vlr, qtd);
            Log.d("DATABASE", "Ativo -> " + ativo.getCodigo());
        }

        return ativo;
    }

    private ContentValues mapearCampos( Ativo ativo) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BDAcoesFacilHelper.COL_ID_FAV, ativo.getCodigo());
        contentValues.put(BDAcoesFacilHelper.COL_QTD_COMPRA_FAV, ativo.getQuantidadeCompra());
        contentValues.put(BDAcoesFacilHelper.COL_VLR_COMPRA_FAV, ativo.getPrecoCompra());

        return contentValues;
    }

    public AtivoDAOImpl(Context context) {
        this.db = BDAcoesFacilHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public void adicionar(Ativo ativo) {
        long id = this.db.insert(BDAcoesFacilHelper.TB_FAVORITOS, null, this.mapearCampos(ativo));
    }

    @Override
    public void alterar(Ativo ativo) {
        long id = this.db.update(BDAcoesFacilHelper.TB_FAVORITOS, this.mapearCampos(ativo),
            BDAcoesFacilHelper.COL_ID_FAV + " =?", new String[]{ativo.getCodigo()}
        );
    }

    @Override
    public void remover(Ativo ativo) {
        this.db.delete(BDAcoesFacilHelper.TB_FAVORITOS, BDAcoesFacilHelper.COL_ID_FAV + " = ?",
            new String[]{ ativo.getCodigo()}
        );
    }

    @Override
    public List<Ativo> listarFavoritos() {
        List<Ativo> lista = new ArrayList<>();
        String sqlConsulta = "Select * from " + BDAcoesFacilHelper.TB_FAVORITOS + " ORDER BY " + BDAcoesFacilHelper.COL_ID_FAV;
        Cursor cursor = this.db.rawQuery(sqlConsulta,null);

        // Achou
        if (cursor != null){
            // Move para o primeiro
            cursor.moveToFirst();
            // Enquanto for antes do último
            while(cursor.isAfterLast() == false){
                lista.add(this.criarEntidade(cursor));
                // Próximo
                cursor.moveToNext();
            }

            cursor.close();
        }

        return lista;
    }

    @Override
    public Ativo consultarPeloAtivo(String id) {
        String sqlConsulta = "SELECT * From " + BDAcoesFacilHelper.TB_FAVORITOS + " " +
                " Where " + BDAcoesFacilHelper.COL_ID_ATIVOS + " = ? ";
        Ativo ativo = null;

        Cursor cursor = this.db.rawQuery(sqlConsulta,
            new String[]{id}
        );

        if (cursor != null) {
            cursor.moveToFirst();
            ativo = this.criarEntidade(cursor);
            if (!cursor.isClosed()){
                cursor.close();
            }
        }

        return ativo;
    }


}
