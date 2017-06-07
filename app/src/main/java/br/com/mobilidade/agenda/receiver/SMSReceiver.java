package br.com.mobilidade.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.Serializable;

import br.com.mobilidade.agenda.R;
import br.com.mobilidade.agenda.dao.AlunoDAO;

/**
 * Created by Igor Silva on 03/06/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO alunoDAO = new AlunoDAO(context);

        if(alunoDAO.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
    }
}
