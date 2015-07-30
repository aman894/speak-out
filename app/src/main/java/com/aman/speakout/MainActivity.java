package com.aman.speakout;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,TextToSpeech.OnInitListener{
    EditText etEnteredWord;
    Button bSpeak;
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent,MY_DATA_CHECK_CODE);
        bSpeak.setOnClickListener(this);
        //myTTS.shutdown();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MY_DATA_CHECK_CODE){
            if(resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                myTTS = new TextToSpeech(this,this);
            }
            else{
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getWord(){
        etEnteredWord = (EditText)findViewById(R.id.etEnteredWord);
        return etEnteredWord.getText().toString();
    }
    private void initialize(){
        bSpeak = (Button)findViewById(R.id.bSpeak);
    }
    private void speakWords(String wordToSpeak){
        myTTS.speak(wordToSpeak,TextToSpeech.QUEUE_FLUSH,null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String wordToSpeak = getWord();
        speakWords(wordToSpeak);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            myTTS.setLanguage(Locale.UK);
        }
        else if(status == TextToSpeech.ERROR){
            Toast.makeText(this,"Could not speak!",Toast.LENGTH_SHORT).show();
        }
    }
}
