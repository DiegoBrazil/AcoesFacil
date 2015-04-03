package br.com.adfm.acoesfacil.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexandre-ms on 01/04/15.
 * <B>Descrição: </B>Classe que fornece conexão com o Banco de Dados
 */
public class BDAcoesFacilHelper extends SQLiteOpenHelper {

    private static BDAcoesFacilHelper instance;

    /*
        Nome e versão do banco de dados
     */
    private static final String BD_NOME ="DataBaseAcoesFacil";
    private static final int BD_VERSAO = 1;

    /*
        Tabelas do Sistema
     */
    public static final String TB_FAVORITOS = "TB_ATIVOS_FAVORITOS";
    public static final String TB_ATIVOS ="TB_ATIVOS";

    /*
       Colunas da Tabela de Ações Favoritas
     */
    public static final String COL_ID_FAV = "ID_ACAO";
    public static final String COL_QTD_COMPRA_FAV = "QTD_COMPRA";
    public static final String COL_VLR_COMPRA_FAV = "VL_COMPRA";

    /*
       Colunas da Tabela de Ações
     */
    public static final String COL_ID_ATIVOS = "ID_ACAO";
    public static final String COL_NOME_EMPRESA = "NOME_EMPRESA";

    /*
        SQL de criação da tabela de ativos Favoritos
     */
    private static final String SQL_DML_TB_FAVORITOS = "CREATE TABLE if not exists " +
            BDAcoesFacilHelper.SQL_DML_TB_ATIVOS +
            BDAcoesFacilHelper.COL_ID_FAV + " text PRIMARY KEY," +
            BDAcoesFacilHelper.COL_QTD_COMPRA_FAV + "  real " +
            BDAcoesFacilHelper.COL_VLR_COMPRA_FAV +" real " ;

    /*
        SQL de exclusão das tabela de Ativos e Favoritos
     */
    private static final String SQL_DML_DROP_TB_FAVORITOS = "DROP TABLE if exists " + BDAcoesFacilHelper.TB_FAVORITOS;

    /*
        Sql de criação da tabela de ativos
     */
    private static final String SQL_DML_TB_ATIVOS = "CREATE TABLE if not exists " +
            BDAcoesFacilHelper.TB_ATIVOS +
            BDAcoesFacilHelper.COL_ID_ATIVOS + " text PRIMARY KEY" +
            BDAcoesFacilHelper.COL_NOME_EMPRESA + " text";
    private static final String SQL_DML_DROP_TB_ATIVOS = "DROP TABLE if exists " + BDAcoesFacilHelper.TB_ATIVOS;

    private SQLiteDatabase db;

    public BDAcoesFacilHelper(Context context) {
        super(context, BDAcoesFacilHelper.BD_NOME, null, BDAcoesFacilHelper.BD_VERSAO);
    }

    /**
     * <b>Descrição:</b> Fábrica de Helper
     * @param context
     * @return
     */
    public static synchronized BDAcoesFacilHelper getInstance(Context context) {
        // Cria apenas uma instância do Helper
        if (instance == null) {
            instance = new BDAcoesFacilHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela de favoritos
        db.execSQL(SQL_DML_TB_FAVORITOS);
        // Cria a tabela de ativos
        db.execSQL(SQL_DML_TB_ATIVOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // remove as tabelas
        db.execSQL(BDAcoesFacilHelper.SQL_DML_DROP_TB_ATIVOS);
        db.execSQL(BDAcoesFacilHelper.SQL_DML_DROP_TB_ATIVOS);
        // Cria novamente
        onCreate(db);
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
