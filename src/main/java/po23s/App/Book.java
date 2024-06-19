package po23s.App;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Book extends ClienteHttp {
    private String url;
    private JSONArray itens;

    public Book(String url) {
        this.url = url;
        inicializaDados();
    }

    private void inicializaDados() {
        try {
            String dados = buscaDados(url);
            if (dados != null) {
                JSONObject infos = new JSONObject(dados);
                itens = infos.optJSONArray("items");
            } else {
                System.out.println("Dados da API não foram obtidos.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar dados do livro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<String> getMainInfos(){
        ArrayList<String> mainInfos = new ArrayList<>();
       
        if (itens != null) {
            for (int i = 0; i < itens.length(); i++) {
                JSONObject item = itens.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String titulo = volumeInfo.optString("title", "Não informado");
                JSONArray listaAutores = volumeInfo.optJSONArray("authors");
                String autores;

                if (listaAutores != null && listaAutores.length() > 0){
                    StringBuilder autoresBuilder = new StringBuilder();
                    for (int j = 0; j < listaAutores.length(); j++){
                        if(j > 0){
                            autoresBuilder.append(", ");
                        }
                        autoresBuilder.append(listaAutores.getString(j));
                    }
                    autores = autoresBuilder.toString();
                }else{
                    autores = "Não informado";
                }

                String strmainInfos = titulo + " - " + autores.toString();
                mainInfos.add(strmainInfos);
                
            }
        }
        return mainInfos;
    }

    
    private String getPreco(JSONObject infos){
        if (infos != null){
            String disponivel = infos.optString("saleability");
            if(disponivel.equals("FOR_SALE")){
                JSONObject listaPreco = infos.getJSONObject("listPrice");
                String valor = listaPreco.optString("amount");
                
                return valor;
            }else{
                return "0";
            }
        }
        return "0";

    }

    public void informaDados() {
        if (itens != null) {
            for (int i = 0; i < itens.length(); i++) {
                JSONObject item = itens.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String titulo = volumeInfo.optString("title", "Não informado");
                
                JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                String authors = (authorsArray != null && authorsArray.length() > 0) ? authorsArray.join(", ") : "Não informado";
              
                String editores = volumeInfo.optString("publisher", "Não informado");

                JSONObject tipoAcesso = item.optJSONObject("accessInfo");
                String pdf;
                if (tipoAcesso != null && tipoAcesso.has("pdf")){
                    pdf = "True";
                }else{
                    pdf = "False";
                }

                JSONObject infoValor = item.optJSONObject("saleInfo");
                String valor = getPreco(infoValor);
                              
                System.out.println("Titulos: " + titulo);
                System.out.println("Authors: " + authors);
                System.out.println("Editora: " + editores);
                System.out.println("Pdf: " + pdf);
                if (valor == "0"){
                    System.out.println("Indisponivel para venda");
                }else{
                    System.out.printf("Valor: %.2f", Float.parseFloat(valor));
                }
                
                System.out.println();

            }
        } else {
            System.out.println("Nenhum item encontrado.");
        }
    }
}
