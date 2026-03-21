package com.boleia.boleia.shared.types;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T, E> {

    private final T value;
    private final E error;
    private final boolean isOk;

    private Result(T value, E error, boolean isOk){
        this.value = value;
        this.error = error;
        this.isOk = isOk;
    }

    public static <TValue, TError> Result<TValue, TError> ok(TValue value) {
        return new Result<>(value, null, true);
    }

    public static <TValue, TError> Result<TValue, TError> error(TError error) {
        return new Result<>(null, error, false);
    }

    public boolean isOk(){
        return isOk;
    }

    public boolean isError() {
        return !isOk;
    }

    public T unwrap() throws IllegalStateException {
        if(isOk) return value;
        throw new IllegalStateException("Cannot unwrap an error result..." + error);
    }

    public E unwrapError() throws IllegalStateException {
        if(!isOk) return error;
        throw new IllegalStateException("Cannot unwrap a success result..." + value);
    }

    public T unwrapOr(T aDefault) {
        return isOk ? value : aDefault;
    }

    public T unwrapOrDefault() {
        return isOk ? value : null;
    }

    public T unwrapOrElse(Function<E, T> onError){
        return isOk ? value : onError.apply(error);
    }

    public void match(Consumer<T> onSuccess, Consumer<E> onFailure){
        if(isOk) {
            onSuccess.accept(value);
        } else {
            onFailure.accept(error);
        }
    }

    public <TNew> TNew match(Function<T, TNew> onSuccess, Function<E, TNew> onFailure) {
        return isOk ? onSuccess.apply(value) : onFailure.apply(error);
    }

    public <TNew> CompletableFuture<TNew> matchAsync(
        Function<T, CompletableFuture<TNew>> onSuccess, Function<E, TNew> onFailure) {
        return isOk
            ? onSuccess.apply(value)
            : CompletableFuture.completedFuture(onFailure.apply(error));
    }

    public Result<T, E> forEach(Consumer<T> operation) {
        if (isOk) {
        operation.accept(value);
        }
        return this;
    }

    public Result<T, E> catchError(Function<E, Result<T, E>> errorHandler) {
        return isError() ? errorHandler.apply(error) : this;
    }

    public Result<T, E> mapError(Function<E, Result<T, E>> errorHandler) {
        return isError() ? errorHandler.apply(error) : this;
    }

    public static class Unit {

        public static final Unit Value = new Unit();

        private Unit() {}
    }


}
