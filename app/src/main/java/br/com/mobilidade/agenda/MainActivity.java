package br.com.mobilidade.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] listaAlunos = {"Igos Silva", "Danielle Fernandes", "Arthur", "Rafael"};
        ListView lista = (ListView) findViewById(R.id.lista_alunos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, listaAlunos);

        lista.setAdapter(adapter);

        Button btnNovoAluno = (Button) findViewById(R.id.lista_btnNovoAluno);

        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentNovoAluno = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intentNovoAluno);
            }
        });
    }
}
