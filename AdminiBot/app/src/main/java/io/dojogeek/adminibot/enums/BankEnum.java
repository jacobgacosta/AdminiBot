package io.dojogeek.adminibot.enums;


import io.dojogeek.adminibot.R;

public enum BankEnum {

    BBVA_BANCOMER(R.string.banks_bancomer, "ic_bbva"),
    BANAMEX(R.string.banks_banamex, "ic_banamex"),
    BANORTE(R.string.banks_banorte, "ic_banorte"),
    SANTANDER(R.string.banks_santander, "ic_santander"),
    HSBC(R.string.banks_hsbc, "ic_hsbc"),
    INBURSA_WALMART(R.string.banks_wallmart, "ic_wallmart"),
    SCOTIABANK(R.string.banks_scotiabank, "ic_scotia"),
    AMERICAN_EXPRESS(R.string.banks_amex, "ic_amex"),
//    INTERACCIONES("Interacciones", "ic_interacciones"),
    BANCO_AZTECA(R.string.banks_bnk_azteca, "ic_banco_azteca"),
    BANREGIO(R.string.banks_banregio, "ic_banregio"),
//    MULTIVA("Multiva", "ic_multiva"),
//    MIFIEL("Mifiel", "ic_mifiel"),
//    COMPARTAMOS("Compartamos", "ic_compartamos"),
//    AFIRME("Afirme", "ic_afirme"),
//    VE_POR_MAS("Ve por más", "ic_ve_por_mas"),
//    INVEX("Invex", "ic_invex"),
    BANCOPPEL(R.string.banks_bancoopel, "ic_bancoppel"),
    BANCO_AHORRO_FAMSA(R.string.banks_famsa, "ic_baf");
//    BANSI("Bansi", "ic_bansi");

    private int mName;
    private String mImageName;

    private BankEnum(int name, String imageName) {
        mName = name;
        mImageName = imageName;
    }

    public int getName() {
        return mName;
    }

    public String getImageName() {
        return mImageName;
    }
}
