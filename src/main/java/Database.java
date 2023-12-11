import java.sql.*;

public class Database {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:database.db";

    private Connection conn;

    public Database() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver not found!");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            stat.execute("CREATE TABLE IF NOT EXISTS schedules (id INTEGER PRIMARY KEY, user_id VARCHAR(32), schedule_name VARCHAR(256), event_list TEXT)");
        } catch (SQLException e) {
            System.err.println("Cannot create SQLite connection!");
            e.printStackTrace();
            return;
        }
    }

    public boolean isInitialised(){
        return conn != null;
    }

    public boolean loadAll(ScheduleManager scheduleManager){
        Statement stat = null;
        ResultSet res = null;
        boolean loadedProperly = false;
        try {
            stat = conn.createStatement();
            res = stat.executeQuery("SELECT * FROM schedules;");
            while(res.next()) {
                scheduleManager.getScheduleComposite(res.getString("user_id"))
                        .loadSchedule(res.getString("schedule_name"), res.getString("event_list"));
            }
            loadedProperly = true;
        } catch (SQLException e) {
            System.err.println("Cannot load schedules!");
            e.printStackTrace();
        } finally {
            close(stat, res);
        }
        return loadedProperly;
    }



    public void createSchedule(String userId, String scheduleName) {
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(
                    "insert into schedules values (NULL, ?, ?, ?);");
            prepStmt.setString(1, userId);
            prepStmt.setString(2, scheduleName);
            prepStmt.setString(3, "{}");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Cannot create schedule: " + userId + " " + scheduleName);
            e.printStackTrace();
        } finally {
            close(prepStmt, null);
        }
    }

    public void updateSchedule(ScheduleManager.ScheduleComposite.Schedule schedule) {
        PreparedStatement prepStmt = null;
        boolean executed = false;
        try {
            prepStmt = conn.prepareStatement("UPDATE schedules SET event_list = ? WHERE user_id = ? AND schedule_name = ?;");
            prepStmt.setString(1, schedule.serialiseEvents());
            prepStmt.setString(2, schedule.getUserId());
            prepStmt.setString(3, schedule.getScheduleName());
            prepStmt.execute();
            executed = true;
        } catch (SQLException e) {
            System.err.println("Cannot update schedule " + schedule.getUserId() + " " + schedule.getScheduleName());
            e.printStackTrace();
        } finally {
            close(prepStmt, null);
        }
        //return executed;
    }

    public boolean deleteSchedule(String userId, String scheduleName) {
        PreparedStatement prepStmt = null;
        boolean executed = false;
        try {
            prepStmt = conn.prepareStatement("DELETE FROM schedules WHERE user_id = ? AND schedule_name = ?;");
            prepStmt.setString(1, userId);
            prepStmt.setString(2, scheduleName);
            prepStmt.execute();
            executed = true;
        } catch (SQLException e) {
            System.err.println("Cannot remove schedule " + userId + " " + scheduleName);
            e.printStackTrace();
        } finally {
            close(prepStmt, null);
        }
        return executed;
    }

    private void close(Statement stat, ResultSet res){
        try {
            if (stat != null)
                stat.close();
            if (res != null)
                res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
