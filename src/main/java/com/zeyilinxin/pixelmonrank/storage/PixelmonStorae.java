package com.zeyilinxin.pixelmonrank.storage;

import com.zeyilinxin.pixelmonrank.PixelmonRank;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PixelmonStorae{


    protected PixelmonRank pixlemonRank;


    public PixelmonStorae(PixelmonRank pixlemonRank){
        this.pixlemonRank = pixlemonRank;
    }


    public Storage getStorage(){
        String data = this.pixlemonRank.getConfig().getString("Storage" ,"").toUpperCase();
        switch (data){
            case "MYSQL":{
                return new MysqlStorage(this.pixlemonRank);
            }
            case "SQL":{
                return new SqlStorage(this.pixlemonRank);
            }
            case "YAML":{
                return new YamlStorage(this.pixlemonRank);
            }
            default:{
                return new YamlStorage(this.pixlemonRank);
            }
        }
    }
}
