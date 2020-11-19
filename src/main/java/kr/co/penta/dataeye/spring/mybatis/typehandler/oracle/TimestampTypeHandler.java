package kr.co.penta.dataeye.spring.mybatis.typehandler.oracle;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.TimeUtils;
import oracle.sql.TIMESTAMP;

@MappedJdbcTypes(value={JdbcType.TIMESTAMP})
@MappedTypes(value={TIMESTAMP.class})
public class TimestampTypeHandler implements TypeHandler<String> {
    @Override
	public void setParameter(PreparedStatement ps, int i, String parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter);
	}

	@Override
	public String getResult(ResultSet rs, String columnName)
			throws SQLException {
	    final Timestamp timestamp = rs.getTimestamp(columnName);
		return TimeUtils.getInstance().formatDate(timestamp, DataEyeSettingsHolder.getInstance().getConfig().getJavaTimestampFormat());
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		final Timestamp timestamp = rs.getTimestamp(columnIndex);
		return TimeUtils.getInstance().formatDate(timestamp, DataEyeSettingsHolder.getInstance().getConfig().getJavaTimestampFormat());
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		final Timestamp timestamp = cs.getTimestamp(columnIndex);
		return TimeUtils.getInstance().formatDate(timestamp, DataEyeSettingsHolder.getInstance().getConfig().getJavaTimestampFormat());
	}
}
