package io.dojogeek.adminibot.enums;


public enum BankEnum {

    BBVA_BANCOMER("Bancomer", "ic_bbva"),
    BANAMEX("Banamex", "ic_banamex"),
    BANORTE("Banorte", "ic_banorte"),
    SANTANDER("Santander", "ic_santander"),
    HSBC("HSBC", "ic_hsbc"),
    INBURSA_WALMART("Wallmart", "ic_wallmart"),
    SCOTIABANK("Scotia bank", "ic_scotia"),
    AMERICAN_EXPRESS("American Express", "ic_amex"),
//    INTERACCIONES("Interacciones", "ic_interacciones"),
    BANCO_AZTECA("Banco Azteca", "ic_banco_azteca"),
    BANREGIO("Banregio", "ic_banregio"),
//    MULTIVA("Multiva", "ic_multiva"),
//    MIFIEL("Mifiel", "ic_mifiel"),
//    COMPARTAMOS("Compartamos", "ic_compartamos"),
//    AFIRME("Afirme", "ic_afirme"),
//    VE_POR_MAS("Ve por más", "ic_ve_por_mas"),
//    INVEX("Invex", "ic_invex"),
    BANCOPPEL("Bancoppel", "ic_bancoppel"),
    BANCO_AHORRO_FAMSA("Banco ahorro famsa", "ic_baf");
//    BANSI("Bansi", "ic_bansi");

    private String mName;
    private String mImageName;

    private BankEnum(String name, String imageName) {
        mName = name;
        mImageName = imageName;
    }

    public String getName() {
        return mName;
    }

    public String getImageName() {
        return mImageName;
    }
}
