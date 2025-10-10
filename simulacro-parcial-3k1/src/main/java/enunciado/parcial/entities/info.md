# Explicacion de que es cada clase y que se guarda en la carpeta entities
Este proyecto contiene tres clases principales que representan entidades en un sistema de gestión de empleados y departamentos. Estas clases están mapeadas a tablas en una base de datos relacional utilizando JPA (Java Persistence API). A continuación se describe cada clase y su propósito:

Primero quiero que entendamos que se guarda dentro de la carpeta entities:
    - Se guardan las clases del modelo de dominio 
    - Representan las tablas de la base de datos en forma de objetos JAVA
    - Estan anotadas con JPA (Java Persistence API) para definir las relaciones y mapeos con la base de datos
    - 

1. **Empleado**: 
    Esta clase representa a un empleado en la empresa. Contiene atributos como `id`, `nombre`, `salario`, `empleadoFijo` (indica si el empleado es fijo o temporal), y una referencia a su `Departamento` y `Puesto`. 
    La clase está anotada con `@Entity` para indicar que es una entidad JPA y `@Table(name = "empleados")` para mapearla a la tabla "empleados" en la BD. Incluye métodos getter y setter para cada atributo, así como un método adicional `calcularSalarioFinal()` que calcula el salario final del empleado considerando si es fijo o no.

2. **Departamento**:
    Esta clase representa un departamento dentro de la empresa. Tiene atributos como `id`, `nombre` y una lista de `Empleado` que pertenecen a ese departamento. 
    Está anotada con `@Entity` y `@Table(name = "departamentos")` para mapearla a la tabla "departamentos". La relación entre `Departamento` y `Empleado` es de uno a muchos, lo que significa que un departamento puede tener múltiples empleados. Esto se maneja con la anotación `@OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)`.

3. **Puesto**:
    Esta clase representa un puesto de trabajo dentro de la empresa. Contiene atributos como `id` y `nombre`.
    Está anotada con `@Entity` y `@Table(name = "puestos")`