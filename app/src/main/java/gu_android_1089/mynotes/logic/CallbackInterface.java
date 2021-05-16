package gu_android_1089.mynotes.logic;

public interface CallbackInterface<T> {
    void onSuccess (T value);
    void onError (Throwable error);
}
