package br.com.adfm.acoesfacil;

import android.app.Application;
import android.test.ApplicationTestCase;

import br.com.adfm.acoesfacil.database.AtivoDAO;
import br.com.adfm.acoesfacil.database.impl.AtivoDAOImpl;
import br.com.adfm.acoesfacil.model.Ativo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        AtivoDAO dao = new AtivoDAOImpl(getContext());
        //Ativo ativo = new Ativo("PETR4", 0d, 0d);
        //dao.adicionar(ativo);
        assertTrue(dao.listarFavoritos().size() >0);
    }
}