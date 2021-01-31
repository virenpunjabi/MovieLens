package edu.sampleproject.movielens.pojo;

public enum ReviewType {
    GOOD,
    BAD,
    AVERAGE;


    public static ReviewType getFromName(String name) {
        for (ReviewType reviewType : ReviewType.values())
            if (reviewType.name().equals(name)) {
                return reviewType;
            }
        return GOOD;
    }
}

