package br.com.mobilidade.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.mobilidade.agenda.converter.AlunoConverter;
import br.com.mobilidade.agenda.dao.AlunoDAO;
import br.com.mobilidade.agenda.modelo.Aluno;

/**
 * Created by Igor Silva on 06/06/2017.
 */

public class EnviaAlunoTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunoTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando dados....", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {

        AlunoDAO alunoDAO = new AlunoDAO(context);
        List<Aluno> alunos = alunoDAO.listarAlunos();
        alunoDAO.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJson(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        super.onPostExecute(resposta);
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
