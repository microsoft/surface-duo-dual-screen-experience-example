# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Bing Maps files
# Will be updated when we get more restrictive rules from the Bing Maps team
-keep class com.microsoft.maps.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.microsoft.device.samples.dualscreenexperience.data.about.model.** { <fields>; }
-keep class com.microsoft.device.samples.dualscreenexperience.data.store.model.** { <fields>; }
-keep class com.microsoft.device.samples.dualscreenexperience.data.product.model.** { <fields>; }
-keep class com.microsoft.device.samples.dualscreenexperience.data.catalog.model.** { <fields>; }