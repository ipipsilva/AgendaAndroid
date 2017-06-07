package br.com.mobilidade.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.mobilidade.agenda.modelo.Aluno;

/**
 * Created by Igor Silva on 29/05/2017.
 */

public class FormularioHelper {

    private final EditText nome;
    private final EditText endereco;
    private final EditText telefone;
    private final EditText email;
    private final EditText site;
    private final RatingBar nota;
    private final ImageView foto;

    private Aluno aluno;


    public FormularioHelper(FormularioActivity activity){
         nome = (EditText) activity.findViewById(R.id.formulario_nome);
         endereco = (EditText) activity.findViewById(R.id.formulario_endereco);
         telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
         email = (EditText) activity.findViewById(R.id.formulario_email);
         site = (EditText) activity.findViewById(R.id.formulario_site);
         nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
         foto = (ImageView) activity.findViewById(R.id.formulario_foto);
         aluno = new Aluno();
    }

    public Aluno pegaAluno(){
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setEmail(email.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setCaminhoFoto((String) foto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno){
        nome.setText(aluno.getNome());
        endereco.setText(aluno.getEndereco());
        telefone.setText(aluno.getTelefone());
        email.setText(aluno.getEmail());
        site.setText(aluno.getSite());
        nota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto){

        if(null != caminhoFoto){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap, 300, 300, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            foto.setTag(caminhoFoto);
        }

    }
}
