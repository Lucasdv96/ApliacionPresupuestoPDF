# MR Cerramientos - App de Presupuestos

App móvil para generar presupuestos técnicos y PDFs para clientes de MR Cerramientos.

## Características

✓ Crear presupuestos con datos del cliente  
✓ Agregar múltiples items técnicos (ventanas, puertas, etc.)  
✓ Especificar medidas, materiales y cantidades  
✓ Editar términos y condiciones  
✓ Generar PDF profesional para compartir  
✓ Historial de presupuestos guardados  

## Stack Técnico

- **Kotlin** con Jetpack Compose (UI moderna)
- **Room Database** (almacenamiento local)
- **iText PDF** (generación de PDFs)
- **MVVM + Coroutines** (arquitectura limpia)

## Estructura del Proyecto

```
app/src/main/kotlin/com/mrcerramiento/presupuesto/
├── data/
│   ├── local/           # Room Database, DAO
│   ├── models/          # Data classes
│   └── BudgetRepository.kt
├── ui/
│   ├── screens/         # Pantallas principales
│   ├── components/      # Componentes reutilizables
│   └── theme/           # Colores, tipografía
├── util/
│   └── PdfGenerator.kt  # Generador de PDFs
└── MainActivity.kt      # Navegación principal
```

## Desarrollo

### Requisitos
- Android Studio (Flamingo o superior)
- JDK 11+
- Android SDK 26+

### Construir
```bash
./gradlew build
```

### Ejecutar
```bash
./gradlew assembleDebug
```

## Cambios Recientes

- Estructura inicial del proyecto
- Modelos de datos (Budget, BudgetItem)
- Base de datos con Room
- Pantallas principales (Home, Create, Detail, EditContract)
- Generador de PDF
- Navegación con Compose Navigation
