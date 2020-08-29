package br.pro.ednilsonrossi.meupocket.utils;

public class UrlUtils {

    public static String corrigeEndereco(String endereco) {
        String url = endereco.trim().replace(" ","");
        if (!url.startsWith("http://")) {
            return "http://" + url;
        }
        return url;
    }

}
