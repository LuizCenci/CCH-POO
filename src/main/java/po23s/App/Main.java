package po23s.App;

import java.util.ArrayList;

//import javax.swing.*;
import po23s.App.utils;
public class Main {
    public static void main(String[] args) {
        String busca = "harry potter";

       // String encode = utils.encoder(busca);
        String urlBusca = "https://www.googleapis.com/books/v1/volumes?q="+"HArry-potter"+"&key=AIzaSyBFL7oexO7Z_k2smvnlYe3jRLY93m2OjuI";
        
        Book book = new Book(urlBusca);
        ArrayList<String> infos = book.getMainInfos();
        for (String info: infos){
            System.out.println(info);
        }
    }
}
