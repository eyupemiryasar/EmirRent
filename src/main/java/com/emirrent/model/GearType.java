package com.emirrent.model;

//enum yaptım çünkü değerler sabit ve değişmeyecek
public enum GearType {
    AUTO("Otomatik"),
    MANUAL("Manuel");

    //değiştirilemez bi isim kaydet
    private final String displayName;

    //ismi değerle eşle
    GearType(String displayName) {
        this.displayName = displayName;
    }

    //otomatik - manuel olarak return at
    public String getDisplayName() {
        return displayName;
    }
}


