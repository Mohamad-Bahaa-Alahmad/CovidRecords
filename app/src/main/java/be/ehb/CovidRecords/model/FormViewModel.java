package be.ehb.CovidRecords.model;

import android.app.Application;
import android.util.Log;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FormViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<FormPost>> formposts;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);
    private LiveData<List<FormPost>> formpostsDatabase;
    private FormPostsDatabase mDatabase;


    public FormViewModel(@NonNull Application application) {
        super(application);

        mDatabase = FormPostsDatabase.getInstance(application);
        formpostsDatabase = mDatabase.getFormPostsDAO().getAllPosts();
        formposts = new MutableLiveData<>();
    }

    public LiveData<List<FormPost>> getFormpostsDatabase() {
        return formpostsDatabase;
    }

    public void insertFormPost(FormPost n){
        FormPostsDatabase.dbExecutor.execute(()->{
            mDatabase.getFormPostsDAO().insertFormPost(n);
        });
    }

    public MutableLiveData<ArrayList<FormPost>> getFormpost() {
        ArrayList<FormPost> posts = new ArrayList<>();
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    Response mResponse = null;
                    OkHttpClient mClient = new OkHttpClient();
                    String url = "https://epistat.sciensano.be/Data/COVID19BE_CASES_MUNI_CUM.json";
                   if(URLUtil.isValidUrl(url)){
                       Request mRequest = new Request.Builder()
                               .url(url)
                               .get()
                               .build();
                        mResponse = mClient.newCall(mRequest).execute();
                   }



                    if(mResponse != null) {
                        String responseText = mResponse.body().string();
                        JSONArray postObject = new JSONArray(responseText);

                        int nObjects = postObject.length();

                        LiveData<List<FormPost>> Listtest = formpostsDatabase;

                        int i = 1;

                        while (i < nObjects) {
                            JSONObject currentPostJson = postObject.getJSONObject(i);


                            String municipality = currentPostJson.getString("TX_DESCR_NL");
                            String province = currentPostJson.getString("PROVINCE");
                            String CASES = currentPostJson.getString("CASES");



                            FormPost currentPostJava = new FormPost(
                                    municipality, province, CASES
                            );

                            if(Listtest == null){
                                insertFormPost(currentPostJava);
                            }

                            posts.add(currentPostJava);
                            i++;

                        }
                    }else {
                        for (FormPost post: formpostsDatabase.getValue()
                             ) {

                            posts.add(post);
                        }
                    }
                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                formposts.postValue(posts);
                 Log.d("Test", formposts.toString());
            }

        });

        return formposts;
    }
}
