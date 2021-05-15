package com.dam.proyecto;


import android.os.AsyncTask;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@SuppressWarnings("deprecation")
public class PublicarTweet extends AsyncTask<String, String, String> {
    private Integer puntuacion;
    private String nombre;
    public PublicarTweet(String name, int puntos) {
        if (name == null){
            this.nombre = "El jugador";
        }else{
            this.nombre = name;
        }

    this.puntuacion=puntos;
    }

    @Override  protected void onPreExecute() {

    }
    @Override  protected String doInBackground(String... params) {
        AccessToken a = new AccessToken("1371898694505750534-cGQIU4wG11WfXh2RYkAiIIInwa52Kn","VYo6Y6hylhhTPPlU2dLIgO6L1g1qtCqcbuUCr2x3jXV5M");
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("DYS3bSM78uuN1gRZElDQPHXXZ", "nz8OOCTgYycOkxMs6BjuTi0bzMohR9hZfnm6XmW5rTBO41F7eL");
        twitter.setOAuthAccessToken(a);

        try {
            String emoji =  getEmojiByUnicode(127881) ;
            String status= nombre + " ha conseguido "+puntuacion.toString() +" puntos " + emoji+emoji;
            //This method calls https://api.twitter.com/1.1/statuses/update
            twitter.updateStatus(status);

        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    @Override   protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation            . progressDialog.dismiss();

    }
    @Override  protected void onProgressUpdate(String... text) {

    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}