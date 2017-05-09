package com.example.social.classes;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maxim on 09.05.2017.
 */

public class LoginAPIWorker {
    static String requestData;
    static String responseData;
    static int responseCode;

    public static boolean signInMethod(String userLogin, String userPassword)
            throws Exception {

        requestData = "login=" + userLogin + "&password=" + userPassword;

        RegistrationTask pt = new RegistrationTask();
        pt.execute();

        if (responseCode != 200) {
            return true;
        } else {
            return false;
        }
    }

    // HTTP Post request
    private static String makeRequest(String url, String requestData) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestData);
        wr.flush();
        wr.close();

        int responceCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + requestData);
        System.out.println("Response Code : " + responceCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();
        responseCode = responceCode;

        System.out.println(responseData);
        return responseData;
    }

    private static class RegistrationTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                makeRequest(Data.URL + "token", requestData);
            } catch (Exception e) {
                System.out.println("Ну пиздец");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            super.onPostExecute(res);

        }
    }
}

