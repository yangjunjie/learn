package druidDemo;

import java.sql.Connection;
import java.sql.SQLException;

public class DruidStatDemo {


    public static void main(String[] args) {
        DBPool dbPool = new DBPool("jdbc:mysql://112.74.132.107:3306/cooxm_device_control?useUnicode=true&characterEncoding=utf8&autoReconnect=true", "cooxm", "cooxm");
        dbPool.startup();
        String sql = "SELECT `ctr_id`, `name`, `devid`, `devsn`, `devtype`, `type`, `roomtype`, `roomid`, `wall`, `relateddevid`, `createtime`, " +
                "`modifytime`, `state`, `remoteControl`, `isOnOffEqual`, `studyResult`,hintDevice FROM info_user_room_bind";
        Connection connection = null;
        try {
            for (; ; ) {
                Thread.sleep(3 * 1000);
                connection = dbPool.getConnection();
                connection.prepareStatement(sql).executeQuery();
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
