package druidDemo;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DBPool {

	private static Logger log = Logger.getLogger(DBPool.class);

	private DruidDataSource dbPool;
	//private BoneCPConfig config;
	private String url;
	private String username;
	private String password;

	public DBPool(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public boolean startup() {
		try {
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbPool = new DruidDataSource();
			initConfig();
			return true;
		} catch (Exception e) {
			log.error("DBPool startup error", e);
		}
		return false;
	}

	/**
	 * 关闭连接池
	 */
	public void shutdown() {
		dbPool.close();
		this.url = null;
		this.username = null;
		this.password = null;
	}

	/**
	 * 从连接池获取一个可用连接
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			if (dbPool != null) {
				return dbPool.getConnection();
			}
		} catch (SQLException e) {
			log.error("getConnection fail", e);
		}
		return null;
	}

	public boolean isClosed() {
		if (dbPool == null) {
			return true;
		}
		return false;
	}

	private void initConfig() throws SQLException {
		//config = new BoneCPConfig();
		// BoneCP主要配置参数
		dbPool.setUrl(url);
		dbPool.setUsername(username);
		dbPool.setPassword(password);
		dbPool.setFilters("stat");
		dbPool.setMaxActive(20);
		dbPool.setResetStatEnable(false);
		dbPool.setName("test");
	/*	dbPool.setAcquireIncrement(acquireIncrement);
		dbPool.setPoolAvailabilityThreshold(poolAvailabilityThreshold);
		dbPool.setConnectionTimeoutInMs(connectionTimeoutInMs);
		// BoneCP线程配置参数
		dbPool.setMaxConnectionAgeInSeconds(maxConnectionAgeInSeconds);
		dbPool.setIdleMaxAgeInMinutes(idleMaxAgeInMinutes);
		dbPool.setIdleConnectionTestPeriodInMinutes(idleConnectionTestPeriodInMinutes);*/
		/*// BoneCP可选配置参数
		config.setAcquireRetryAttempts(acquireRetryAttempts);
		config.setAcquireRetryDelayInMs(acquireRetryDelayDelayInMs);
		config.setLazyInit(lazyInit);
		config.setDisableJMX(disableJMX);
		// BoneCP调试配置参数
		config.setCloseConnectionWatch(closeConnectionWatch);
		config.setCloseConnectionWatchTimeoutInMs(closeConnectionWatchTimeoutInMs);
		config.setTransactionRecoveryEnabled(transactionRecoveryEnabled);
		config.setLogStatementsEnabled(logStatementsEnabled);
		config.setQueryExecuteTimeLimitInMs(queryExecuteTimeLimitInMs);
		config.setDisableConnectionTracking(disableConnectionTracking);*/
	}

	// 设置分区个数。这个参数默认为1，建议3-4
	private int partitionCount = 3;
	// 设置每个分区含有connection最大个数。这个参数默认为2。如果小于2，BoneCP将设置为50。
	private int maxConnectionsPerPartition = 20;
	// 设置每个分区含有connection最小个数。这个参数默认为0。
	private int minConnectionsPerPartition = 3;
	// 设置分区中的connection增长数量。这个参数默认为1。
	private int acquireIncrement = 3;
	// 设置连接池阀值。这个参数默认为20。如果小于0或是大于100，BoneCP将设置为20。
	private int poolAvailabilityThreshold = 20;
	// 设置获取connection超时的时间。这个参数默认为Long.MAX_VALUE;单位：毫秒。
	private long connectionTimeoutInMs = Long.MAX_VALUE;

	// BoneCP线程配置参数

	// 设置connection的存活时间。这个参数默认为0，单位：毫秒。设置为0该功能失效。
	private long maxConnectionAgeInSeconds = 0;
	// 设置connection的空闲存活时间。这个参数默认为60，单位：分钟。设置为0该功能失效。
	private long idleMaxAgeInMinutes = 20;
	// 设置测试connection的间隔时间。这个参数默认为240，单位：分钟。设置为0该功能失效。
	private long idleConnectionTestPeriodInMinutes = 240;

	// BoneCP可选配置参数
	// 设置重新获取连接的次数。这个参数默认为5。
	private int acquireRetryAttempts = 5;
	// 设置重新获取连接的次数间隔时间。这个参数默认为7000，单位：毫秒。如果小于等于0，BoneCP将设置为1000。
	private long acquireRetryDelayDelayInMs = 7000;
	// 设置连接池初始化功能。这个参数默认为false。
	private boolean lazyInit = false;
	// 设置是否关闭JMX功能。这个参数默认为false。
	private boolean disableJMX = false;

	// BoneCP调试配置参数
	// 设置是开启connection关闭情况监视器功能。这个参数默认为false。
	private boolean closeConnectionWatch = false;
	// 设置关闭connection监视器（CloseThreadMonitor）持续多长时间。这个参数默认为0
	private long closeConnectionWatchTimeoutInMs = 0;
	// 设置是否开启记录SQL语句功能。这个参数默认是false。
	private boolean logStatementsEnabled = false;
	// 设置执行SQL的超时时间。这个参数默认为0；单位：毫秒。
	private long queryExecuteTimeLimitInMs = 0;
	// 设置是否关闭connection跟踪功能。这个参数默认为false。
	private boolean disableConnectionTracking = false;
	// 设置事务回放功能。这个参数默认为false。
	private boolean transactionRecoveryEnabled = false;

}
