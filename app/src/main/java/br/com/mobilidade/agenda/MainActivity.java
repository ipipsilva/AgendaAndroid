package br.com.mobilidade.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.mobilidade.agenda.dao.AlunoDAO;
import br.com.mobilidade.agenda.modelo.Aluno;

public class MainActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.lista_alunos);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);
                Intent intentChamaFormulario = new Intent(MainActivity.this, FormularioActivity.class);
                intentChamaFormulario.putExtra("aluno", aluno);
                startActivity(intentChamaFormulario);
            }
        });

        Button btnNovoAluno = (Button) findViewById(R.id.lista_btnNovoAluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentNovoAluno = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intentNovoAluno);
            }
        });

        registerForContextMenu(lista);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarAlunos();
    }

    private void listarAlunos() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.listarAlunos();
        alunoDAO.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_expandable_list_item_1, alunos);
        lista.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
       MenuItem deletar = menu.add("Deletar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) lista.getItemAtPosition(info.position);

                AlunoDAO alunoDAO = new AlunoDAO(MainActivity.this);
                alunoDAO.deletar(aluno);
                alunoDAO.close();

                listarAlunos();
                return false;
            }
        });
    }
}
