package com.example.kryptonote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.CipherSpi;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onEncrypt(View v)
    {
        try
        {
            String key = ((TextView) findViewById(R.id.key)).getText().toString();
            Cipher cipher = new Cipher();
            String result = cipher.encrypt(key);
            ((TextView) findViewById(R.id.data)).setText(result);
        }
        catch(Exception e)
        {
            Toast.makeText(this, "nope", Toast.LENGTH_LONG);
        }
    }

    public void onDecrypt(View v)
    {

    }

    public void onSave(View v)
    {
        try
        {
            String name = ((EditText) findViewById(R.id.file)).getText().toString();
            File dir = this.getFilesDir();
            File file = new File(dir, name);
            FileWriter fw = new FileWriter(file);
            fw.write(((EditText) findViewById(R.id.data)).getText().toString());
            fw.close();
            Toast.makeText(this, "Note Saved.", Toast.LENGTH_LONG);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    public void onLoad(View v)
    {

    }



    public class Cipher {
        private String key = "1234";
        public static final String ALPHABET = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        private String makePad(String note)
        {
            String pad =  this.key;
            for (; pad.length() < note.length(); )
            {
                pad += this.key;
            }
            return pad;
        }

        public String encrypt(String note)
        {
            String pad = makePad(note);
            String result = "";
            for (int i = 0; i < note.length(); i++)
            {
                String c = note.substring(i, i + 1);
                int position = ALPHABET.indexOf(c);
                int shift = Integer.parseInt(pad.substring(i, i + 1 ));
                int newPosition = (position + shift) % ALPHABET.length();
                result = result + ALPHABET.substring(newPosition, newPosition + 1);
            }
            return result;
        }

        public String decrypt(String note)
        {
            String pad = makePad(note);
            String result = "";
            for (int i = 0; i < note.length(); i++)
            {
                String c = note.substring(i, i + 1);
                int position = ALPHABET.indexOf(c);
                int shift = Integer.parseInt(pad.substring(i, i + 1 ));
                int newPosition = (position - shift) % ALPHABET.length();
                result = result + ALPHABET.substring(newPosition, newPosition + 1);
            }
            return result;
        }

    }
}
