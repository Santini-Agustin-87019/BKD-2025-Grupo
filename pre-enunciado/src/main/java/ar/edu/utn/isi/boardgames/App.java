package ar.edu.utn.isi.boardgames;

import ar.edu.utn.isi.boardgames.infrastructure.DataSourceProvider;
import ar.edu.utn.isi.boardgames.infrastructure.DbInitializer;
import ar.edu.utn.isi.boardgames.infrastructure.LocalEntityManagerProvider;
import ar.edu.utn.isi.boardgames.repositories.*;
import ar.edu.utn.isi.boardgames.entities.*;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

public class App {
    public static void main(String[] args) {
        System.out.println("Hola Mundo!!");

    }
}
