package br.com.mobilidade.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.mobilidade.agenda.dao.AlunoDAO;
import br.com.mobilidade.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 1;
    FormularioHelper formularioHelper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formularioHelper = new FormularioHelper(this);
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if(null != aluno){
            formularioHelper.preencheFormulario(aluno);
        }

        // Ação de click do botão de foto
        Button btnFoto = (Button) findViewById(R.id.formulario_botao_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, REQUEST_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_CAMERA:
                    formularioHelper.carregaImagem(caminhoFoto);
                    break;
            }
        }
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
