# Desafío Práctico 10% — Desarrollo de Software para Móvil

Universidad Don Bosco — Escuela de Ingeniería en Computación

Aplicación Android (Kotlin) con 3 ejercicios accesibles desde un menú principal.

## Tecnologías
Kotlin · Android SDK (compileSdk/targetSdk 37, minSdk 24) · ConstraintLayout / GridLayout

## Estructura
```
app/
 ├── MainActivity.kt          → Menú de navegación
 ├── PromedioActivity.kt      → Ejercicio 1
 ├── SalarioActivity.kt       → Ejercicio 2
 ├── CalculadoraActivity.kt   → Ejercicio 3
 ├── utils/Calculos.kt        → Funciones de cálculo
 res/values/                  → strings.xml, colors.xml, dimens.xml
 res/drawable/                → Íconos de la calculadora
```

## Ejercicio 1: Promedio del Estudiante
Nombre + 5 notas ponderadas (25/25/20/15/15%). Valida rango 0–10. Calcula promedio con `DecimalFormat` (2 decimales), indica aprobado/reprobado (≥6.0) y envía una notificación con el resultado.

## Ejercicio 2: Descuentos al Salario
Nombre + salario base. Valida positivo (si no, `setError()` + vibración). Calcula:
- **AFP**: 7.25%
- **ISSS**: 3%, con tope de cotización de $1,000 (descuento máx. $30)
- **Renta**: sobre renta imponible (salario − AFP − ISSS), según tabla oficial:

| Tramo | Desde | Hasta | % | Exceso de | Cuota fija |
|---|---|---|---|---|---|
| I | $0.01 | $472.00 | Sin retención | — | — |
| II | $472.01 | $895.24 | 10% | $472.00 | $17.67 |
| III | $895.25 | $2,038.10 | 20% | $895.24 | $60.00 |
| IV | $2,038.11 | En adelante | 30% | $2,038.10 | $288.57 |

Muestra bruto (verde) y neto (rojo) diferenciados.

## Ejercicio 3: Calculadora Básica
Suma, resta, multiplicación, división, exponente y raíz cuadrada con botones de íconos. Valida división entre cero y raíz de negativos. Guarda historial en almacenamiento interno (`openFileOutput`), visible en un diálogo.

## Permisos
- `POST_NOTIFICATIONS` — notificación del promedio (Ej. 1)
- `VIBRATE` — vibración en salario inválido (Ej. 2)

## Ejecutar
1. `git clone https://github.com/<usuario>/Desafio1SS233210.git`
2. Abrir en Android Studio, sincronizar Gradle.
3. Ejecutar en emulador/dispositivo con Android 7.0 (API 24)+.

## Generar APK
Build → Generate Signed Bundle / APK → APK.

## Autor
Proyecto individual — Desafío Práctico 10%, Desarrollo de Software para Móvil, UDB.
