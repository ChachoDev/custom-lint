
# Reglas Lint Personalizadas para Clean Architecture 🚀

¡Bienvenido al repositorio de reglas Lint personalizadas! Este proyecto está diseñado para ayudar a mantener la **separación de capas** en proyectos que siguen los principios de **Clean Architecture**, pero donde las capas están organizadas en **paquetes** en lugar de módulos.

## ¿Qué hace este proyecto? 🤔

Nuestro conjunto de reglas Lint verifica que no se violen las restricciones de visibilidad entre capas. En Clean Architecture, cada capa tiene una responsabilidad clara y no debería acceder directamente a detalles de otras capas. Este proyecto garantiza que:

- La **capa de presentación** (por ejemplo, ViewModels, Activities) no acceda a la capa de **datos** (por ejemplo, Repositorios, DTOs) sin pasar por la capa de dominio.
- La **capa de dominio** se mantenga pura y libre de dependencias a otras capas.
  
Con nuestras reglas Lint, cualquier violación será detectada automáticamente durante el análisis estático del código.

## ¿Cómo funciona? 🔧

Hemos definido reglas personalizadas que Lint ejecuta durante el análisis estático. Estas reglas detectan si el código fuente está respetando la separación de capas dentro del proyecto.

Cuando Lint se ejecuta:

1. **Carga el IssueRegistry**: El núcleo de nuestras reglas.
2. **Registra nuestras reglas**.
3. **Analiza el código**: Busca violaciones de arquitectura.
4. **Genera un informe**: Señalando los errores y violaciones en formato `.txt` y `.html`.

Para ver cómo se configuran las reglas y se utilizan, revisa el artículo detallado que se encuentra en este repositorio: [Reglas Lint para Clean Architecture](https://www.google.es).

## Instalación ⚙️

1. Clona este repositorio en tu proyecto local.
   
   ```bash
   git clone https://github.com/usuario/proyecto-lint.git
   ```
   
2. Añade la dependencia de las reglas Lint a los módulos donde quieras aplicarlas, editando el `build.gradle` de cada uno de ellos:

   ```groovy
   dependencies {
       lintChecks(project(":custom_lint"))
   }
   ```

## Ejecución de Lint 🔍

Para analizar el proyecto manualmente, simplemente ejecuta el siguiente comando en la terminal de **Android Studio**:

```bash
./gradlew lint
```

Lint se encargará de revisar el código y te notificará si alguna de nuestras reglas ha sido violada.

## Forzando un Error para Prueba 🛠️

Si quieres probar cómo funciona una de nuestras reglas y ver cómo Lint te avisa de las violaciones, puedes hacerlo fácilmente:

1. Dirígete al archivo `MainViewModel`.
2. Dentro del método `init`, crea una instancia de `UserDto` (que pertenece a la capa de datos).
   
   Al hacerlo, como el `ViewModel` pertenece a la capa de presentación y `UserDto` a la capa de datos, nuestras reglas lanzarán un error indicando que estás rompiendo la separación de capas.

## Mejoras 🔄

Sabemos que ejecutar `./gradlew lint` desde la terminal una y otra vez no es lo más práctico del mundo. No te preocupes, existen formas de **automatizar el proceso**, como:

- Ejecutar Lint cada vez que haces un commit.
- Integrarlo directamente en el proceso de compilación.

Hablaremos de todo esto en un próximo artículo, así que ¡mantente al tanto! 😉

## Contribuciones 👥

Si tienes ideas para mejorar las reglas o quieres colaborar, ¡siéntete libre de hacer un fork y enviar un pull request! Todos los aportes son bienvenidos.

## Licencia 📄

Este proyecto está bajo la licencia [MIT](./LICENSE).

---

¡Que la fuerza del Pollo Campero te acompañe! 🐔
