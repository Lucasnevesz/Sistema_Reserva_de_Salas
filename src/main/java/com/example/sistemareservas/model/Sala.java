package com.example.sistemareservas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "salas")
public class Sala {

    @Id
    private String id;
    private String nome;
    private int capacidade;
    private List<String> recursos;
    private String status;
    private boolean ativa;

    public Sala() {}
    
    private Sala(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.capacidade = builder.capacidade;
        this.recursos = builder.recursos;
        this.status = builder.status;
        this.ativa = builder.ativa;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public List<String> getRecursos() {
        return recursos;
    }

    public String getStatus() {
        return status;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public void setRecursos(List<String> recursos) {
        this.recursos = recursos;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Builder {
        private String id;
        private String nome;
        private int capacidade;
        private List<String> recursos = new ArrayList<>();
        private String status = "A";
        private boolean ativa = true;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder capacidade(int capacidade) {
            this.capacidade = capacidade;
            return this;
        }

        public Builder recursos(List<String> recursos) {
            this.recursos = recursos;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder ativa(boolean ativa) {
            this.ativa = ativa;
            return this;
        }

        public Sala build() {
            return new Sala(this);
        }
    }
}
