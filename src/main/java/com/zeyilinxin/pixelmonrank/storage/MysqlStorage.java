package com.zeyilinxin.pixelmonrank.storage;

import com.zeyilinxin.pixelmonrank.PixelmonRank;
import com.zeyilinxin.pixelmonrank.utils.LogPostUtils;
import org.bukkit.entity.Player;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MysqlStorage implements Storage{

    private boolean isError = false;
    private PixelmonRank pixlemonRank;

    public MysqlStorage(PixelmonRank pixlemonRank) {
        this.pixlemonRank = pixlemonRank;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        String ip = this.pixlemonRank.getConfig().getString("Mysql.Host");
        String user = this.pixlemonRank.getConfig().getString("Mysql.User");
        String password = this.pixlemonRank.getConfig().getString("Mysql.Password");
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
        }
        connection = DriverManager.getConnection(ip , user , password);
        return connection;
    }


    @Override
    public void connect() {
        Connection connection = null;
        PreparedStatement statement = null;
        LogPostUtils.postLog("选择了MYSQL连接方式");
        try {
            connection = getConnection();
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS pixelmonrank (id int(16) AUTO_INCREMENT,player VARCHAR(64) NOT NULL,fraction int(64) NOT NULL,win int(64) NOT NULL,fail int(64) NOT NULL,time VARCHAR(255) NOT NULL,uuid VARCHAR(64) NOT NULL,one VARCHAR(255) NOT NULL,wintime VARCHAR(255) NOT NULL,title VARCHAR(255) NOT NULL,PRIMARY KEY(id))");
            statement.execute();
            connection.close();
            statement.close();
            this.pixlemonRank.putInfo("MYSQL数据库初始化成功!");
        } catch (SQLException e) {
            error();
        }finally {
            try {
                if (connection != null){
                    connection.close();
                    connection = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public synchronized boolean initialization(Player player){
        java.util.Date date = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "INSERT INTO pixelmonrank VALUES ('0','" + player.getName() + "', " + "'" + this.pixlemonRank.getSettingsConfig().getFraction() + "'," + "'0'," + "'0'," + "'" + simpleDateFormat.format(date) + "'," + "'" + player.getUniqueId().toString() + "'," + "'false','air','-1'" + " )";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("写入初始化数据失败");
            LogPostUtils.postLog("写入初始化数据失败");
            error();
            return false;
        }
        finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }


    @Override
    public boolean setIsFrist(String player , boolean one) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET one = '" + one + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家首次进入服务器失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean setFraction(String player , int fraction) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET fraction = '" + fraction + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家积分失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean addFraction(String player, int fraction) {
        return this.setFraction(player , this.getFraction(player) + fraction);
    }

    @Override
    public boolean delFraction(String player, int fraction) {
        return this.setFraction(player , this.getFraction(player) - fraction);
    }

    @Override
    public boolean setWin(String player , int win) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET win = '" + win + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家胜场失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean addWin(String player, int win) {
        return this.setWin(player , this.getWin(player) + win);
    }

    @Override
    public boolean delWin(String player, int win) {
        return this.setWin(player , this.getWin(player) - win);
    }

    @Override
    public boolean setFail(String player , int fail) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET fail = '" + fail + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家失败场次失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean addFail(String player, int fail) {
        return this.setFail(player , this.getFail(player) + fail);
    }

    @Override
    public boolean delFail(String player, int fail) {
        return this.setFail(player , this.getFail(player) - fail);
    }

    @Override
    public boolean setTime(String player , String time) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET time = '" + time + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家进入服务器时间失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean setUUID(String player , String uuid) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET uuid = '" + uuid + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家UUID失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean setWinTime(String player , String winTime) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET wintime = '" + winTime + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家首胜时间失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public boolean setTitle(String player , int title) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "UPDATE pixelmonrank SET title = '" + title + "' WHERE player = '" + player + "'";
            statement.executeUpdate(sql);
            conn.close();
            return true;
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("设置玩家称号失败!");
            this.error();
            return false;
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public synchronized boolean getIsFrist(String player){
        boolean isFist = true;
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * FROM pixelmonrank WHERE PLAYER='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                isFist = res.getBoolean("ONE");
                return isFist;
            }
            res.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家是否首次进入服务器失败");
            error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return isFist;
    }

    @Override
    public int getId(String player) {
        int id = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                id = res.getInt("id");
                return id;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家ID出现问题");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return id;
    }

    @Override
    public synchronized int getFraction(String player) {
        int fraction = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                fraction = res.getInt("fraction");
                return fraction;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家分数失败");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return fraction;
    }

    @Override
    public synchronized int getWin(String player) {
        int win = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                win = res.getInt("win");
                return win;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家胜场失败");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return win;
    }

    @Override
    public synchronized int getFail(String player) {
        int fail = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                fail = res.getInt("fail");
                return fail;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家失败场次失败");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return fail;
    }

    @Override
    public synchronized String getTime(String player) {
        String time = "";
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                time = res.getString("time");
                return time;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取进入服务器时间");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return time;
    }

    @Override
    public synchronized String getUUID(String player) {
        String uuid = "";
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                uuid = res.getString("uuid");
                return uuid;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家UUID失败");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return uuid;
    }

    @Override
    public synchronized String getWinTime(String player) {
        String winTime = "";
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                winTime = res.getString("wintime");
                return winTime;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家首胜时间失败");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return winTime;
    }

    @Override
    public synchronized int getTitle(String player) {
        int title = -1;
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank where player='" + player + "'";
            res = statement.executeQuery(sql);
            while (res.next()){
                title = res.getInt("title");
                return title;
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家称号失败");
            this.error();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return title;
    }

    @Override
    public List<String> getPlayers() {
        ArrayList<String> arrayList = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank";
            res = statement.executeQuery(sql);
            while (res.next()){
                String name = res.getString("player");
                arrayList.add(name);
            }
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家列表失败!");
            this.error();
            return new ArrayList<>();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return arrayList;
    }

    @Override
    public Map<String, Integer> getPaiPlayers() {
        Map<String , Integer> playerMap = new ConcurrentHashMap();
        Connection conn = null;
        Statement statement = null;
        ResultSet res = null;
        try {
            conn = this.getConnection();
            statement = conn.createStatement();
            String sql = "SELECT * from pixelmonrank";
            res = statement.executeQuery(sql);
            while (res.next()){
                String name = res.getString("player");
                int fen = res.getInt("fraction");
                playerMap.put(name , fen);
            }
            conn.close();
        } catch (SQLException e) {
            this.pixlemonRank.warnInfo("获取玩家列表分数失败!");
            this.error();
            return new ConcurrentHashMap<>();
        }finally {
            try {
                if (conn != null){
                    conn.close();
                    conn = null;
                }
                if (statement != null){
                    statement.close();
                    statement = null;
                }
                if (res != null){
                    res.close();
                    res = null;
                }
            } catch (SQLException e) {
            }
        }
        return playerMap;
    }

    private void error(){
        this.isError = true;
        this.pixlemonRank.warnInfo("MYSQL数据库连接失败");
    }
}
