package com.dam.proyecto;


import android.os.AsyncTask;

import java.util.Random;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@SuppressWarnings("deprecation")
public class PublicarTweet extends AsyncTask<String, String, String> {
    @Override  protected void onPreExecute() {

    }
    @Override  protected String doInBackground(String... params) {
        AccessToken a = new AccessToken("1371898694505750534-cGQIU4wG11WfXh2RYkAiIIInwa52Kn","VYo6Y6hylhhTPPlU2dLIgO6L1g1qtCqcbuUCr2x3jXV5M");
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("DYS3bSM78uuN1gRZElDQPHXXZ", "nz8OOCTgYycOkxMs6BjuTi0bzMohR9hZfnm6XmW5rTBO41F7eL");
        twitter.setOAuthAccessToken(a);

        try {
            Random rand = new Random();
            int upperbound=255;
            int int_random = rand.nextInt(upperbound);
            String status= "Puntos:"+Integer.toString(int_random);
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
}