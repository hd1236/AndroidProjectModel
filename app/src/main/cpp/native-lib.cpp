#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_hand_android_nativelib_NativeStringlib_stringFromJNI(
        JNIEnv* env, jclass type) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
