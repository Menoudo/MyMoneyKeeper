package ru.dis_ip.mymoneykeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

/**
 * Created by ilia on 14.11.17.
 */

public class SmsMonitor extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Проверям получили мы смс или еще что-то
        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0)
        {
            // собираем составные смс'ки
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }

            // получаем отправителя
            String sms_from = messages[0].getDisplayOriginatingAddress();

            // Проверям от кого сообщение
            if (sms_from.equalsIgnoreCase("900"))
            {
                // Соберем текст
                StringBuilder bodyText = new StringBuilder();
                for (int i = 0; i < messages.length; i++)
                {
                    bodyText.append(messages[i].getMessageBody());
                }
                String body = bodyText.toString();

                // Стартуем сервис и передаем ему текст
                Intent mIntent = new Intent(context, MoneySmsParserService.class);
                mIntent.putExtra("sms_body", body);
                context.startService(mIntent);

                // Прерываем дальнейшую рассылку
                // FIXME не работает отмена рассылки
                // abortBroadcast();
            }

        }

    }
}
