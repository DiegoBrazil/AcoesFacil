package br.com.adfm.acoesfacil;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.List;

import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testCRUD() {

        Ativo ativo = new Ativo("PETR4", 1.0, 2.0);
        AtivoDAO ativoDAO = new AtivoDAOImpl(getContext());
        ativoDAO.adicionar(ativo);

        ativo = new Ativo("ABEV3", 1.1, 2.1);
        ativoDAO.adicionar(ativo);

        ativo = new Ativo("BRFS3", 1.2, 2.2);
        ativoDAO.adicionar(ativo);

        List<Ativo> ativoList = ativoDAO.listarFavoritos();
        if (!ativoList.isEmpty()) {
            ativoList.size();
        }

/*

        ABEV3	AMBEV S/A
        ALLL3	ALL AMER LAT
        BBAS3	BRASIL
        BBDC3	BRADESCO
        BBDC4	BRADESCO
        BBSE3	BBSEGURIDADE
        BRAP4	BRADESPAR
        BRFS3	BRF SA
        BRKM5	BRASKEM
*/


    }
}