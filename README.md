Mi Habitat

Las siguientes lineas son un sencillo instructivo para poder ejecutar la aplicacion, la cual esta separada en varios proyectos.

1.- En el proyecto se usan dependencias externas, que deberan ser instaladas manualmente en Maven, las siguientes lineas permiten la instalacion de estas dependencias.
Unicamente es necesario sustituir la palabra {path} por la ruta en donde se haya descargado el proyecto.

mvn install:install-file -Dfile=lib\\cfdi-3.2.jar -DgroupId=mx.gob.sat -DartifactId=cfdi -Dversion=3.2 -Dpackaging=jar -DgeneratedPom=true
mvn install:install-file -Dfile=lib\\timbre-cfdi-3.2.jar -DgroupId=mx.gob.sat -DartifactId=timbre-cfdi -Dversion=3.2 -Dpackaging=jar -DgeneratedPom=true
mvn install:install-file -Dfile=lib\\pandora-crypto-1.0.jar -DgroupId=com.serti.pandora -DartifactId=pandora-crypto -Dversion=1.0 -Dpackaging=jar -DgeneratedPom=true

