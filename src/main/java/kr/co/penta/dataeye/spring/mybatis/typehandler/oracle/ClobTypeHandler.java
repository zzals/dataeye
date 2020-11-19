package kr.co.penta.dataeye.spring.mybatis.typehandler.oracle;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import oracle.jdbc.OracleClob;

@MappedJdbcTypes({JdbcType.CLOB})
@MappedTypes(value={OracleClob.class, String.class})
public class ClobTypeHandler extends org.apache.ibatis.type.ClobTypeHandler implements TypeHandler<String> {
	@Override
	public void setParameter(PreparedStatement ps, int i, String parameter,
			JdbcType jdbcType) throws SQLException {
	    ps.setString(i, parameter);
	}

	@Override
	public String getResult(ResultSet rs, String columnName)
			throws SQLException {
		final StringBuffer output = new StringBuffer();
		final Reader reader = rs.getCharacterStream(columnName);
		char[] buffer = new char[1024];
		int read = 0;
		try {
			while ( (read = reader.read(buffer,0,1024)) != -1) {
				output.append(buffer, 0, read);
			}
		} catch (Exception e) {}
		return output.toString();
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		final StringBuffer output = new StringBuffer();
		final Reader reader = rs.getCharacterStream(columnIndex);
		char[] buffer = new char[1024];
		int read = 0;
		try {
			while ( (read = reader.read(buffer,0,1024)) != -1) {
				output.append(buffer, 0, read);
			}
		} catch (Exception e) {}
		return output.toString();
	}

	@Override
	public String getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		final StringBuffer output = new StringBuffer();
		final Reader reader = cs.getCharacterStream(columnIndex);
		char[] buffer = new char[1024];
		int read = 0;
		try {
			while ( (read = reader.read(buffer,0,1024)) != -1) {
				output.append(buffer, 0, read);
			}
		} catch (Exception e) {}
		return output.toString();
	}
}
