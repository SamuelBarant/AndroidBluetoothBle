# AndroidBluetoothBle

Proyecto práctico de DAM cuyo objetivo es establecer una comunicación **Bluetooth Low Energy (BLE)**
entre una **aplicación Android** y una **Arduino Nano 33 BLE**, permitiendo enviar comandos desde el
móvil y ejecutar acciones en el microcontrolador.

## 🧩 Descripción del proyecto

La aplicación Android actúa como **cliente BLE**, escaneando dispositivos cercanos, conectándose a
la Arduino Nano 33 BLE y escribiendo datos en una característica BLE.  
La Arduino recibe estos datos y realiza una acción concreta (por ejemplo, encender o apagar un LED).

Este proyecto sirve como introducción práctica a:

- Comunicación BLE en Android
- Uso de servicios y características GATT
- Integración Android ↔ hardware (IoT básico)

## 🎯 Objetivos

- Comprender el funcionamiento de Bluetooth Low Energy
- Aprender a usar la API BLE de Android
- Conectar una app Android con un dispositivo físico
- Enviar datos desde Android y procesarlos en Arduino
- Aplicar buenas prácticas de estructura de proyecto

## 🛠️ Tecnologías utilizadas

### Android

- Kotlin
- Android Studio
- Bluetooth Low Energy (BLE)
- API Bluetooth GATT

### Hardware

- Arduino Nano 33 BLE
- Arduino IDE
- Librería `ArduinoBLE`

## 📱 Funcionalidades

- Escaneo de dispositivos BLE cercanos
- Conexión a la Arduino Nano 33 BLE
- Envío de datos desde la app
- Recepción y procesamiento de datos en Arduino
- Control de un LED desde la aplicación

## 📂 Estructura del proyecto

