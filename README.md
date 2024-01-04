# Apollo-11

En el contexto de la ingeniería informática, el manejo eficiente de las operaciones en segundo plano y la multitarea es crucial, especialmente en situaciones que emulan eventos críticos en tiempo real, como el lanzamiento de un cohete. Este ejercicio está inspirado en el histórico lanzamiento del Apollo 11 el 16 de julio de 1969, el cual llevó al primer hombre a la Luna. Los estudiantes deben crear una simulación de la cuenta regresiva para el lanzamiento, enfocándose en la sincronización de hilos y la comunicación en tiempo real con la interfaz de usuario.

Enunciado del Ejercicio:

Desarrolla una aplicación que simule la cuenta regresiva para el lanzamiento del Apollo 11. La interfaz debe permitir al usuario ingresar una cantidad de segundos y mostrar una barra de progreso y una etiqueta de texto que reflejen la cuenta atrás en tiempo real.

La cuenta atrás debe iniciarse cuando el usuario pulse un botón.
La aplicación debe permitir al usuario cancelar la cuenta atrás en cualquier momento.
Al finalizar la cuenta, se debe mostrar un mensaje en una ventana emergente que indique si el lanzamiento ha sido exitoso o cancelado.

**Adicionalmente se han agregado algunas validaciones extra.

1- Por un lado no se permite el lanzamiento de la cuenta atras si el usuario no ha introducido el número de segundos para la cuenta atras.

2- Como existe la posibilidad de lanzar multiples hilos de cuenta atras y perder el control, tanto de los contadores como de la progress bar, se ha agregado una validacion que no permita lanzar una cuenta atras si ya hay iniciada una previamente. Para poder lanzar otra cuenta atras debe cancelarse la actual o esperar a que finalice.
