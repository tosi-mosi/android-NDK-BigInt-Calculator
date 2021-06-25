#include <jni.h>
#include <string>

#include "BigInt/big_int.h"
#include "BigInt/calculator.h"

#include <vector>

using calculator_t = Calculator<BigInt<unsigned int, 2048>>;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_native17_MainActivity_calculate(JNIEnv* env, jobject, jstring input){

    jclass runtimeExceptionClazz = env->FindClass("java/lang/RuntimeException");

    std::string result{""};

    // convert Java string to C++ string
    unsigned char make_copy{ false };
    const char* input_in_c_format{ env->GetStringUTFChars(input, &make_copy) };

    if(input_in_c_format == nullptr){
        // [?] should throw exception here
        if(runtimeExceptionClazz != NULL)
            env->ThrowNew(runtimeExceptionClazz, "couldn't convert Java string to C++ string");
        return NULL;
    }

    try{
        result = calculator_t::calculate(input_in_c_format);
    }catch(std::runtime_error& e){
        env->ThrowNew(runtimeExceptionClazz, e.what());
        return NULL;
    }

    env->DeleteLocalRef(runtimeExceptionClazz);
    env->ReleaseStringUTFChars(input, input_in_c_format);

    // convert C++ string to Java string
    return env->NewStringUTF(result.c_str());
}
