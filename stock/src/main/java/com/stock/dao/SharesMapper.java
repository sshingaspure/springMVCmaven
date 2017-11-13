package com.stock.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.stock.beans.Shares;

public class SharesMapper implements RowMapper<Shares>{
	
	public Shares mapRow(ResultSet rs, int arg1) throws SQLException {

		Shares shares=new Shares();
		shares.setCmp_name(rs.getString("cmp_name"));
		shares.setShare_num(rs.getInt("shares"));
		
		return shares;
	}

	
}
