package edu.sampleproject.movielens.pojo;

public enum Certification {
    UNIVERSAL("U"),
    UNIVERSALANDADULTS("UA"),
    ADULTS("A");

    String certificationName;

    Certification(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getCertificationName(){
        return certificationName;
    }

    public static Certification getFromName(String name){
        for(Certification certification: Certification.values()){
            if(certification.getCertificationName().equals(name))
                return certification;
        }
        return UNIVERSALANDADULTS;
    }

}
