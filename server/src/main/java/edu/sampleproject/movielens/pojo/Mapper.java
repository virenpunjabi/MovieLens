package edu.sampleproject.movielens.pojo;

public interface Mapper<F, T> {
    T map(F from);
}
