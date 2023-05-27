package com.example.projeto_gerenciador_de_encomenda.Model;

public class Encomenda {
    private String nomeMorador;
    private String Complemento;
    private String dataRecebida;
    private String status;
    private String dataEntregue;

    private String imagem;

    private String key;

    public Encomenda(String nomeMorador, String complemento, String dataRecebida, String status, String dataEntregue) {
        this.nomeMorador = nomeMorador;
        Complemento = complemento;
        this.dataRecebida = dataRecebida;
        this.status = status;
        this.dataEntregue = dataEntregue;
    }


    public Encomenda() {
    }


    public String getNomeMorador() {
        return nomeMorador;
    }

    public void setNomeMorador(String nomeMorador) {
        this.nomeMorador = nomeMorador;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getDataRecebida() {
        return dataRecebida;
    }

    public void setDataRecebida(String dataRecebida) {
        this.dataRecebida = dataRecebida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataEntregue() {
        return dataEntregue;
    }

    public void setDataEntregue(String dataEntregue) {
        this.dataEntregue = dataEntregue;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
