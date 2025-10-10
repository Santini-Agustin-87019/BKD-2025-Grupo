# Explicacion de que se guarda en la carpeta repositories
- Se guardan las clases encargadas de la persistencia de datos (CRUD) de las entidades del sistema. Esto quiere decir que estas clases permiten realizar operaciones de creación, lectura, actualización y eliminación de registros en la base de datos.

- Hacen la conexion entre las entidades (clases del modelo de dominio) y la base de datos, utilizando JPA  (Java Persistence API) para mapear las clases a tablas y gestionar las relaciones entre ellas.

- Usan JPA/Hibernate para ejecutar operaciones CRUD y consultas personalizadas en la base de datos.

- Los repositorios son el puente directo a la base de datos.

1. **/context/DbContext.java**
    Es una aplicacion del tipo Singleton que nos facilitara el uso de un unico EntityManager en toda la aplicacion.
    - El EntityManager es el componente principal de JPA que se encarga de gestionar las operaciones de persistencia y las transacciones con la base de datos. (CRUD, Querys, transacciones, etc)
2. **/repositories/Repository.java**
    Es la clase Padre que agrupara las operaciones CRUD y/o metodos comunes a todos los repositorios.
