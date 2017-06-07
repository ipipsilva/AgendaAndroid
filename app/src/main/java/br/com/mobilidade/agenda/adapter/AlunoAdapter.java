package br.com.mobilidade.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mobilidade.agenda.R;
import br.com.mobilidade.agenda.modelo.Aluno;

/**
 * Created by Igor Silva on 03/06/2017.
 */

public class AlunoAdapter extends BaseAdapter {

    private final Context context;
    private final List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Aluno getItem(int position) {
        return this.alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = this.alunos.get(position);

        View view = convertView;

        if(null == view){
            LayoutInflater inflater = LayoutInflater.from(context);
            view  = inflater.inflate(R.layout.item_lista, parent, false);
        }

        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.lista_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if(null != caminhoFoto){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = bitmap.createScaledBitmap(bitmap, 100, 100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
