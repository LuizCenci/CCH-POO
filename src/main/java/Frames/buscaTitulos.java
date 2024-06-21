package Frames;

import po23s.App.utils;
import po23s.App.Book;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;

public class buscaTitulos extends javax.swing.JFrame {
    DefaultListModel<Book> modelLstLivros = new DefaultListModel<>(); // Modelo para armazenar objetos Book
    private Book livro;

    public buscaTitulos() {
        initComponents();
        lstLivros.setModel(modelLstLivros);

        // Adiciona ListSelectionListener para capturar eventos de seleção na lista
        lstLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite seleção única
        lstLivros.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = lstLivros.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Book selectedBook = modelLstLivros.getElementAt(selectedIndex);
                        // Exibe as informações completas do livro selecionado
                        System.out.println("Informações completas do livro selecionado:");
                        System.out.println("Título: " + selectedBook.getTitulo());
                        System.out.println("Autores: " + selectedBook.getAutores());
                        System.out.println("Editora: " + selectedBook.getEditora());
                        System.out.println("PDF: " + selectedBook.isDisponivelPDF());
                        if (selectedBook.getValor().equals("0")) {
                            System.out.println("Indisponível para venda");
                        } else {
                            System.out.printf("Valor: %.2f\n", Float.parseFloat(selectedBook.getValor()));
                        }
                    }
                }
            }
        });

        // Configura um ListCellRenderer personalizado para exibir autor e título na lista
        lstLivros.setCellRenderer(new BookListCellRenderer());
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblBuscatit = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstLivros = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblBuscatit.setText("Digite um título:");

        txtTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTituloActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lstLivros);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBuscatit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuscatit)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String txtBusca = txtTitulo.getText();
        String url = utils.encoder(txtBusca);
        livro = new Book(url);
        ArrayList<Book> buscaTitulos = livro.getBooksFromAPI(); // Método para buscar os livros na API e retornar uma lista de objetos Book

        modelLstLivros.clear(); // Limpa o modelo atual
        for (Book book : buscaTitulos) {
            modelLstLivros.addElement(book); // Adiciona o objeto Book ao modelo da lista
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new buscaTitulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuscatit;
    private javax.swing.JList<Book> lstLivros; // Alterado para JList<Book>
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables

    // Classe interna para renderizar os itens da lista
    class BookListCellRenderer extends JLabel implements ListCellRenderer<Book> {
        public BookListCellRenderer() {
            setOpaque(true);
        }

        @Override
        public java.awt.Component getListCellRendererComponent(JList<? extends Book> list, Book value, int index,
                boolean isSelected, boolean cellHasFocus) {
            setText(value.getTitulo() + " - " + value.getAutores()); // Exibe título e autores na lista
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }
}
