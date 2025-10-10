package enunciado.parcial.app;

import java.net.URL;

// import java.util.ArrayList;
import enunciado.parcial.menu.Menu;
import enunciado.parcial.menu.ItemMenu;

// import enunciado.parcial.services.PuestoService;
import enunciado.parcial.services.EmpleadosServices;

public class App {
    public static void main(String[] args) {
        
        // inicializar context global de la app como KEY VALUE, STRING: OBJECT
        AppContext context = AppContext.getInstance();

        // reemplaza T por AppContext como variable que recibe dinammicamente
        Menu<AppContext> menu = new Menu<>();
        
        // menu.setTitulo("Menu de Opciones para Museo"); // capaz agregar atributo
        URL folderPath = App.class.getResource("/resources/data");
        context.put("path", folderPath);
        context.registerService(EmpleadosServices.class, new EmpleadosServices());
        // context.registerService(EstiloArtisticoService.class, new EstiloArtisticoService());

        Actions actions = new Actions();
        menu.agregarOpcion(1, new ItemMenu<>(
            "Cargar empleados desde CSV", 
            actions::importarEmpleados
        ));

        menu.runMenu(context);
    }
}

