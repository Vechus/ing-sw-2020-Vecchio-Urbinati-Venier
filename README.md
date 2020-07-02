# Prova Finale Ingegneria del Software 2019
## Group GC18

- ###   10574063    Urbinati Daria ([@DariaUr](https://github.com/DariaUr))<br>daria.urbinati@mail.polimi.it
- ###   10565156    Vecchio Luca ([@Vechus](https://github.com/Vechus))<br>luca1.vecchio@mail.polimi.it
- ###   10617531    Venier Daniele ([@Danis98](https://github.com/Danis98))<br>daniele.venier@mail.polimi.it

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Advanced gods | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |


<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

# Coverage

![Coverage report](https://github.com/Vechus/ing-sw-2020-Vecchio-Urbinati-Venier/blob/master/photo_2020-07-02_17-20-04.jpg?raw=true)

# Building instructions
The project can be built using the following Maven command
```
mvn clean package
```
The resulting jar executable can be found in the target folder as `GC18-1.0-SNAPSHOT-jar-with-dependencies.jar`
This jar contains both the clients and the server.

# Execution instructions
Java 14 is required to run.

## Running the Server
To start the server, run the jar the following arguments
```
java -jar [jarname] --server
```

## Running the Client
The client can be run in two modes: CLI and GUI.
In both modes, you will be asked for information such as the server ip&port, your username etc.

To run the client in GUI mode, no command line argument is needed.
```
java -jar [jarname]
```
Alternatively, it can be run with a double click on the jar.

To run the client in terminal mode, use the following arguments
```
java -jar [jarname] --cli
```

# Test coverage 


