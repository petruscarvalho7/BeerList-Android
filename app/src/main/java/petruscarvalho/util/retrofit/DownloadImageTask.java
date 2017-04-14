package petruscarvalho.util.retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dev01 on 20/10/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected synchronized Bitmap doInBackground(String... urls) {
        String urlImagem = urls[0];
        Bitmap mIcon11 = null;
        try {
            Log.i("Notification", "Não existe local, vai baixar a imagem");
            InputStream in = new URL(urlImagem).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            Log.i("Notification", "Baixou a imagem e salvou no banco de dados");

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Error", "Erro ao realizar download da imagem");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error", "Erro ao salvar a imagem");
        }
        return mIcon11;
    }


    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

}
