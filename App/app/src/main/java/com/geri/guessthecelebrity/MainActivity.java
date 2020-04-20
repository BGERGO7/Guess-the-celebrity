package com.geri.guessthecelebrity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    int goodAnswer;
    String html;
    boolean theGoodAnswer;

    public void click1(View view){
        if(goodAnswer == 1){
            Toast.makeText(this, "Good Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }else{
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }
    }

    public void click2(View view){
        if(goodAnswer == 2){
            Toast.makeText(this, "Good Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }else{
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }
    }
    public void click3(View view){
        if(goodAnswer == 3){
            Toast.makeText(this, "Good Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }else{
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }
    }
    public void click4(View view){
        if(goodAnswer == 4){
            Toast.makeText(this, "Good Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }else{
            Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            Game();
        }
    }

    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void Game(){
        Pattern pattern = Pattern.compile("img src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(html);

        ArrayList<String> pictures = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> picturesAndNames = new ArrayList<>();

        pictures.clear();
        names.clear();
        picturesAndNames.clear();

        while (matcher.find()){
            Log.i("Pictures" , matcher.group(1));
            pictures.add(matcher.group(1));
        }


        pattern = Pattern.compile("alt=\"(.*?)\"");
        matcher = pattern.matcher(html);
        while (matcher.find()){
            Log.i("Names", matcher.group(1));
            names.add(matcher.group(1));
        }

        for(int i = 0; i < 8; i++){
            picturesAndNames.add(pictures.get(i) + " + " + names.get(i));
            Log.i("Pictures And Names", picturesAndNames.get(i));
        }

        Random random = new Random();
        int bound = random.nextInt(9);
        int buttonRand = random.nextInt(3);
        ImageDownloader task = new ImageDownloader();

        Log.i("Random", Integer.toString(bound));
        try {
            Bitmap myImage = task.execute(pictures.get(bound)).get();
            imageView.setImageBitmap(myImage);
            Log.i("Info", "Bitmap updated");
        }catch (Exception e){
            e.printStackTrace();
        }

        btn1.setText(names.get(bound));
        btn1.setTag(2);
        bound = random.nextInt(9);
        btn2.setText(names.get(bound));
        btn2.setTag(2);
        bound = random.nextInt(9);
        btn3.setText(names.get(bound));
        btn3.setTag(2);
        bound = random.nextInt(9);
        btn4.setText(names.get(bound));
        btn4.setTag(2);

        for(int i = 0; i < 8; i++){
            if(pictures.get(bound).equals(pictures.get(i))) if (buttonRand == 0) {
                btn1.setText(names.get(i));
                goodAnswer = 1;
                btn1.setTag(1);
            } else if (buttonRand == 1) {
                btn2.setText(names.get(i));
                goodAnswer = 2;
                btn2.setTag(1);
            } else if (buttonRand == 2) {
                btn3.setText(names.get(i));
                goodAnswer = 3;
                btn3.setTag(1);
            } else if (buttonRand == 3) {
                btn4.setText(names.get(i));
                goodAnswer = 4;
                btn4.setTag(1);
            }
        }


        while(btn1.getText().equals(btn2.getText()) || btn1.getText().equals(btn3.getText()) || btn1.getText().equals(btn4.getText())&& btn1.getTag().equals(2)){
            bound = random.nextInt(9);
            btn1.setText(names.get(bound));
        }
        while(btn2.getText().equals(btn1.getText()) || btn2.getText().equals(btn3.getText()) || btn2.getText().equals(btn4.getText())&& btn2.getTag().equals(2)){
            bound = random.nextInt(9);
            btn2.setText(names.get(bound));
        }
        while(btn3.getText().equals(btn1.getText()) || btn3.getText().equals(btn2.getText()) || btn3.getText().equals(btn4.getText())&& btn3.getTag().equals(2)){
            bound = random.nextInt(9);
            btn3.setText(names.get(bound));
        }while(btn4.getText().equals(btn1.getText()) || btn4.getText().equals(btn2.getText()) || btn4.getText().equals(btn3.getText())&& btn4.getTag().equals(2)){
            bound = random.nextInt(9);
            btn4.setText(names.get(bound));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);




        html = "<div class=\"channelList\">\n" +
                "\t\t<div class=\"channels_nav\">\n" +
                "\t<div class=\"title\">Lista:</div>\n" +
                "\t<div class=\"links\">\n" +
                "\t\t\t\t\t\t\t\t\t<p class=\"link\">Topp 100 kändisar</p>\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/kandisar/a_till_o\" class=\"link\">Kändisar A-Ö</a>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "</div>\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/adam_alsing\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/02ac24d552f2499528ea43eb61685ec7b\" alt=\"Adam Alsing\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">1</div>\n" +
                "\t\t\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t<div class=\"img pos\"></div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"value\">+7</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tAdam Alsing\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/beyonce\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/0b17fa7e4e9dbbed7430a38e4750fd21d\" alt=\"Beyonce\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">2</div>\n" +
                "\t\t\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t<div class=\"img pos\"></div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"value\">+3</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tBeyonce\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/lady_gaga\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/088110b3627723dd6f73b718905c2498f\" alt=\"Lady Gaga\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">3</div>\n" +
                "\t\t\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t<div class=\"img neg\"></div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"value\">-2</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tLady Gaga\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/kylie_jenner\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/04e3e4db7b764c66b5437de543f1c652c\" alt=\"Kylie Jenner\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">4</div>\n" +
                "\t\t\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t<div class=\"img neg\"></div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"value\">-2</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tKylie Jenner\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/kenza_zouiten\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/c/630264\" alt=\"Kenza Zouiten\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">5</div>\n" +
                "\t\t\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t<div class=\"img neg\"></div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"value\">-2</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tKenza Zouiten\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/demi_lovato\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/1869c12d87c0e82ade7ca34dbc39f23b8\" alt=\"Demi Lovato\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">6</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"value\">-</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tDemi Lovato\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/ariana_grande\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/09bd8ba96c471ecd93343b69de668399d\" alt=\"Ariana Grande\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">7</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"value\">-</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tAriana Grande\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t\t\t<div class=\"channelListEntry\">\n" +
                "\t\t\t\t<a href=\"/kenza\">\n" +
                "\t\t\t\t\t<div class=\"image\">\n" +
                "\t\t\t\t\t\t<img src=\"http://cdn.posh24.se/images/:profile/c/1393531\" alt=\"Kenza\">\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t\t\t<div class=\"info\">\n" +
                "\t\t\t\t\t\t<div class=\"status-container\">\n" +
                "\t\t\t\t\t\t\t<div class=\"position\">8</div>\n" +
                "\t\t\t\t\t\t\t \n" +
                "\t\t\t\t\t\t\t\t<div class=\"img neg\"></div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"value\">-4</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div class=\"name\">\n" +
                "\t\t\t\t\t\t\tKenza\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t</div>";


                Game();

    }
}
