package io.dojogeek.adminibot.enums;


import io.dojogeek.adminibot.R;

public enum BankEnum {

    BBVA_BANCOMER(R.string.banks_bancomer, "ic_amex"),
    BANAMEX(R.string.banks_banamex, "ic_amex"),
    BANORTE(R.string.banks_banorte, "ic_amex"),
    SANTANDER(R.string.banks_santander, "ic_amex"),
    HSBC(R.string.banks_hsbc, "ic_amex"),
    INBURSA_WALMART(R.string.banks_wallmart, "ic_amex"),
    SCOTIABANK(R.string.banks_scotiabank, "ic_amex"),
    AMERICAN_EXPRESS(R.string.banks_amex, "ic_amex"),
//    INTERACCIONES("Interacciones", "ic_interacciones"),
    BANCO_AZTECA(R.string.banks_bnk_azteca, "ic_amex"),
    BANREGIO(R.string.banks_banregio, "ic_amex"),
//    MULTIVA("Multiva", "ic_multiva"),
//    MIFIEL("Mifiel", "ic_mifiel"),
//    COMPARTAMOS("Compartamos", "ic_compartamos"),
//    AFIRME("Afirme", "ic_afirme"),
//    VE_POR_MAS("Ve por más", "ic_ve_por_mas"),
//    INVEX("Invex", "ic_invex"),
    BANCOPPEL(R.string.banks_bancoopel, "ic_amex"),
    BANCO_AHORRO_FAMSA(R.string.banks_famsa, "ic_amex");
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
