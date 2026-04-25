# ProGuard rules
-keep class com.mrcerramiento.presupuesto.** { *; }
-keep class androidx.compose.** { *; }
-keep class com.itextpdf.** { *; }

# Room
-keep class androidx.room.** { *; }

# Coroutines
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Compose
-keep interface androidx.compose.** { *; }
-keep class androidx.compose.** { *; }
