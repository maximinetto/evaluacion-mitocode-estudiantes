# evaluacion-mitocode-estudiantes
_Trabajo de evaluación de certificación en spring reactivo con webflux_
_Un backend con tecnología webflux para el desarrollo de API reactivas
_Incluye protección con token JWT_

### Pre-requisitos 📋
_Mongo DB versión 4 o superior._

### Instalación 🔧
_Se debe configurar el application properties y agregar_
```
jjwt.secret="aqui un llave secreta HMAC256, HMAC512 (sin commillas)"
```
y automáticamente asigna la fuerza del cifrado
```
jjwt.expirationTime="segundos de duración del token (sin commillas)"
```
Opcional: se puede cambiar el param del id de los endpoints que se obtengan un recurso por su ID
```
id.name="nombre del id (sin commillas)"
```

## Construido con
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Spring](https://spring.io/projects/spring-framework) - Framework de desarrollo web

## Documentación API 📖

Puedes encontrar mucho más de la documentación de la API en 
[Drive](https://drive.google.com/drive/folders/1BIg_YAv7jqNDfrJtKUCmv-sPfQT51G10)

## Autor
* **Maximiliano Minetto** - *Trabajo y documentación* - [maximinetto](https://github.com/maximinetto)✒️
