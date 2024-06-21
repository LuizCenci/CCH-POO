package po23s.App;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Book extends ClienteHttp {
    private String url;
    private JSONArray itens;
    private String titulo;
    private String autores;
    private String editora;
    private boolean disponivelPDF;
    private String valor;

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

    public ArrayList<Book> getBooksFromAPI() {
        ArrayList<Book> booksList = new ArrayList<>();

        if (itens != null) {
            for (int i = 0; i < itens.length(); i++) {
                JSONObject item = itens.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                titulo = volumeInfo.optString("title", "Não informado");
                JSONArray listaAutores = volumeInfo.optJSONArray("authors");

                if (listaAutores != null && listaAutores.length() > 0) {
                    StringBuilder autoresBuilder = new StringBuilder();
                    for (int j = 0; j < listaAutores.length(); j++) {
                        if (j > 0) {
                            autoresBuilder.append(", ");
                        }
                        autoresBuilder.append(listaAutores.getString(j));
                    }
                    autores = autoresBuilder.toString();
                } else {
                    autores = "Não informado";
                }

                editora = volumeInfo.optString("publisher", "Não informado");

                JSONObject tipoAcesso = item.optJSONObject("accessInfo");
                disponivelPDF = (tipoAcesso != null && tipoAcesso.has("pdf"));

                JSONObject infoValor = item.optJSONObject("saleInfo");
                valor = getPreco(infoValor);

                // Cria um novo objeto Book com as informações obtidas
                Book book = new Book(url);
                book.setTitulo(titulo);
                book.setAutores(autores);
                book.setEditora(editora);
                book.setDisponivelPDF(disponivelPDF);
                book.setValor(valor);

                booksList.add(book);
            }
        }
        return booksList;
    }

    private String getPreco(JSONObject infos) {
        if (infos != null) {
            String disponivel = infos.optString("saleability");
            if (disponivel.equals("FOR_SALE")) {
                JSONObject listaPreco = infos.getJSONObject("listPrice");
                String valor = listaPreco.optString("amount");

                return valor;
            } else {
                return "0";
            }
        }
        return "0";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public boolean isDisponivelPDF() {
        return disponivelPDF;
    }

    public void setDisponivelPDF(boolean disponivelPDF) {
        this.disponivelPDF = disponivelPDF;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
