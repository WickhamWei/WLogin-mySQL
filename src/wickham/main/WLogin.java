package wickham.main;

import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

import wickham.command.player.Login;
import wickham.command.player.Register;
import wickham.command.player.UnLogin;
import wickham.main.login.WLoginSYS;

public class WLogin extends JavaPlugin {

	public static WLogin main;
	public static MySQL mySQL;
	public static boolean enableMySQL = true;// mysql启动开关

	@Override
	public void onEnable() {
		main = this;
		if (enableMySQL) {
			mySQL = new MySQL();
			try {// 启动mysql
				mySQL.openConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				getLogger().warning("连接数据库失败");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				getLogger().warning("连接数据库失败");
			}
			try {// 检查是否启动mysql
				if (mySQL.isConnection()) {
					getLogger().info("成功连接至数据库");
					WLoginSYS.initTable();
				} else {
					enableMySQL = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loadCommand();
	}

	@Override
	public void onDisable() {
		if (enableMySQL) {
			try {
				if (mySQL.isConnection()) {
					if (mySQL.disconnection()) {
						getLogger().info("已断开与数据库的连接");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void loadCommand() {// 载入指令
		main.getCommand("login").setExecutor(new Login());
		main.getCommand("register").setExecutor(new Register());
		main.getCommand("unlogin").setExecutor(new UnLogin());
	}

	public boolean isMySQLEnable() {// 服务器是否启动mysql
		return enableMySQL;
	}
}