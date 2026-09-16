# Sistema de Gestión de Planilla

Este proyecto consiste en una aplicación de escritorio desarrollada en Java para administrar la información de los empleados de una organización.

El sistema permite registrar los datos personales de cada empleado, asignarle diferentes bonos y deducciones, y calcular automáticamente su salario bruto y salario neto.

Fue desarrollado como parte del Examen I del curso Programación III, correspondiente al segundo ciclo de 2026.

## Funciones del sistema

La aplicación permite realizar las siguientes acciones:

* Cargar los empleados, bonos y deducciones desde un archivo XML.
* Guardar automáticamente los cambios realizados.
* Registrar nuevos empleados.
* Consultar la lista de empleados registrados.
* Seleccionar un empleado desde la tabla.
* Modificar la información de un empleado.
* Asociar bonos a cada empleado.
* Asociar deducciones a cada empleado.
* Eliminar bonos y deducciones seleccionados.
* Calcular el salario bruto de cada empleado.
* Calcular el salario neto de cada empleado.
* Limpiar los campos del formulario.
* Validar la información antes de guardarla.

## Información de los empleados

Para registrar un empleado se solicita la siguiente información:

* Cédula.
* Nombre completo.
* Número de teléfono.
* Correo electrónico.
* Salario base.
* Bonos aplicables.
* Deducciones aplicables.

Los empleados registrados se muestran en una tabla que contiene:

* Cédula.
* Nombre.
* Teléfono.
* Correo.
* Salario base.
* Salario bruto.
* Salario neto.

## Cálculo de salarios

El salario bruto se obtiene sumando los bonos al salario base del empleado:

```text
Salario bruto = salario base + bonos
```

El salario neto se obtiene restando las deducciones al salario bruto:

```text
Salario neto = salario bruto - deducciones
```

Los bonos y las deducciones pueden ser de dos tipos:

* Porcentuales: se calculan utilizando un porcentaje del salario.
* Fijos: representan una cantidad específica de dinero.

## Bonos incluidos

El sistema contiene los siguientes bonos:

* Dedicación exclusiva: 35 %.
* Grado de Maestría: ₡20 000.
* Grado de Doctorado: ₡30 000.

## Deducciones incluidas

El sistema contiene las siguientes deducciones:

* Seguro de salud: 5,5 %.
* Seguro de vida del Magisterio: ₡19 970.
* Régimen de pensiones: 4,33 %.

## Validaciones

Antes de guardar o modificar un empleado, el programa verifica lo siguiente:

* La cédula no puede estar vacía.
* El nombre no puede estar vacío.
* El teléfono no puede estar vacío.
* El correo electrónico debe contener `@`.
* El salario base no puede ser negativo.
* No pueden existir dos empleados con la misma cédula.
* Para modificar un empleado primero debe seleccionarse desde la tabla.

Cuando se encuentra un error, el sistema muestra un mensaje y evita que la información incorrecta sea almacenada.

## Persistencia de datos

La información se guarda en el archivo:

```text
data.xml
```

Este archivo almacena:

* Los empleados registrados.
* El catálogo de bonos.
* El catálogo de deducciones.
* Los bonos asociados con cada empleado.
* Las deducciones asociadas con cada empleado.

Los datos se cargan cuando inicia la aplicación. Los cambios se guardan después de agregar o modificar un empleado y también cuando se cierra el programa correctamente.

Es importante ejecutar la aplicación desde la carpeta principal del proyecto para que pueda encontrar el archivo `data.xml`.

## Arquitectura del proyecto

El programa utiliza una arquitectura por capas y el patrón Modelo-Vista-Controlador.

### Modelo

El modelo contiene la información que utiliza la interfaz y notifica los cambios mediante `PropertyChangeSupport`.

### Vista

La vista contiene la interfaz gráfica creada con Java Swing. Se encarga de recibir la información del usuario, mostrar los empleados y actualizar las tablas.

### Controlador

El controlador comunica la vista con la lógica del sistema. Recibe las acciones realizadas por el usuario y solicita al servicio que agregue o modifique los datos.

### Lógica

La capa lógica contiene las entidades, los cálculos salariales, las validaciones y las operaciones relacionadas con los empleados.

### Datos

La capa de datos carga y almacena la información en formato XML mediante JAXB.

## Estructura del proyecto

```text
src/main/java/system/
├── Application.java
├── data/
│   ├── Data.java
│   └── XMLPersister.java
├── logic/
│   ├── Service.java
│   └── entities/
│       ├── Empleado.java
│       ├── Bonos.java
│       └── Deducciones.java
└── presentation/
    ├── AbstractModel.java
    ├── AbstractTableModel.java
    └── board/
        ├── Controller.java
        ├── Model.java
        ├── View.java
        ├── View.form
        ├── EmpleadoTableModel.java
        ├── BonoTableModel.java
        └── DeduccionTableModel.java
```

## Tecnologías utilizadas

Para desarrollar el proyecto se utilizaron las siguientes tecnologías:

* Java 21.
* Java Swing.
* IntelliJ IDEA.
* IntelliJ IDEA GUI Designer.
* Maven.
* Jakarta XML Binding.
* JAXB Runtime.
* Archivos XML.
* Patrón Modelo-Vista-Controlador.
* PropertyChangeSupport y PropertyChangeListener.

## Requisitos

Para ejecutar el sistema se necesita:

* Java JDK 21 o una versión superior.
* Maven.
* IntelliJ IDEA, recomendado para trabajar correctamente con el archivo `View.form`.

## Cómo ejecutar el proyecto

Primero se debe clonar el repositorio:

```bash
git clone https://github.com/itswarnerg2007/Examen1-Progra3.git
```

Después se deben seguir estos pasos:

1. Abrir IntelliJ IDEA.
2. Seleccionar la opción para abrir un proyecto.
3. Escoger la carpeta `Examen1ICICLO2026`.
4. Esperar a que Maven descargue las dependencias.
5. Comprobar que el proyecto utilice Java 21.
6. Buscar la clase `Application.java`.
7. Ejecutar su método `main`.

La clase principal se encuentra en:

```text
src/main/java/system/Application.java
```

También se puede compilar desde la terminal con el siguiente comando:

```bash
mvn clean compile
```

## Cómo utilizar el sistema

1. Escribir los datos del empleado.
2. Seleccionar un bono de la lista.
3. Presionar el botón **Agregar** ubicado junto a los bonos.
4. Seleccionar una deducción.
5. Presionar el botón **Agregar** ubicado junto a las deducciones.
6. Presionar el botón **Agregar** de la sección del empleado.
7. Comprobar que el empleado aparezca en la tabla.

Para modificar un empleado:

1. Seleccionar al empleado desde la tabla.
2. Esperar a que sus datos aparezcan en el formulario.
3. Modificar la información necesaria.
4. Presionar el botón **Modificar**.

El botón **Limpiar** permite borrar la información mostrada en el formulario y preparar la interfaz para un nuevo registro.

## Autor

**Warner Guevara Cárdenas**

Estudiante de la Universidad Nacional de Costa Rica.

## Uso académico

Este proyecto fue desarrollado con fines académicos para el curso Programación III de la Universidad Nacional de Costa Rica.
