package backend;



 public class App {
    public static void main(String[] args) {
        System.out.println("\n\nCafeteria Inteligente");
        
        Cafetera cafe = new Cafetera("La Virginia", "Molido", 500, 0, false, 0, 20);

        cafe.apagar();
        cafe.cargarAgua(200);
        cafe.encender();
        cafe.cargarAgua(300);
        cafe.calentar();
        cafe.calentar();
        cafe.servirCafe();
        cafe.servirCafe();
        cafe.servirCafe();

    }
}
