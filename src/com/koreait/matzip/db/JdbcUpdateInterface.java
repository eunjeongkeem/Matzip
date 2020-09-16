package com.koreait.matzip.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface JdbcUpdateInterface {
	void update(PreparedStatement ps) throws SQLException; // 인터페이스에 객체화가 안된다 (public abstract 생략가능)
}
