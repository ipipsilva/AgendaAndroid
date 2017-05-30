package br.com.mobilidade.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

import br.com.mobilidade.agenda.dao.AlunoDAO;
import br.com.mobilidade.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    FormularioHelper formularioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formularioHelper = new FormularioHelper(this);
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        formularioHelper.preencheFormulario(aluno);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.ok:
                Aluno aluno = formularioHelper.pegaAluno();
                AlunoDAO alunoDAO = new AlunoDAO(this);

                if(null != aluno.getId()){
                    alunoDAO.alterarAluno(aluno);
                }else{
                    alunoDAO.salvarAluno(aluno);
                }

                alunoDAO.close();

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " adicionado!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
