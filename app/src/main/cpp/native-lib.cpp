#include <jni.h>

extern "C"
JNIEXPORT int JNICALL
Java_com_cascade_nativeandroid_MainActivity_sumNumbersInC(JNIEnv *env, jobject, int length) {
    int sum = 0;
    for (int i = 0; i < length; ++i)
        sum++;
    return sum;
}