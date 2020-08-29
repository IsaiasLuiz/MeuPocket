package br.pro.ednilsonrossi.meupocket.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import br.pro.ednilsonrossi.meupocket.model.Site;


public class SiteDao {

    private static final String PREFERENCES_NAME = "PREFERENCES_NAME";
    private static final String TAG = "SITE_DAO";

    private static SharedPreferences sharedPreferences ;
    private static SharedPreferences.Editor editor;

    public static final List<Site> recuperateAll(Context context){
        LinkedList<Site> siteList = new LinkedList();
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, context.MODE_PRIVATE);
        try {
            String sites = sharedPreferences.getString(PREFERENCES_NAME, "");
            JSONArray jsonArray =  new JSONArray(sites);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Site s = new Site(jsonObject.getString("titulo"), jsonObject.getString("endereco"));
                if (jsonObject.getBoolean("favorite")) {
                    s.doFavotite();
                } else {
                    s.undoFavorite();
                }
                siteList.add(s);
            }
        } catch (Exception e ) {
            siteList.clear();
            Log.e(TAG,"Erro ao recuperar site", e);
        }
        return siteList;
    }

    public static final void insert(final Context context, final Site site) {
        List<Site> siteList = recuperateAll(context);
        siteList.add(site);
        insert(context, siteList);
    }

    // criei esse metodo, mas fiquei sem saber de onde chamar ele, pq é necessario um contexto
    public static void doFavorite(Context context, Site site) {
        List<Site> siteList = recuperateAll(context);
        for (Site s : siteList) {
            if(s.equals(site)) {
                s.doFavotite();
            }
        }
        insert(context, siteList);
    }

    public static void undoFavorite(Context context, Site site) {
        List<Site> siteList = recuperateAll(context);
        for (Site s : siteList) {
            if(s.equals(site)) {
                s.undoFavorite();
            }
        }
        insert(context, siteList);
    }

    private static void insert(Context context, List<Site> siteList) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        try {
            JSONArray jsonArray = new JSONArray();
            for (Site s : siteList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("titulo", s.getTitulo());
                jsonObject.put("endereco", s.getEndereco());
                jsonObject.put("favorite", s.isFavorito());
                jsonArray.put(jsonObject);
            }
            String sites = jsonArray.toString();
            editor.putString(PREFERENCES_NAME, sites);
            editor.commit();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir site", e);
        }
    }

}