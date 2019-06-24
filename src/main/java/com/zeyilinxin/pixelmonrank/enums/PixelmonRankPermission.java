package com.zeyilinxin.pixelmonrank.enums;

public enum PixelmonRankPermission {

    ALL("pixelmonrank.*") , USE_ALL("pixelmonrank.use.*") , USE("pixelmonrank.use") , RANK("pixelmonrank.rank.use"), CLOSE("pixelmonrank.close.use") ,ADMIN_ALL("pixelmonrank.admin.*") , ADMIN("pixelmonrank.admin.use")
    ,ADMIN_SF("pixelmonrank.admin.sf") , ADMIN_AF("pixelmonrank.admin.af"), ADMIN_DF("pixelmonrank.admin.df"), ADMIN_SW("pixelmonrank.admin.sw")
    , ADMIN_AW("pixelmonrank.admin.aw"), ADMIN_DW("pixelmonrank.admin.dw") , ADMIN_SS("pixelmonrank.admin.ss") , ADMIN_AS("pixelmonrank.admin.as")
    , ADMIN_DS("pixelmonrank.admin.ds") , ADMIN_SJT("pixelmonrank.admin.sjt"), ADMIN_SWT("pixelmonrank.admin.swt") , ADMIN_STT("pixelmonrank.admin.stt")
    , INFO("pixelmonrank.info.use") , ADMIN_BM("pixelmonrank.admin.bm") , ADMIN_BAN("pixelmonrank.admin.ban") , ADMIN_REC("pixelmonrank.admin.rec")
    ,ADMIN_RECBM("pixelmonrank.admin.recbm") , ADMIN_RELOAD("pixelmonrank.admin.reload") , ADMIN_BANP("pixelmonrank.admin.banp") , ADMIN_RECP("pixelmonrank.admin.recp");;

    private String data;


    PixelmonRankPermission(String data) {
        this.data = data;
    }




    @Override
    public String toString() {
        return this.data;
    }
}
