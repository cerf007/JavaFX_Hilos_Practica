# JavaFX_Hilos_Practica

Práctica guiada de **Hilos en JavaFX** — Universidad Americana (UAM).
Unidad III: Programación concurrente.

## Descripción

Aplicación de escritorio JavaFX que simula el procesamiento de registros
sin bloquear la interfaz gráfica, usando hilos (`Thread` + `Runnable`)
y actualizaciones seguras mediante `Platform.runLater()`.

## Funcionalidades

- Procesar 100 registros de clientes.
- Procesar 50 registros de productos.
- Dos hilos independientes con tiempos de ejecución diferentes.
- Dos barras de progreso con porcentaje.
- Detener cada hilo individualmente con `interrupt()`.
- Reiniciar los controles al finalizar o detener.
- Cierre seguro de los hilos al cerrar la ventana.

## Estructura del proyecto
```
JavaFX_Hilos_Practica/
└── src/main/
├── java/
│ ├── ni/edu/uam/practicahilos/javafx_hilos_practica/
│ │ ├── MainApp.java
│ │ └── ProcesadorController.java
│ └── module-info.java
└── resources/
└── ni/edu/uam/practicahilos/javafx_hilos_practica/
└── procesador-view.fxml
```

## Ejecución

1. Clonar el repositorio.
2. Abrir el proyecto en IntelliJ IDEA.
3. Configurar el JDK y JavaFX.
4. Ejecutar la clase `MainApp`.

## Conceptos aplicados

| Concepto | Uso |
|----------|-----|
| `Thread` | Hilos de clientes y productos. |
| `Runnable` | Tarea que ejecuta cada hilo. |
| `start()` | Inicia la ejecución del hilo. |
| `sleep()` | Simula el tiempo de procesamiento. |
| `interrupt()` / `isInterrupted()` | Detención controlada. |
| `Platform.runLater()` | Actualización segura de la UI. |

- Nombre: _Carlos Eduardo Rodriguez Flores_
- Docente: _José Durán García_
- Fecha: _13/10/2026_