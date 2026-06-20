# Add project specific ProGuard rules here.
# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Hilt
-keepclasseswithmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Media3
-keep class androidx.media3.** { *; }

# Kotlin metadata
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }
