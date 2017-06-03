package br.com.mobilidade.agenda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import java.util.jar.Manifest;

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

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) lista.getItemAtPosition(info.position);

        // Opção de ir para o site do aluno
        configuraMenuSite(menu, aluno);

        //Opção para exibir endereço
        configuraMenuEndereco(menu, aluno);
        
        //Opção para envio de sms
        configuraMenuSMS(menu, aluno);

        //Opção para chamada telefonica
        MenuItem menuLigar = menu.add("Ligar");

        menuLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });

        // Opção de deletar
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO alunoDAO = new AlunoDAO(MainActivity.this);
                alunoDAO.deletar(aluno);
                alunoDAO.close();

                listarAlunos();
                return false;
            }
        });
    }

    private void configuraMenuSMS(ContextMenu menu, Aluno aluno) {
        MenuItem menuSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        menuSMS.setIntent(intentSMS);
    }

    private void configuraMenuSite(ContextMenu menu, Aluno aluno) {
        MenuItem menuSite = menu.add("Ir para o site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);

        String site = aluno.getSite();

        if(!site.startsWith("http://")){
            site = "http://" + site;
        }

        intentSite.setData(Uri.parse(site));
        menuSite.setIntent(intentSite);
    }

    private void configuraMenuEndereco(ContextMenu menu, Aluno aluno) {
        MenuItem menuEndereco = menu.add("Exibir endereço");
        Intent intentEndereco = new Intent(Intent.ACTION_VIEW);
        intentEndereco.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        menuEndereco.setIntent(intentEndereco);
    }
}
