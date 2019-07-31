# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\androidStudioSdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-----------------混淆配置设定------------------------------------------------------------------------
-optimizationpasses 5                                                       #指定代码压缩级别
-dontusemixedcaseclassnames                                                 #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses                                            #指定不忽略非公共类库
-dontpreverify                                                              #不预校验，如果需要预校验，是-dontoptimize
-ignorewarnings                                                             #屏蔽警告
-verbose                                                                    #混淆时记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*    #优化

#-----------------不需要混淆第三方类库------------------------------------------------------------------
-dontwarn android.support.v4.**                                             #去掉警告
-keep class android.support.v4.** { *; }                                    #过滤android.support.v4
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-keep class org.apache.**{*;}                                               #过滤commons-httpclient-3.1.jar

# 系统类不需要混淆
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }
-keepattributes Signature                                                   #类型转换错误 使用Gson注意
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
#-----------------------如果引用了v4或者v7包------------------------------------
-dontwarn android.support.**
#--------打包安装好运行闪退-------------------------
-dontwarn org.xmlpull.v1.XmlPullParser
-dontwarn org.xmlpull.v1.XmlSerializer
-keep class org.xmlpull.v1.* {*;}
-keepattributes *Annotation*
#-keepattributes Signature
-keep class **.R$* { *; }

#  Jpush不需要混淆
-dontwarn cn.jpush**
-keep class cn.jpush.** { *; }#Jpush


# 自定义控件不需要混淆
-keep class com.yourong.zhongchou.widget.** {*;}#CustomView


# volley工具不需要混淆
-dontwarn com.android.volley.toolbox**
-keep class com.android.volley.toolbox{*;}

# gson工具不需要混淆
-dontwarn com.google.gson**
-keep class com.google.gson.**{*;}

#eventbus混淆报错
  -keep class de.greenrobot.event.** {*;}
  -keepclassmembers class ** {
      public void onEvent*(**);
      void onEvent*(**);
  }
# 铜盾
-dontwarn android.os.**
-dontwarn com.android.internal.**
-keep class cn.tongdun.android.**{*;}

-dontwarn org.apache.http**
-keep class org.apache.http.**{*;}
#-------------------------------------

# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
 # 实体类不混淆
-dontwarn com.nongjinsuo.mimijinfu.beans**
-keep class com.nongjinsuo.mimijinfu.beans.**{*;}
-dontwarn com.nongjinsuo.mimijinfu.httpmodel**
-keep class com.nongjinsuo.mimijinfu.httpmodel.**{*;}
-dontwarn com.nongjinsuo.mimijinfu.colorful**
-keep class com.nongjinsuo.mimijinfu.colorful.**{*;}


-printmapping mapping.txt #混淆后文件映射

-keep public class com.nongjinsuo.mimijinfu.R$*{
    public static final int *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#ShareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**

-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}